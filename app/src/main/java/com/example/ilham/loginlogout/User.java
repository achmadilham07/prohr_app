package com.example.ilham.loginlogout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ilham on 2/19/2018.
 */

public class User {
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_ID_BEACON = "id_beacon";
    public static final String KEY_UNAME = "uname";
    public static final String KEY_UID = "uid";
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_PHONENUM = "phoneNum";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_POSITION = "position";
    public static final String KEY_DIVISION = "division";
    public static final String KEY_PASSWORD = "password";

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("id_beacon")
    private String id_beacon;

    @SerializedName("uname")
    private String uname;

    @SerializedName("uid")
    private String uid;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("phoneNum")
    private String phoneNum;

    @SerializedName("email")
    private String email;

    @SerializedName("address")
    private String address;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("country")
    private String country;

    @SerializedName("position")
    private String position;

    @SerializedName("division")
    private String division;

    @SerializedName("password")
    private String password;

    //
    // Getter
    //

    public String getFullname() {
        return fullname;
    }

    public String getId_beacon() {
        return id_beacon;
    }

    public String getUname() {
        return uname;
    }

    public String getUid() {
        return uid;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPosition() {
        return position;
    }

    public String getDivision() {
        return division;
    }

    public String getPassword() {
        return password;
    }

    //
    // Setter
    //

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setId_beacon(String id_beacon) {
        this.id_beacon = id_beacon;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
