package com.example.ilham.loginlogout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ilham on 1/24/2018.
 */

public class PresenceYear {

    @SerializedName("year")
    private String year;

    public PresenceYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
