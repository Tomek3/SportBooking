package com.example.booking.sportbooking.service;

/**
 * Created by Tomek on 02.04.2017.
 */

public class ReservationService {
    public static final String BASE_URL = "http://192.168.0.16:8080/ReservationService/";
    //public static final String BASE_URL = "http://192.168.43.15:8080/ReservationService/";

    public static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
