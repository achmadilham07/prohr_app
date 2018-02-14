package com.example.ilham.loginlogout;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by ilham on 12/16/2017.
 */

public interface RetrofitInterface {
    @POST("presenceGetYear.php")
    @FormUrlEncoded
    Call<List<PresenceYear>> getPresenceYear(@Field("id_beacon") String id_beacon);

    @Multipart
    @POST("presenceGetMonth.php")
    Call<List<PresenceMonth>> getPresenceMonth(@Part("id_beacon") RequestBody id_beacon,
                                               @Part("year") RequestBody year);

    @Multipart
    @POST("presenceGetAll.php")
    Call<List<Presence>> getPresence(@Part("id_beacon") RequestBody id_beacon,
                                     @Part("year") RequestBody year,
                                     @Part("month") RequestBody month);

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


    @Multipart
    @POST("contactEmp.php")
    Call<List<ContactEmp>> getContactEmp(@Part("uid") RequestBody uidRequest,
                                         @Part("id_beacon") RequestBody idBeaconRequest);
}
