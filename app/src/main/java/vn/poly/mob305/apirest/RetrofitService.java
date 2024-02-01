package vn.poly.mob305.apirest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {

    @GET("/todos/{id}")
    public Call<User> getUserInfo(@Path("id") int id);
     // định nghĩa phương thức request vào địa chỉ /todos/ với biến id
     // trong địa chỉ nên dùng từ khoá là Path



}
