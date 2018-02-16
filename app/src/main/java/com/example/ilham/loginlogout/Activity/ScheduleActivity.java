package com.example.ilham.loginlogout.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilham.loginlogout.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class ScheduleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CompactCalendarView compactCalendarView;
    private TextView textView,textView2;
    private Calendar calendar;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM-yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance(Locale.getDefault());
        setContentView(R.layout.activity_schedule);
        compactCalendarView =(CompactCalendarView) findViewById(R.id.compactcalendar_view);
        textView = (TextView) findViewById(R.id.textView4);
        textView2 = (TextView) findViewById(R.id.textView6);
        textView.setText(dateFormatForMonth.format(calendar.getTime()));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        //perlu koneksi ke database. Database berisi hari, bulan, tahun, dan isi evemt
        addEvent(10,1,2018,"Test Coba Event"); // 11 Februari 2018

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                textView2.setText(dateClicked.toString());
                //menampilkan event..
                List<Event> eventnya = compactCalendarView.getEvents(dateClicked);
                if(eventnya.size() != 0)
                    for(int i=0; i<eventnya.size(); i++)
                        Toast.makeText(getApplicationContext(),eventnya.get(i).getData().toString(),Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Tidak ada event",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                textView.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });
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

