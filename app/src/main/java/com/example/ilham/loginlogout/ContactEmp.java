package com.example.ilham.loginlogout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ilham on 2/4/2018.
 */

public class ContactEmp {

    @SerializedName("id_beacon")
    private String id_beacon;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("uname")
    private String uname;

    @SerializedName("email")
    private String email;

    @SerializedName("phoneNum")
    private String phoneNum;

    @SerializedName("birthday_date")
    private String birthday_date;

    @SerializedName("position")
    private String position;

    @SerializedName("division")
    private String division;

    @SerializedName("address")
    private String address;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("country")
    private String country;

    public ContactEmp(String id_beacon, String fullname, String uname, String email, String phoneNum, String birthday_date, String position, String division, String address, String city, String state, String country) {
        this.id_beacon = id_beacon;
        this.fullname = fullname;
        this.uname = uname;
        this.email = email;
        this.phoneNum = phoneNum;
        this.birthday_date = birthday_date;
        this.position = position;
        this.division = division;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
    }
//
    // Getter

    public String getId_beacon() {
        return id_beacon;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUname() {
        return uname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getBirthday_date() {
        return birthday_date;
    }

    public String getPosition() {
        return position;
    }

    public String getDivision() {
        return division;
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

    //
    // Setter

    public void setId_beacon(String id_beacon) {
        this.id_beacon = id_beacon;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setBirthday_date(String birthday_date) {
        this.birthday_date = birthday_date;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDivision(String division) {
        this.division = division;
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
}
