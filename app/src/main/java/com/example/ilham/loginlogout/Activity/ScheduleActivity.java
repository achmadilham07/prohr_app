package com.example.ilham.loginlogout.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilham.loginlogout.Constant;
import com.example.ilham.loginlogout.ContactEmp;
import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.RetrofitInterface;
import com.example.ilham.loginlogout.SessionManager;
import com.example.ilham.loginlogout.Users;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScheduleActivity extends AppCompatActivity {

    private Constant constant = new Constant();
    Users users;
    private String idBeacon, uid;
    private Toolbar toolbar;
    private CompactCalendarView compactCalendarView;
    private TextView textView;
    private Calendar calendar, dateclick;
    private ListView listView;
    private Date mydate;
    private FloatingActionButton Fbutton;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM-yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    private SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
    private SimpleDateFormat monthFormat = new SimpleDateFormat("M", Locale.getDefault());
    private SimpleDateFormat yearFormat = new SimpleDateFormat("y", Locale.getDefault());
    private List<String> allevent;
    private ArrayAdapter adapter;
    private int day = 0;
    private int month = 0;
    private int year = 0;
    String [] nameList;
    boolean[] checkedItem;
    private ImageButton previousMonth, nextMonth;
    private SharedPreferences prefSchedule;
    private SharedPreferences.Editor editorSchedule;
    SessionManager session;
    private ArrayList<ContactEmp> contactList = new ArrayList<>();
    private ArrayList<String> listNameContact = new ArrayList<>();
    private ArrayList<Integer> mUserItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allevent = new ArrayList<>();
        calendar = Calendar.getInstance(Locale.getDefault());
        setContentView(R.layout.activity_schedule);

        users = new Users(getApplicationContext());
        idBeacon = users.getId_beacon();
        uid = users.getUid();

        session = new SessionManager(getApplicationContext());
        prefSchedule = session.pref;
        editorSchedule = session.editor;

        loadData();

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        textView = (TextView) findViewById(R.id.textView4);
        listView = (ListView) findViewById(R.id.Mylst);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Fbutton = (FloatingActionButton) findViewById(R.id.entry_fab);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        nextMonth = (ImageButton) findViewById(R.id.schedule_right);
        previousMonth = (ImageButton) findViewById(R.id.schedule_left);
        previousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showPreviousMonth();
            }
        });
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showNextMonth();
            }
        });

        mydate = calendar.getTime();
        textView.setText(dateFormatForMonth.format(calendar.getTime()));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allevent);
        listView.setAdapter(adapter);
        //perlu koneksi ke database. Database berisi hari, bulan, tahun, dan isi evemt
        addEvent(10, 1, 2018, "Test Coba Event"); // 11 Februari 2018
        addEvent(10, 1, 2018, "Test Coba Event2"); // 11 Februari 2018
        addEvent(10, 1, 2018, "Test Coba Event3"); // 11 Februari 2018
        addEvent(13, 1, 2018, "Test Coba Event"); // 14 Februari 2018
        addEvent(13, 1, 2018, "Test Coba Event2"); // 14 Februari 2018
        showEvent(mydate);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                mydate = dateClicked;
                showEvent(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                textView.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Fbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputEvent(mydate);
            }
        });
    }

    private void loadData() {
        Gson gson = new Gson();
        String json = prefSchedule.getString("contactEmp", null);
        Type type = new TypeToken<ArrayList<ContactEmp>>() {
        }.getType();
        contactList = gson.fromJson(json, type);

        if (contactList == null) {
            contactList = new ArrayList<>();
            refreshContact();
        }

        for(ContactEmp member : contactList)
            listNameContact.add(member.getFullname());
        nameList = listNameContact.toArray(new String[listNameContact.size()]);
    }

    private void refreshContact() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

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
                    Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                }
                dialog.hide();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                dialog.hide();
                Snackbar.make(getCurrentFocus(), "No Internet Connected", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void saveData() {
        Gson gson = new Gson();
        String json = gson.toJson(contactList);
        editorSchedule.putString("contactEmp", json);
        editorSchedule.apply();
    }

    private void inputEvent(final Date date) {
        String newdate = dateFormat.format(date.getTime());
        month = Integer.parseInt(monthFormat.format(date.getTime())) - 1;
        day = Integer.parseInt(dayFormat.format(date.getTime())) - 1;
        year = Integer.parseInt(yearFormat.format(date.getTime()));
        mUserItems.clear();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mView = inflater.inflate(R.layout.item_event, null);
        final TextInputEditText event_title = (TextInputEditText) mView.findViewById(R.id.event_title);
        final TextInputEditText event_input = (TextInputEditText) mView.findViewById(R.id.event_info);
        final TextInputEditText event_with = (TextInputEditText) mView.findViewById(R.id.event_with);
        final CheckBox event_alone = (CheckBox) mView.findViewById(R.id.event_check);
        checkedItem = new boolean[listNameContact.size()];
        event_alone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    event_with.setText("");
                    mUserItems.clear();
                    Arrays.fill(checkedItem,false);
                }
            }
        });
        event_with.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriends(event_with);
                event_alone.setChecked(false);
            }
        });
        builder.setView(mView)
                .setTitle(newdate)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String setTitle = event_title.getText().toString();
                        String setEvent = event_input.getText().toString();
                        String ListName = event_with.getText().toString();
                        if(event_alone.isChecked())
                            ListName = "";
                        if (!setTitle.isEmpty() && !setEvent.isEmpty() &&
                                (!ListName.isEmpty() || event_alone.isChecked())){
                            addEvent(day, month, year, setEvent); // nilai - nilai ini masuk ke database
                            Toast.makeText(getApplicationContext(), setTitle+" "+setEvent+" "+ListName, Toast.LENGTH_SHORT).show();
                        }

                        showEvent(date);
                    }
                });
        Dialog alert = builder.create();
        alert.show();
    }

    private void showEvent(Date date) {
        //menampilkan event..
        List<Event> eventnya = compactCalendarView.getEvents(date);
        if (eventnya != null) {
            allevent.clear();
            if (eventnya.size() < 1)
                allevent.add("No Event This Day");
            for (Event event : eventnya) {
                allevent.add(event.getData().toString());
                //Toast.makeText(getApplicationContext(), event.getData().toString(), Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void addEvent(int DoM, int month, int year, String string) {
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        calendar.setTime(firstDayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.add(Calendar.DATE, DoM);
        DefaultTime(calendar);
        long timeInMillis = calendar.getTimeInMillis();
        Event events = new Event(Color.argb(255, 169, 68, 65), timeInMillis, string);
        compactCalendarView.addEvent(events);
    }

    private void  addFriends(final TextInputEditText event_with){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ScheduleActivity.this);
        mBuilder.setTitle("Contact List");
        mBuilder.setMultiChoiceItems(nameList, checkedItem, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if(b)
                    mUserItems.add(i);
                else
                    mUserItems.remove(Integer.valueOf(i));
            }
        });
        mBuilder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int x) {
                String nameChecked = "";
                for (int i = 0; i < mUserItems.size(); i++){
                    nameChecked += listNameContact.get(mUserItems.get(i));
                    if (i != mUserItems.size() - 1)
                        nameChecked += ", ";
                }
                event_with.setText(nameChecked);
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }

    private void DefaultTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

