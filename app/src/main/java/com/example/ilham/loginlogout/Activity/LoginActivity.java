package com.example.ilham.loginlogout.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ilham.loginlogout.Constant;
import com.example.ilham.loginlogout.Message;
import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.RetrofitInterface;
import com.example.ilham.loginlogout.SessionManager;
import com.example.ilham.loginlogout.Users;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    private Constant constant = new Constant();
    private TextInputEditText email, password;
    private TextView forgotPassword;
    private Button submit;
    boolean doubleBackToExitPressedOnce = false;


    SessionManager session;
    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());
        if (session.checkLogin2()) {
            finish();
        }

        users = new Users(getApplicationContext());

        email = (TextInputEditText) findViewById(R.id.emailLogin);
        password = (TextInputEditText) findViewById(R.id.passwordLogin);

        forgotPassword = (TextView) findViewById(R.id.checkBox);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestNewPassword();
            }
        });


        submit = (Button) findViewById(R.id.submitLogin);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText()))){
                    String Email = email.getText().toString();
                    String Password = password.getText().toString();
                    findData(Email, Password);
                }
                else{
                    YoYo.with(Techniques.Shake).playOn(email);
                    YoYo.with(Techniques.Shake).playOn(password);
                    Toast.makeText(getApplicationContext(), "Please fill the required fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void requestNewPassword() {
        LayoutInflater inflater = getLayoutInflater();
        final View mView = inflater.inflate(R.layout.item_requestpassword, null);
        final TextInputEditText parameter = (TextInputEditText) mView.findViewById(R.id.input_parameter);
        final AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle("Forgot Password")
                .setPositiveButton(android.R.string.yes, null)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();

        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button buttonPositive = builder.getButton(AlertDialog.BUTTON_POSITIVE);
                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!parameter.getText().toString().isEmpty()) {
                            String Name = parameter.getText().toString();
                            // ^^^^^^^^^^ data yang dikirim ke database.. NOW seharusnya ngambil nilai di database
                            builder.dismiss(); //close builder(dialog)
                            Toast.makeText(getApplicationContext(), "Please Check your Email", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Please fill the context", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setView(mView);
        builder.show();
    }

    private void findData(final String email, final String password) {
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

        RequestBody emailRequest = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody passRequest = RequestBody.create(MediaType.parse("text/plain"), password);

        // melakukan koneksi ke http addCatatan.php
        Call call = retrofitInterface.findData(emailRequest, passRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                // response code sama dengan 200
                if (response.isSuccessful()) {

                    // ubah response body ke dalam catatan list
                    Message message = (Message) response.body();

                    if (message.getStatus()) {
                        Map<String, String> user = message.getUsers();
                        users.setUsers(user);
                        session.createLoginSession(email);
                        Toast.makeText(getApplicationContext(), ""+ message.getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        finish();
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
                dialog.dismiss();
                Snackbar.make(getCurrentFocus(), "No Internet Connected", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
