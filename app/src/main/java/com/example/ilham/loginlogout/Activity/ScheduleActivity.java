package com.example.ilham.loginlogout.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilham.loginlogout.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class ScheduleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CompactCalendarView compactCalendarView;
    private TextView textView;
    private Calendar calendar;
    private ListView listView;
    private String mydate;
    private FloatingActionButton Fbutton;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM-yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<String> allevent = new ArrayList<>();
        calendar = Calendar.getInstance(Locale.getDefault());
        setContentView(R.layout.activity_schedule);

        compactCalendarView =(CompactCalendarView) findViewById(R.id.compactcalendar_view);
        textView = (TextView) findViewById(R.id.textView4);
        listView = (ListView) findViewById(R.id.Mylst);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Fbutton = (FloatingActionButton) findViewById(R.id.entry_fab);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        //perlu koneksi ke database. Database berisi hari, bulan, tahun, dan isi evemt

        mydate = dateFormat.format(calendar.getTime());
        textView.setText(dateFormatForMonth.format(calendar.getTime()));
        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allevent);
        listView.setAdapter(adapter);
        addEvent(10,1,2018,"Test Coba Event"); // 11 Februari 2018
        addEvent(10,1,2018,"Test Coba Event2"); // 11 Februari 2018
        addEvent(10,1,2018,"Test Coba Event3"); // 11 Februari 2018
        addEvent(13,1,2018,"Test Coba Event"); // 11 Februari 2018
        addEvent(13,1,2018,"Test Coba Event2"); // 11 Februari 2018
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                //menampilkan event..
                List<Event> eventnya = compactCalendarView.getEvents(dateClicked);
                mydate = dateFormat.format(dateClicked.getTime());
                if(eventnya != null) {
                    allevent.clear();
                    if(eventnya.size() < 1)
                        allevent.add("No Event This Day");
                    for (Event event : eventnya) {
                        allevent.add(event.getData().toString());
                        //Toast.makeText(getApplicationContext(), event.getData().toString(), Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                }
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
                addEvent(mydate);
            }
        });
    }

    private void addEvent(String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.item_event, null))
        .setTitle(date)
        .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        Dialog alert = builder.create();
        alert.show();
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

    private void DefaultTime (Calendar calendar) {
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

