package com.example.ilham.loginlogout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ilham on 1/24/2018.
 */

public class Presence {

    @SerializedName("no")
    private String no;

    @SerializedName("id_beacon")
    private String id_beacon;

    @SerializedName("year")
    private String year;

    @SerializedName("month")
    private String month;

    @SerializedName("date")
    private String date;

    @SerializedName("in_time")
    private String in_time;

    @SerializedName("out_time")
    private String out_time;

    @SerializedName("status")
    private String status;

    public Presence(String no, String id_beacon, String year, String month, String date, String in_time, String out_time, String status) {
        this.no = no;
        this.id_beacon = id_beacon;
        this.year = year;
        this.month = month;
        this.date = date;
        this.in_time = in_time;
        this.out_time = out_time;
        this.status = status;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_beacon() {
        return id_beacon;
    }

    public void setId_beacon(String id_beacon) {
        this.id_beacon = id_beacon;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
