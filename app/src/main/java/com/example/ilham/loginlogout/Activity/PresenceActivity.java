package com.example.ilham.loginlogout.Activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilham.loginlogout.Constant;
import com.example.ilham.loginlogout.Presence;
import com.example.ilham.loginlogout.PresenceMonth;
import com.example.ilham.loginlogout.PresenceYear;
import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.RetrofitInterface;
import com.example.ilham.loginlogout.SessionManager;
import com.example.ilham.loginlogout.Users;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PresenceActivity extends AppCompatActivity implements OnItemSelectedListener {

    private Constant constant = new Constant();
    private Spinner yearSpin, monthSpin;
    private Toolbar toolbar;
    private ArrayList<String> yearArrayList = new ArrayList<String>();
    private List<String> monthArrayList = new ArrayList<String>();
    private ArrayList<Presence> allArrayList = new ArrayList<>();
    SessionManager session;
    Users users;
    private String idBeacon, year, month;
    private RecyclerView recycleView;
    private RecyclerView.LayoutManager layoutManager;
    private SlimAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presence);

        session = new SessionManager(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Presence");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        recycleView = (RecyclerView) findViewById(R.id.presence_recycleview);
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(layoutManager);

        users = new Users(getApplicationContext());
        idBeacon = users.getId_beacon();

        monthSpin = (Spinner) findViewById(R.id.spinner_month);
        yearSpin = (Spinner) findViewById(R.id.spinner_year);
        yearSpin.setOnItemSelectedListener(this);
        monthSpin.setOnItemSelectedListener(this);

        getYear(idBeacon);

        adapter = SlimAdapter.create()
                .register(R.layout.item_list_presence, new SlimInjector<Presence>() {
                    @Override
                    public void onInject(final Presence data, IViewInjector injector) {
                        injector.text(R.id.presence_date, data.getDate())
                                .text(R.id.presence_status, data.getStatus())
                                .text(R.id.presence_in, data.getIn_time())
                                .text(R.id.presence_out, data.getOut_time())

                                .with(R.id.item, new IViewInjector.Action() {
                                    @Override
                                    public void action(View view) {
                                        view.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View view) {
                                                Toast.makeText(getApplicationContext(), "" + data.getDate(), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                });

                    }
                })
                .attachTo(recycleView);

    }


    private void inilisialzeYear() {
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, yearArrayList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpin.setAdapter(adapterYear);
    }

    private void inilisialzeMonth() {
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, monthArrayList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpin.setAdapter(adapterMonth);
    }

    private void getYear(String idBeacon) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        // setting uri
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        RequestBody idBeaconRequest = RequestBody.create(MediaType.parse("text/plain"), idBeacon);

        // melakukan koneksi ke http getPresenceYear.php
        Call call = retrofitInterface.getPresenceYear(idBeacon);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                // response code sama dengan 200
                if (response.isSuccessful()) {

                    // ubah response body ke dalam catatan list
                    List<PresenceYear> catatanList = (List<PresenceYear>) response.body();
                    yearArrayList.clear();
                    yearArrayList.add("- Select a Year -");
                    for (int i = 0; i < catatanList.size(); i++) {
                        yearArrayList.add(catatanList.get(i).getYear());
                    }
                    inilisialzeYear();
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

    private void getMonth(String idBeacon, String year) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        // setting uri
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        RequestBody idBeaconRequest = RequestBody.create(MediaType.parse("text/plain"), idBeacon);
        RequestBody yearRequest = RequestBody.create(MediaType.parse("text/plain"), year);

        // melakukan koneksi ke http getPresenceMonth.php
        Call call = retrofitInterface.getPresenceMonth(idBeaconRequest, yearRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                // response code sama dengan 200
                if (response.isSuccessful()) {

                    // ubah response body ke dalam catatan list
                    List<PresenceMonth> monthList = (List<PresenceMonth>) response.body();

                    monthArrayList.clear();
                    monthArrayList.add("- Select a Month -");
                    for (int i = 0; i < monthList.size(); i++) {
                        monthArrayList.add(monthList.get(i).getMonth());
                    }

                    inilisialzeMonth();

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

    private void getData(String idBeacon, String year, String month) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        // setting uri
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        RequestBody idBeaconRequest = RequestBody.create(MediaType.parse("text/plain"), idBeacon);
        RequestBody yearRequest = RequestBody.create(MediaType.parse("text/plain"), year);
        RequestBody monthRequest = RequestBody.create(MediaType.parse("text/plain"), month);

        // melakukan koneksi ke http getpresence.php
        Call call = retrofitInterface.getPresence(idBeaconRequest, yearRequest, monthRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                // response code sama dengan 200
                if (response.isSuccessful()) {

                    // ubah response body ke dalam catatan list
                    List<Presence> allList = (List<Presence>) response.body();

                    allArrayList.clear();
                    // looping list catatan lalu dimasukkan ke arraylist di main
                    for (Presence presence : allList) {

                        allArrayList.add(presence);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                }

                adapter.updateData(allArrayList);
                adapter.notifyDataSetChanged();

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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        year = String.valueOf(yearSpin.getSelectedItem());
        month = String.valueOf(monthSpin.getSelectedItem());

        TextView parentView = (TextView) parent.getSelectedView();
        parentView.setTextColor(Color.BLACK);

        switch (parent.getId()) {
            case R.id.spinner_year:
//                Toast.makeText(this,""+ yearArray[position], Toast.LENGTH_SHORT).show();
                if (position > 0) {
                    getMonth(idBeacon, year);
                    allArrayList.clear();
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.spinner_month:
//                Toast.makeText(this,""+ yearArray[position], Toast.LENGTH_SHORT).show();
                if (position > 0)
                    getData(idBeacon, year, month);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
