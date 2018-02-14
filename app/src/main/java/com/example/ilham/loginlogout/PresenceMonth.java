package com.example.ilham.loginlogout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ilham on 1/24/2018.
 */

public class PresenceMonth {

    @SerializedName("month")
    private String month;

    public PresenceMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
