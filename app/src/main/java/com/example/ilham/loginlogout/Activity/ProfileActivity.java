package com.example.ilham.loginlogout.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.Users;

/**
 * Created by Belal on 18/09/16.
 */


public class ProfileActivity extends Fragment {

    private TextView uname, fullname, birthday, phoneNum, email, address, city, state, country;
    Users users;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_profile, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile");
        setHasOptionsMenu(true);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.presence_swiperefresh);

        users = new Users(getActivity().getApplicationContext());
        uname = (TextView) view.findViewById(R.id.profile_name);
        fullname = (TextView) view.findViewById(R.id.profile_fullName);
        birthday = (TextView) view.findViewById(R.id.profile_birthDay);
        phoneNum = (TextView) view.findViewById(R.id.profile_phoneNum);
        email = (TextView) view.findViewById(R.id.profile_email);
        address = (TextView) view.findViewById(R.id.profile_address);
        city = (TextView) view.findViewById(R.id.profile_city);
        state = (TextView) view.findViewById(R.id.profile_state);
        country = (TextView) view.findViewById(R.id.profile_country);

        String cekUname = users.getUname();
        String cekFullname = users.getFullname();
        String cekBirthday = users.getBirthday();
        String cekPhoneNum = users.getPhoneNum();
        String cekEmail = users.getEmail();
        String cekAddress = users.getAddress();
        String cekCity = users.getCity();
        String cekState = users.getState();
        String cekCountry = users.getCountry();

        uname.setText(cekUname);
        fullname.setText(cekFullname);
        birthday.setText(cekBirthday);
        phoneNum.setText(cekPhoneNum);
        email.setText(cekEmail);
        address.setText(cekAddress);
        city.setText(cekCity);
        state.setText(cekState);
        country.setText(cekCountry);
    }

    public void onStop() {
        super.onStop();
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.profile_edit:
                Snackbar.make(getView(), "Edit", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.profile_share:
                Snackbar.make(getView(), "Share", Snackbar.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

/*
    private void initialize() {
        uname.setText(users.getUname());
        fullname.setText(users.getFullname());
        phoneNum.setText(users.getPhoneNum());
        email.setText(users.getEmail());
        address.setText(users.getAddress());
    }

    private void findViewById() {
        uname = (TextView) getView().findViewById(R.id.profile_name);
        fullname = (TextView) getView().findViewById(R.id.profile_fullName);
        phoneNum = (TextView) getView().findViewById(R.id.profile_phoneNum);
        email = (TextView) getView().findViewById(R.id.profile_email);
        address = (TextView) getView().findViewById(R.id.profile_address);
    }
    */
}