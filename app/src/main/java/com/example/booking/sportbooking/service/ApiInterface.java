package com.example.booking.sportbooking.service;

/**
 * Created by Tomek on 09.04.2017.
 */

import com.example.booking.sportbooking.object.ReservationObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("reservationObject")
    Call<List<ReservationObject>> doGetListReservationObject();

//    @POST("/api/users")
//    Call<User> createUser(@Body User user);
//
//    @GET("/api/users?")
//    Call<UserList> doGetUserList(@Query("page") String page);
//
//    @FormUrlEncoded
//    @POST("/api/users?")
//    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
}
