package vn.poly.mob305.apirest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.poly.mob305.apirest.Person;
import vn.poly.mob305.apirest.PersonAdapter;
import vn.poly.mob305.apirest.R;
import vn.poly.mob305.apirest.RetrofitInstance;
import vn.poly.mob305.apirest.RetrofitService;
import vn.poly.mob305.apirest.loadmore.EndlessRecyclerViewScrollListener;

public class ListActivity extends AppCompatActivity {
    private RecyclerView rvList;
    private int _page = 1;

    private List<Person> personList;
    private PersonAdapter personAdapter;

    private SwipeRefreshLayout sLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        sLayout = findViewById(R.id.sLayout);
        sLayout.setOnRefreshListener(() -> {
            RetrofitService service = RetrofitInstance.getInstance().create(RetrofitService.class);
            _page = 1;
            Call<List<Person>> listCall = service.getListUsers(_page,10);
            listCall.enqueue(new Callback<List<Person>>() {
                @Override
                public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                    if (response.body() != null){
                        if (response.body().size() == 0){
                            rvList.addOnScrollListener(null);
                        }else {
                            sLayout.setRefreshing(false);
                            _page++;
                            personList = new ArrayList<>();
                            personList.addAll(response.body());
                            personAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Person>> call, Throwable t) {

                }
            });
        });
        rvList = findViewById(R.id.rvList);
        personList = new ArrayList<>();
        personAdapter = new PersonAdapter(personList);

        LinearLayoutManager ln = new LinearLayoutManager(this);
        rvList.setLayoutManager(ln);
        rvList.setAdapter(personAdapter);

        RetrofitService service = RetrofitInstance.getInstance().create(RetrofitService.class);
        Call<List<Person>> listCall = service.getListUsers(_page,10);
        listCall.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body() != null){
                    if (response.body().size() == 0){
                        rvList.addOnScrollListener(null);
                    }else {
                        _page++;
                        personList.addAll(response.body());
                        personAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {

            }
        });
        rvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(ln) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // sự kiện được gọi vào mỗi khi kéo list xuống dưới cùng
                // viết câu lệnh request tới API tại đây !!!
                Log.e("AAA","AAA");
                RetrofitService service = RetrofitInstance.getInstance().create(RetrofitService.class);
                Call<List<Person>> listCall = service.getListUsers(_page,10);
                listCall.enqueue(new Callback<List<Person>>() {
                    @Override
                    public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                        if (response.body() != null){
                            if (response.body().size() == 0){
                                rvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(ln) {
                                    @Override
                                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                                    }
                                });
                            }else {
                                _page++;
                                personList.addAll(response.body());
                                personAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Person>> call, Throwable t) {

                    }
                });


            }
        });


    }
}