package com.example.ilham.loginlogout.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilham.loginlogout.Constant;
import com.example.ilham.loginlogout.Message;
import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.RetrofitInterface;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private Constant constant = new Constant();
    private EditText name, age, email, password;
    private TextView login;
    private Button submit;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        name = (EditText) findViewById(R.id.nameRegist);
        age = (EditText) findViewById(R.id.ageRegist);
        email = (EditText) findViewById(R.id.emailRegist);
        password = (EditText) findViewById(R.id.passwordRegist);

        login = (TextView) findViewById(R.id.clickLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });

        submit = (Button) findViewById(R.id.sumbitRegist);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Age = age.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                addData(Name, Age, Email, Password);
            }
        });

    }

    private void addData(String name, String age, String email, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        // setting uri
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        RequestBody nameRequest = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody ageRequest = RequestBody.create(MediaType.parse("text/plain"), age);
        RequestBody emailRequest = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody passRequest = RequestBody.create(MediaType.parse("text/plain"), password);

        // melakukan koneksi ke http addCatatan.php
        Call call = retrofitInterface.addData(nameRequest, ageRequest, emailRequest, passRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                // response code sama dengan 200
                if (response.isSuccessful()) {

                    // ubah response body ke dalam catatan list
                    Message message = (Message) response.body();

                    if (message.getStatus()) {
                        Toast.makeText(getApplicationContext(), "" + message.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "" + message.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                }


                dialog.hide();
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
