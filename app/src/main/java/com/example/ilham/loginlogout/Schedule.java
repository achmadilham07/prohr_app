package com.example.ilham.loginlogout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ilham on 4/24/2018.
 */

public class Schedule {

    @SerializedName("id_beacon")
    private String id_beacon;

    @SerializedName("title")
    private String title;

    @SerializedName("notes")
    private String notes;

    @SerializedName("level")
    private String level;

    @SerializedName("date_event")
    private String date_event;

    @SerializedName("partner")
    private String partner;

    @SerializedName("id_event")
    private String id_event;

    // GETTER

    public String getId_beacon() {
        return id_beacon;
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }

    public String getLevel() {
        return level;
    }

    public String getDate_event() {
        return date_event;
    }

    public String getPartner() {
        return partner;
    }

    public String getId_event() {
        return id_event;
    }

    // SETTER

    public void setId_beacon(String id_beacon) {
        this.id_beacon = id_beacon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setDate_event(String date_event) {
        this.date_event = date_event;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public void setId_event(String id_event) {
        this.id_event = id_event;
    }
}
