package com.example.ilham.loginlogout.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.SessionManager;

public class SalaryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtcoba;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

        session = new SessionManager(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Salary");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
