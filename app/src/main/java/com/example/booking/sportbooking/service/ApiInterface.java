package com.example.booking.sportbooking.service;

/**
 * Created by Tomek on 09.04.2017.
 */

import com.example.booking.sportbooking.object.ReservationObject;
import com.example.booking.sportbooking.objectItem.ReservationObjectItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("reservationObject")
    Call<List<ReservationObject>> doGetListReservationObject();

    @GET("reservationObjectItem/byObjectAndDate")
    Call<List<ReservationObjectItem>> doGetListReservationObjectItem(@Query("objectId") String objectId, @Query("date") String date);

    @GET("reservation/create")
    Call<String> createReservation(@Query("userId") String userId, @Query("resId") String resId);

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
