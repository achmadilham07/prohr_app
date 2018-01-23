package com.example.ilham.loginlogout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ilham on 12/23/2017.
 */

public class List {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("caption")
    private String caption;

    @SerializedName("image")
    private int image;

    public List(String id, String title, String caption, int image) {
        this.id = id;
        this.title = title;
        this.caption = caption;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void add(String automobile) {

    }
}
