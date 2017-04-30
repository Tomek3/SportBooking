package com.example.booking.sportbooking.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tomek on 30.04.2017.
 */

public class Notification {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("reservationId")
    @Expose
    private Integer reservationId;
    @SerializedName("objectWatchId")
    @Expose
    private Integer objectWatchId;
    @SerializedName("objectName")
    @Expose
    private String objectName;
    @SerializedName("dateFrom")
    @Expose
    private String dateFrom;
    @SerializedName("dateTo")
    @Expose
    private String dateTo;
    @SerializedName("reservationObjectItemId")
    @Expose
    private Integer reservationObjectItemId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getObjectWatchId() {
        return objectWatchId;
    }

    public void setObjectWatchId(Integer objectWatchId) {
        this.objectWatchId = objectWatchId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
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

    public Integer getReservationObjectItemId() {
        return reservationObjectItemId;
    }

    public void setReservationObjectItemId(Integer reservationObjectItemId) {
        this.reservationObjectItemId = reservationObjectItemId;
    }

}
