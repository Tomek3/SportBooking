package com.example.booking.sportbooking.reservation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tomek on 25.04.2017.
 */

public class Reservation {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("reservationObjectId")
    @Expose
    private Integer reservationObjectId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("dateFrom")
    @Expose
    private String dateFrom;
    @SerializedName("dateTo")
    @Expose
    private String dateTo;
    @SerializedName("reservationObjectName")
    @Expose
    private String reservationObjectName;
    @SerializedName("reservationObjectAddress")
    @Expose
    private String reservationObjectAddress;
    @SerializedName("reservationObjectInfo")
    @Expose
    private String reservationObjectInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReservationObjectId() {
        return reservationObjectId;
    }

    public void setReservationObjectId(Integer reservationObjectId) {
        this.reservationObjectId = reservationObjectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getReservationObjectName() {
        return reservationObjectName;
    }

    public void setReservationObjectName(String reservationObjectName) {
        this.reservationObjectName = reservationObjectName;
    }

    public String getReservationObjectAddress() {
        return reservationObjectAddress;
    }

    public void setReservationObjectAddress(String reservationObjectAddress) {
        this.reservationObjectAddress = reservationObjectAddress;
    }

    public String getReservationObjectInfo() {
        return reservationObjectInfo;
    }

    public void setReservationObjectInfo(String reservationObjectInfo) {
        this.reservationObjectInfo = reservationObjectInfo;
    }
}
