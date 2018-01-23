package com.example.ilham.loginlogout.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.Users;

/**
 * Created by Belal on 18/09/16.
 */


public class ProfileActivity extends Fragment {

    private TextView uname, fullname, phoneNum, email, address;
    Users users;

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

        users = new Users(getActivity().getApplicationContext());

        uname = (TextView) view.findViewById(R.id.profile_name);
        fullname = (TextView) view.findViewById(R.id.profile_fullName);
        phoneNum = (TextView) view.findViewById(R.id.profile_phoneNum);
        email = (TextView) view.findViewById(R.id.profile_email);
        address = (TextView) view.findViewById(R.id.profile_address);

        String cekUname = users.getUname();
        String cekFullname = users.getFullname();
        String cekPhoneNum = users.getPhoneNum();
        String cekEmail = users.getEmail();
        String cekAddress = users.getAddress();

        uname.setText(cekUname);
        fullname.setText(cekFullname);
        phoneNum.setText(""+cekPhoneNum);
        email.setText(cekEmail);
        address.setText(cekAddress);
    }

    private void initialize() {
        uname.setText(users.getUname());
        fullname.setText(users.getFullname());
        phoneNum.setText(users.getPhoneNum());
        email.setText(users.getEmail());
        address.setText(users.getAddress());
    }
/*
    private void findViewById() {
        uname = (TextView) getView().findViewById(R.id.profile_name);
        fullname = (TextView) getView().findViewById(R.id.profile_fullName);
        phoneNum = (TextView) getView().findViewById(R.id.profile_phoneNum);
        email = (TextView) getView().findViewById(R.id.profile_email);
        address = (TextView) getView().findViewById(R.id.profile_address);
    }
    */
}