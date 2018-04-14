package com.example.ilham.loginlogout;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EntryNow {

    @SerializedName("id")
    private String id;

    @SerializedName("id_beacon")
    private String id_beacon;

    @SerializedName("entry")
    private String entry;

    @SerializedName("category_id")
    private String category_id;

    @SerializedName("notes")
    private String notes;

    @SerializedName("date_created")
    private String date_created;

    // GETTER

    public String getId() {
        return id;
    }

    public String getId_beacon() {
        return id_beacon;
    }

    public String getEntry() {
        return entry;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getNotes() {
        return notes;
    }

    public String getDate_created() {
        return date_created;
    }

    // SETTER

    public void setId(String id) {
        this.id = id;
    }

    public void setId_beacon(String id_beacon) {
        this.id_beacon = id_beacon;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
