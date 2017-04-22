package com.example.booking.sportbooking.objectItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReservationObjectItem implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("reservation_object_id")
    @Expose
    private Integer reservationObjectId;
    @SerializedName("dateFrom")
    @Expose
    private String dateFrom;
    @SerializedName("dateTo")
    @Expose
    private String dateTo;
    @SerializedName("available")
    @Expose
    private Boolean available;

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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

}
