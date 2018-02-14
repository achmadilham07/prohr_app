package com.example.ilham.loginlogout;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by ilham on 12/16/2017.
 */

public class Message {

    @SerializedName("status")
    private Boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("users")
    private Map<String,String> users;

    public Message() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }

}
