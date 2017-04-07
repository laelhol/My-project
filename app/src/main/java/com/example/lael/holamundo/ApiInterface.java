package com.example.lael.holamundo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


interface ApiInterface {
    @FormUrlEncoded
    @POST("/user/login")
    Call<Responses<Integer>> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("/user/getclientprofile")
    Call <Responses<UserData>> info();

    @GET("/getGyms/1/28")
    Call <Responses<List<GymPostRetrofit>>> allGyms();

}
