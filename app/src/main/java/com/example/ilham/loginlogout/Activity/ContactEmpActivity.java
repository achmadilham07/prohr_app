package com.example.ilham.loginlogout.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.daimajia.swipe.SwipeLayout;
import com.example.ilham.loginlogout.Constant;
import com.example.ilham.loginlogout.ContactEmp;
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

public class ContactEmpActivity extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    Users users;
    View v;
    private Constant constant = new Constant();
    SessionManager session;
    private String idBeacon, uid;
    private ArrayList<ContactEmp> contactList = new ArrayList<>(); //selalu di-init gini jangan null biar gak error >.<
    private ArrayList<ContactEmp> searched = new ArrayList<>(); // buat duplikasi search
    private RecyclerView recycleView;
    private RecyclerView.LayoutManager layoutManager;
    private SlimAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreferences.Editor editorContactEmp;
    private SharedPreferences prefContactEmp;
    private ImageView contactimg;
    private SearchView searchView;
    private TextDrawable[] drawable;
    private SwipeLayout swipeLayout;
    private LinearLayout call_contact, email_contact;
    ColorGenerator generator = ColorGenerator.MATERIAL;
    int color;

    public ContactEmpActivity() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_contact_emp, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = (SearchView) view.findViewById(R.id.contact_search);
        getActivity().setTitle("Contact Employee");
        setHasOptionsMenu(true);

        users = new Users(getContext());
        idBeacon = users.getId_beacon();
        uid = users.getUid();

        session = new SessionManager(getContext());
        prefContactEmp = session.pref;
        editorContactEmp = session.editor;

        recycleView = (RecyclerView) view.findViewById(R.id.contactEmp_recycleview);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.contactEmp_swipe);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = SlimAdapter.create()
                .register(R.layout.item_list_contact_emp, new SlimInjector<ContactEmp>() {
                    @Override
                    public void onInject(final ContactEmp data, IViewInjector injector) {
                        injector.text(R.id.contact_name, data.getFullname())
                                .text(R.id.contact_phone, data.getPhoneNum())
                                .text(R.id.contact_email, data.getEmail())
                                .with(R.id.item, new IViewInjector.Action() {
                                    @Override
                                    public void action(final View viewkotak) {

                                        swipeLayout = (SwipeLayout) viewkotak.findViewById(R.id.swipe);
                                        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
                                        swipeLayout.setClickToClose(true);
//                                        swipeLayout.addDrag(SwipeLayout.DragEdge.Bottom, viewkotak.findViewById(R.id.rightSwipe));

                                        call_contact = (LinearLayout) viewkotak.findViewById(R.id.call_emp);
                                        call_contact.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Snackbar.make(viewkotak, "" + data.getPhoneNum(), Snackbar.LENGTH_SHORT).show();
                                            }
                                        });
                                        email_contact = (LinearLayout) viewkotak.findViewById(R.id.email_emp);
                                        email_contact.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Snackbar.make(viewkotak, "" + data.getEmail(), Snackbar.LENGTH_SHORT).show();
                                            }
                                        });

                                        contactimg = (ImageView) viewkotak.findViewById(R.id.contact_img);
                                        color = generator.getRandomColor();

                                        String firstchar = "";
                                        firstchar = String.valueOf(data.getFullname().toString().charAt(0));
                                        final TextDrawable drawable = TextDrawable.builder().buildRound(firstchar, color);
                                        contactimg.setImageDrawable(drawable);

                                        LinearLayout viewContact = (LinearLayout) viewkotak.findViewById(R.id.view_contact);
                                        viewContact.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View view) {
                                                Toast.makeText(getContext(), "" + data.getFullname(), Toast.LENGTH_SHORT).show();
                                                Bundle bundle = new Bundle();
                                                Intent i = new Intent(getContext(), ContactEmpViewActivity.class);
                                                i.putExtra("fullname", data.getFullname());
                                                i.putExtra("uname", data.getUname());
                                                i.putExtra("birthday_date", data.getBirthday_date());
                                                i.putExtra("phoneNum", data.getPhoneNum());
                                                i.putExtra("address", data.getAddress());
                                                i.putExtra("city", data.getCity());
                                                i.putExtra("state", data.getState());
                                                i.putExtra("country", data.getCountry());
                                                i.putExtra("email", data.getEmail());
                                                i.putExtra("position", data.getPosition());
                                                i.putExtra("division", data.getDivision());

                                                contactimg.buildDrawingCache();
                                                Bitmap image = contactimg.getDrawingCache();
                                                bundle.putParcelable("image", image);
                                                i.putExtras(bundle);

//                                                i.setType("image/*");
                                                startActivity(i);
                                            }
                                        });
                                    }
                                });

                    }
                })
                .attachTo(recycleView);

        loadData();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void refreshContent() {
        swipeRefreshLayout.setRefreshing(true);

        // setting uri
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        RequestBody idBeaconRequest = RequestBody.create(MediaType.parse("text/plain"), idBeacon);
        RequestBody uidRequest = RequestBody.create(MediaType.parse("text/plain"), uid);

        // melakukan koneksi ke http getpresence.php
        Call call = retrofitInterface.getContactEmp(uidRequest, idBeaconRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                // response code sama dengan 200
                if (response.isSuccessful()) {

                    // ubah response body ke dalam catatan list
                    List<ContactEmp> allList = (List<ContactEmp>) response.body();

                    contactList.clear();
                    // looping list catatan lalu dimasukkan ke arraylist di main
                    for (ContactEmp contactEmp : allList) {
                        contactList.add(contactEmp);
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
        String json = gson.toJson(contactList);
        editorContactEmp.putString("contactEmp", json);
        editorContactEmp.apply();
    }

    private void loadData() {
        Gson gson = new Gson();
        String json = prefContactEmp.getString("contactEmp", null);
        Type type = new TypeToken<ArrayList<ContactEmp>>() {
        }.getType();
        contactList = gson.fromJson(json, type);

        if (contactList == null) {
            contactList = new ArrayList<>();
            refreshContent();
        }

        adapter.updateData(contactList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshContent();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2500);
    }

    public void onStop (){
        super.onStop();
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.contact_search:
                Snackbar.make(getView(),"Search", Snackbar.LENGTH_SHORT).show();
                searchView.setVisibility(View.VISIBLE);
                searchView.setActivated(true);
                searchView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                searchView.onActionViewExpanded();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        searched.clear();
                        for (ContactEmp member : contactList){
                            if(member.getFullname().toLowerCase().contains(s.toLowerCase()))
                                searched.add(member);
                        }
                        adapter.updateData(searched);
                        adapter.notifyDataSetChanged();
                        return false;
                    }
                });

                break;
            case R.id.contact_export:
                Snackbar.make(getView(),"Export", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.contact_sort_by:
                Snackbar.make(getView(),"Sort by", Snackbar.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
