package com.example.peoplemon.Network;

import com.example.peoplemon.Models.Account;
import com.example.peoplemon.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by JoshuaMabry on 11/5/16.
 */

public interface ApiService {

    //Register New Account
    @POST("/api/Account/Register")
    Call<Void>register(@Body Account account);

    //Login with registered info
    @FormUrlEncoded
    @POST("/token")
    Call<Account>login(@Field(value = "grant_type", encoded = true) String grant_type,
                       @Field(value = "username", encoded = true) String username,
                       @Field(value = "password", encoded = true) String password);

    //Get User Info Call
    @GET("/api/Account/UserInfo")
    Call<Account>getUserInfo();

    @POST("/api/Account/UserInfo")
    Call<Void>postUserInfo(@Body Account account);

    @POST("/v1/User/CheckIn")
    Call<Void>checkIn(@Body Account checkIn);

    @GET("v1/User/Nearby")
    Call<User[]> findNearby(@Query("radiusInMeters") Integer radiusInMeters);

    @GET ("/v1/User/Caught")
    Call<User[]> caughtUsers();

    @POST("/v1/User/Catch")
    Call<Void>catchUser(@Body User user);
}
