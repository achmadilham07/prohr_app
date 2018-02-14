package com.example.ilham.loginlogout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by ilham on 1/19/2018.
 */
@SuppressLint("CommitPrefEdits")
public class Users {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // nama sharepreference
    private static final String PREF_NAME = "USER";
    public static final String KEY_USER = "user";
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

    private Map<String, String> user;
    private String fullname;
    private String id_beacon;
    private String uname;
    private String uid;
    private String birthday;
    private String phoneNum;
    private String email;
    private String address;
    private String city;
    private String state;
    private String country;
    private String position;
    private String division;
    private String password;

    public Users(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public Map<String, String> getUser() {
        return user;
    }

    public void setUsers(Map<String, String> users) {
        this.user = users;
        editor.putString(KEY_EMAIL, user.get(KEY_EMAIL));
        editor.putString(KEY_ID_BEACON, user.get(KEY_ID_BEACON));
        editor.putString(KEY_UNAME, user.get(KEY_UNAME));
        editor.putString(KEY_UID, user.get(KEY_UID));
        editor.putString(KEY_ADDRESS, user.get(KEY_ADDRESS));
        editor.putString(KEY_CITY, user.get(KEY_CITY));
        editor.putString(KEY_STATE, user.get(KEY_STATE));
        editor.putString(KEY_COUNTRY, user.get(KEY_COUNTRY));
        editor.putString(KEY_FULLNAME, user.get(KEY_FULLNAME));
        editor.putString(KEY_BIRTHDAY, user.get(KEY_BIRTHDAY));
        editor.putString(KEY_PHONENUM, (user.get(KEY_PHONENUM)));
        editor.putString(KEY_PASSWORD, user.get(KEY_PASSWORD));
        editor.commit();
    }

    public String getUid() {
        setUid();
        return uid;
    }

    public void setUid() {
        this.uid = pref.getString(KEY_UID, null);
    }

    public String getBirthday() {
        setBirthday();
        return birthday;
    }

    public void setBirthday() {
        this.birthday = pref.getString(KEY_BIRTHDAY, null);
    }

    public String getCity() {
        setCity();
        return city;
    }

    public void setCity() {
        this.city = pref.getString(KEY_CITY, null);
    }

    public String getState() {
        setState();
        return state;
    }

    public void setState() {
        this.state = pref.getString(KEY_STATE, null);
    }

    public String getCountry() {
        setCountry();
        return country;
    }

    public void setCountry() {
        this.country = pref.getString(KEY_COUNTRY, null);
    }

    public String getId_beacon() {
        setId_beacon();
        return id_beacon;
    }

    public void setId_beacon() {
        this.id_beacon = pref.getString(KEY_ID_BEACON, null);
    }

    public void setEmail() {
        this.email = pref.getString(KEY_EMAIL, null);
    }

    public String getEmail() {
        setEmail();
        return email;
    }

    public String getUname() {
        setUname();
        return uname;
    }

    public void setUname() {
        this.uname = pref.getString(KEY_UNAME, null);
    }

    public String getFullname() {
        setFullname();
        return fullname;
    }

    public void setFullname() {
        this.fullname = pref.getString(KEY_FULLNAME, null);
    }

    public String getPhoneNum() {
        setPhoneNum();
        return phoneNum;
    }

    public void setPhoneNum() {
        this.phoneNum = pref.getString(KEY_PHONENUM, null);
    }

    public String getAddress() {
        setAddress();
        return address;
    }

    public void setAddress() {
        this.address = pref.getString(KEY_ADDRESS, null);
    }

    public String getPassword() {
        setPassword();
        return password;
    }

    public void setPassword() {
        this.password = pref.getString(KEY_PASSWORD, null);
    }
}
