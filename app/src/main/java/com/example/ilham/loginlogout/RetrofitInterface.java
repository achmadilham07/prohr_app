package com.example.ilham.loginlogout;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by ilham on 12/16/2017.
 */

public interface RetrofitInterface {

    @Multipart
    @POST("addData.php")
    Call<Message> addData(@Part("name") RequestBody name,
                          @Part("age") RequestBody age,
                          @Part("email") RequestBody email,
                          @Part("password") RequestBody password);

    @Multipart
    @POST("findData.php")
    Call<Message> findData(@Part("email") RequestBody email,
                           @Part("password") RequestBody password);


}
