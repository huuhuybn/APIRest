package vn.poly.mob305.apirest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String url = "https://imageio.forbes.com/specials-images/imageserve/6064af50093e0936dc61b40f/2020-Ford-GT/960x0.jpg";
    String gif = "https://i.pinimg.com/originals/0c/64/9a/0c649a17ec1e5f5ca340248b4ef4e4be.gif";

    String api = "https://jsonplaceholder.typicode.com/todos/1";

    ImageView imgNormal,imgGif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        imgGif = findViewById(R.id.imgGif);
        imgNormal = findViewById(R.id.imgNormal);
        // thu vien hien thi anh tu url
        // Glide
//        Glide.with(this).load(url).centerCrop().into(imgNormal);
//        Glide.with(this).load(gif).placeholder(R.drawable.ic_launcher_background)
//                .into(imgGif);
        // Fresco
        SimpleDraweeView imgDree =
                findViewById(R.id.imgFressco);
        imgDree.setImageURI(url);

        imgDree.setOnClickListener(v -> {
            RetrofitService rf = RetrofitInstance.getInstance()
                    .create(RetrofitService.class);
            Call<User> user = rf.createPost(new User());
            user.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Toast.makeText(MainActivity.this, response.body().body, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

        });
        // Picasso
        Picasso.get().load(url).into(imgNormal);
        Picasso.get().load(gif).into(imgGif);
        // thu vien HTTP Request - Retrofit
        imgNormal.setOnClickListener(v -> {
            RetrofitService rf = RetrofitInstance.getInstance()
                    .create(RetrofitService.class);

            Call<User> user = rf.getUserInfo(2);
            user.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Toast.makeText(MainActivity.this, response.body().title, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Loi ...", Toast.LENGTH_SHORT).show();
                }
            });



        });

        imgGif.setOnClickListener(v -> {
            RetrofitService rf = RetrofitInstance.getInstance()
                    .create(RetrofitService.class);
            Call<List<Comment>> comments = rf.getPostComment(2);
            comments.enqueue(new Callback<List<Comment>>() {
                @Override
                public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                    Toast.makeText(MainActivity.this, "" + response.body().size(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<List<Comment>> call, Throwable t) {

                }
            });
        });
    }
}