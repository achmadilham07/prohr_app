package com.example.ilham.loginlogout.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ilham.loginlogout.Constant;
import com.example.ilham.loginlogout.ContactEmp;
import com.example.ilham.loginlogout.EntryNow;
import com.example.ilham.loginlogout.Presence;
import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.RetrofitInterface;
import com.example.ilham.loginlogout.SessionManager;
import com.example.ilham.loginlogout.Users;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EntryNowActivity extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View v;
    Users users;
    private String idBeacon;
    private Constant constant = new Constant();
    private RecyclerView recycleView;
    private RecyclerView.LayoutManager layoutManager;
    private SlimAdapter adapter;
    private ArrayList<EntryNow> arrayEntryNow = new ArrayList<>();
    private SharedPreferences.Editor editorEntryNow;
    private SharedPreferences prefEntryNow;
    SessionManager session;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_entry_now, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        users = new Users(getContext());
        idBeacon = users.getId_beacon();

        session = new SessionManager(getContext());
        prefEntryNow = session.pref;
        editorEntryNow = session.editor;

        recycleView = (RecyclerView) view.findViewById(R.id.entry_not_approved);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.entry_now_swipe);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = SlimAdapter.create()
                .register(R.layout.item_list_entry_now, new SlimInjector<EntryNow>() {
                    @Override
                    public void onInject(final EntryNow data, IViewInjector injector) {
                        injector.text(R.id.entry_now_title, data.getEntry())
                                .text(R.id.entry_now_text, data.getCategory_id() +" - "+ data.getNotes())
                                .text(R.id.entry_now_created, data.getDate_created())
                                .with(R.id.item, new IViewInjector.Action() {
                                    @Override
                                    public void action(View view) {
                                        view.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View view) {
                                                Toast.makeText(getContext(), "" + data.getCategory_id(), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                });

                    }
                })
                .attachTo(recycleView);

//        getData(idBeacon);
        loadData();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(idBeacon);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2500);
    }

    public void onStop (){
        super.onStop();
        swipeRefreshLayout.setEnabled(false);
    }

    private void getData(String idBeacon) {
        swipeRefreshLayout.setRefreshing(true);

        // setting uri
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        RequestBody idBeaconRequest = RequestBody.create(MediaType.parse("text/plain"), idBeacon);

        // melakukan koneksi ke http getpresence.php
        Call call = retrofitInterface.getEntryNow(idBeacon);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                // response code sama dengan 200
                if (response.isSuccessful()) {

                    // ubah response body ke dalam catatan list
                    List<EntryNow> allList = (List<EntryNow>) response.body();

                    arrayEntryNow.clear();
                    // looping list catatan lalu dimasukkan ke arraylist di main
                    for (EntryNow entrynow : allList) {

                        arrayEntryNow.add(entrynow);
                    }

                    saveData();
                    loadData();

                } else {
                    Toast.makeText(getContext(), "Error ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(getView(), "No Internet Connected", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void saveData() {
        Gson gson = new Gson();
        String json = gson.toJson(arrayEntryNow);
        editorEntryNow.putString("entryNow", json);
        editorEntryNow.apply();
    }

    private void loadData() {
        Gson gson = new Gson();
        String json = prefEntryNow.getString("entryNow", null);
        Type type = new TypeToken<ArrayList<EntryNow>>() {
        }.getType();
        arrayEntryNow = gson.fromJson(json, type);

        if (arrayEntryNow == null) {
            arrayEntryNow = new ArrayList<>();
            getData(idBeacon);
        }

        adapter.updateData(arrayEntryNow);
        adapter.notifyDataSetChanged();
    }


}
