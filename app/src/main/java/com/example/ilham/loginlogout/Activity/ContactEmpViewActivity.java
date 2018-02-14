package com.example.ilham.loginlogout.Activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ilham.loginlogout.R;

public class ContactEmpViewActivity extends AppCompatActivity {

    private String fullname, uname, birthday_date, phoneNum, address, city, state, country, email, position, division, image;
    Bundle bundle;
    private TextView Fullname, Nickname, Birthday, PhoneNum, Address, Email, Position;
    private ImageView Image;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_emp_view);

        bundle = getIntent().getExtras();
        initializeBundle();
        initializeMaterialView();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(fullname);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        collapsingToolbar.setExpandedTitleColor(Color.parseColor("#ffffff"));

        setMaterialView();

    }

    private void setMaterialView() {
        PhoneNum.setText(phoneNum);
        Email.setText(email);
        Birthday.setText(birthday_date);
        Address.setText("" + address + ", " + city + ", " + state + ", " + country);
        Nickname.setText(uname);
        Position.setText("" + position + " / " + division);
        Image.setImageDrawable(Drawable.createFromPath((image)));
    }

    private void initializeMaterialView() {
        PhoneNum = (TextView) findViewById(R.id.contactemp_phone);
        Email = (TextView) findViewById(R.id.contactemp_email);
        Birthday = (TextView) findViewById(R.id.contactemp_birthday);
        Address = (TextView) findViewById(R.id.contactemp_address);
        Nickname = (TextView) findViewById(R.id.contactemp_uname);
        Position = (TextView) findViewById(R.id.contactemp_position);
        Image = (ImageView) findViewById(R.id.contactemp_img);
    }

    private void initializeBundle() {
        fullname = bundle.getString("fullname");
        uname = bundle.getString("uname");
        birthday_date = bundle.getString("birthday_date");
        phoneNum = bundle.getString("phoneNum");
        address = bundle.getString("address");
        city = bundle.getString("city");
        state = bundle.getString("state");
        country = bundle.getString("country");
        email = bundle.getString("email");
        position = bundle.getString("position");
        division = bundle.getString("division");
        image = bundle.getParcelable("image");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
