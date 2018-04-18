package com.example.ilham.loginlogout.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ilham.loginlogout.Constant;
import com.example.ilham.loginlogout.Message;
import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.RetrofitInterface;
import com.example.ilham.loginlogout.Users;
import com.example.ilham.loginlogout.ViewPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EntryActivity extends AppCompatActivity {
    private Constant constant = new Constant();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Toolbar toolbar;
    private FloatingActionButton btnFloat1;
    private CardView cardView;
    private Calendar calendar;
    private Button button1, button2, button3, button;
    boolean isFromButton1 = false;
    private Animation rotate_forward, rotate_backward;
    String[] Array = {"Permit", "Leave", "Overtime", "Claim", "Loan & Installment"};
    private Calendar fromDate;
    private Calendar toDate;
    Users users;
    private String idBeacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        calendar = Calendar.getInstance(Locale.getDefault());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Entry");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        users = new Users(getApplicationContext());
        idBeacon = users.getId_beacon();

        tabLayout = (TabLayout) findViewById(R.id.entry_tablayout);
        viewPager = (ViewPager) findViewById(R.id.entry_viewpager);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        cardView = (CardView) findViewById(R.id.fab_sheet);

        btnFloat1 = (FloatingActionButton) findViewById(R.id.entry_fab1);

        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);

        ArrayAdapter adapter1 = new ArrayAdapter<>(this, R.layout.item_list_entry, Array);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                closeCardView();
                switch (position) {
                    case 0:
                        permit();
                        break;
                    case 1:
                        leave();
                        break;
                    case 2:
                        overtime();
                        break;
                    case 3:
                        claim();
                        break;
                    case 4:
                        loan();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "" + Array[position], Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        btnFloat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonPressed1();
            }
        });

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeCardView();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                animateFab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Add Fragment Here
        adapter.DeleteAllFragment();
        adapter.AddFragment(new EntryNowActivity(), "Now");
        adapter.AddFragment(new EntryRecentlyActivity(), "Recently");

        viewPager.setAdapter(adapter);
        tabLayout.removeAllTabs();
        tabLayout.setupWithViewPager(viewPager);

    }

    private void add_permit(String category_id, String date_begin, String date_end, String notes) {
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

        RequestBody idBeaconRequest = RequestBody.create(MediaType.parse("text/plain"), idBeacon);
        RequestBody category_idRequest = RequestBody.create(MediaType.parse("text/plain"), category_id);
        RequestBody date_beginRequest = RequestBody.create(MediaType.parse("text/plain"), date_begin);
        RequestBody date_endRequest = RequestBody.create(MediaType.parse("text/plain"), date_end);
        RequestBody notesRequest = RequestBody.create(MediaType.parse("text/plain"), notes);

        // melakukan koneksi ke http addCatatan.php
        Call call = retrofitInterface.addPermit(idBeaconRequest, category_idRequest, date_beginRequest, date_endRequest, notesRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                // response code sama dengan 200
                if (response.isSuccessful()) {

                    // ubah response body ke dalam catatan list
                    Message message = (Message) response.body();

                    if (message.getStatus()) {
                        Toast.makeText(getApplicationContext(), "" + message.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "" + message.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                }
                viewPager.setAdapter(adapter);
                dialog.hide();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "No Internet Connected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void add_leave(String category_id, String date_begin, String date_end, String notes) {
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

        RequestBody idBeaconRequest = RequestBody.create(MediaType.parse("text/plain"), idBeacon);
        RequestBody category_idRequest = RequestBody.create(MediaType.parse("text/plain"), category_id);
        RequestBody date_beginRequest = RequestBody.create(MediaType.parse("text/plain"), date_begin);
        RequestBody date_endRequest = RequestBody.create(MediaType.parse("text/plain"), date_end);
        RequestBody notesRequest = RequestBody.create(MediaType.parse("text/plain"), notes);

        // melakukan koneksi ke http addCatatan.php
        Call call = retrofitInterface.addLeave(idBeaconRequest, category_idRequest, date_beginRequest, date_endRequest, notesRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                // response code sama dengan 200
                if (response.isSuccessful()) {

                    // ubah response body ke dalam catatan list
                    Message message = (Message) response.body();

                    if (message.getStatus()) {
                        Toast.makeText(getApplicationContext(), "" + message.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "No Internet Connected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void add_overtime(String datefrom,String dateto, String time_begin, String time_end, String notes, String type) {
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

        RequestBody idBeaconRequest = RequestBody.create(MediaType.parse("text/plain"), idBeacon);
        RequestBody dateRequest = RequestBody.create(MediaType.parse("text/plain"), datefrom);
        RequestBody time_beginRequest = RequestBody.create(MediaType.parse("text/plain"), time_begin);
        RequestBody time_endRequest = RequestBody.create(MediaType.parse("text/plain"), time_end);
        RequestBody notesRequest = RequestBody.create(MediaType.parse("text/plain"), notes);

        // melakukan koneksi ke http addCatatan.php
        Call call = retrofitInterface.addOvertime(idBeaconRequest, dateRequest, time_beginRequest, time_endRequest, notesRequest);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                // response code sama dengan 200
                if (response.isSuccessful()) {

                    // ubah response body ke dalam catatan list
                    Message message = (Message) response.body();

                    if (message.getStatus()) {
                        Toast.makeText(getApplicationContext(), "" + message.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "No Internet Connected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void permit() {
        final LayoutInflater inflater = getLayoutInflater();
        final View mView = inflater.inflate(R.layout.item_entry_permit, null);
        final Spinner spinner = (Spinner) mView.findViewById(R.id.permit_type);
        final TextInputEditText information = (TextInputEditText) mView.findViewById(R.id.permit_info);
        final AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle("Permit")
                .setPositiveButton(android.R.string.yes, null) //di-set biar gak auto close
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {// diisi kosong aja
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
                        if (!information.getText().toString().isEmpty()
                                && !button1.getText().toString().isEmpty()
                                && !button2.getText().toString().isEmpty()) {
                            if (toDate.getTimeInMillis() - fromDate.getTimeInMillis() < 0) {
                                Toast.makeText(getApplicationContext(), "please fix the date", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String Information = information.getText().toString();
                            String DateFrom = button1.getText().toString();
                            String JenisIjin = spinner.getSelectedItem().toString();
                            String DateTo = button2.getText().toString();
                            Log.i("AL-", Information + " " + DateFrom + " " + DateTo + " " + JenisIjin);

                            add_permit(JenisIjin, DateFrom, DateTo, Information);
                            // ^^^^^^^^^^ data yang dikirim ke database.. NOW seharusnya ngambil nilai di database
                            builder.dismiss(); //close builder(dialog)
                        } else
                            Toast.makeText(getApplicationContext(), "Please fill the context", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        button1 = (Button) mView.findViewById(R.id.datefrom);
        button2 = (Button) mView.findViewById(R.id.dateto);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AL-", "Hello");
                isFromButton1 = true;
                setdate();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AL-", "Hello");
                isFromButton1 = false;
                setdate();

            }
        });
        builder.setView(mView);
        builder.show();
    }

    private void loan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.item_entry_loan, null))
                .setTitle("Loan")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        Dialog alert = builder.create();
        alert.show();
    }

    private void claim() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.item_entry_claim, null))
                .setTitle("Claim")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        Dialog alert = builder.create();
        alert.show();
    }

    private void overtime() {
        LayoutInflater inflater = getLayoutInflater();
        final View mView = inflater.inflate(R.layout.item_entry_overtime, null);
        final Spinner spinner = (Spinner) mView.findViewById(R.id.overtime_tipe);
        final TextInputEditText information = (TextInputEditText) mView.findViewById(R.id.overtime_info);
        final AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle("Overtime")
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

                        if (!information.getText().toString().isEmpty()
                                && !button1.getText().toString().isEmpty()
                                && !button2.getText().toString().isEmpty()
                                && !button.getText().toString().isEmpty()
                                && !button3.getText().toString().isEmpty()) {
                            String Information = information.getText().toString();
                            String type = spinner.toString();
                            String Datefrom = button1.getText().toString();
                            String Dateto = button2.getText().toString();
                            String From = button.getText().toString();
                            String To = button3.getText().toString();
                            Log.i("AL-", Information + " " + Datefrom+" "+Dateto+ " " + From + " " + To+ " "+type);

                            add_overtime(Datefrom,Dateto, From, To, Information , type);
                            // ^^^^^^^^^^ data yang dikirim ke database.. NOW seharusnya ngambil nilai di database
                            builder.dismiss(); //close builder(dialog)
                        } else
                            Toast.makeText(getApplicationContext(), "Please fill the context", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        button1 = (Button) mView.findViewById(R.id.overtime_date_from);
        button2 = (Button) mView.findViewById(R.id.overtime_date_to);
        button3 = (Button) mView.findViewById(R.id.overtime_to);
        button = (Button) mView.findViewById(R.id.overtime_from);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromButton1 = false;
                setdate();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AL-", "Hello");
                isFromButton1 = true;
                setdate();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromButton1 = true;
                setTime();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFromButton1 = false;
                setTime();
            }
        });
        builder.setView(mView);
        builder.show();
    }

    private void leave() {
        final LayoutInflater inflater = getLayoutInflater();
        final View mView = inflater.inflate(R.layout.item_entry_leave, null);
        final Spinner spinner = (Spinner) mView.findViewById(R.id.leave_cuti);
        final TextInputEditText information = (TextInputEditText) mView.findViewById(R.id.leave_info);
        final AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle("Leave")
                .setPositiveButton(android.R.string.yes, null) //di-set biar gak auto close
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {// diisi kosong aja
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
                        if (!information.getText().toString().isEmpty()
                                && !button1.getText().toString().isEmpty()
                                && !button2.getText().toString().isEmpty()) {
                            if (toDate.getTimeInMillis() - fromDate.getTimeInMillis() < 0) {
                                Toast.makeText(getApplicationContext(), "please fix the date", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String Information = information.getText().toString();
                            String DateFrom = button1.getText().toString();
                            String JenisCuti = spinner.getSelectedItem().toString();
                            String DateTo = button2.getText().toString();
                            Log.i("AL-", Information + " " + DateFrom + " " + DateTo + " " + JenisCuti);

                            add_leave(JenisCuti, DateFrom, DateTo, Information);
                            // ^^^^^^^^^^ data yang dikirim ke database.. NOW seharusnya ngambil nilai di database
                            builder.dismiss(); //close builder(dialog)
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Please fill the context", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        button1 = (Button) mView.findViewById(R.id.datefrom);
        button2 = (Button) mView.findViewById(R.id.dateto);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AL-", "Hello");
                isFromButton1 = true;
                setdate();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AL-", "Hello");
                isFromButton1 = false;
                setdate();

            }
        });
        builder.setView(mView);
        builder.show();
    }

    public void closeCardView() {
        btnFloat1.startAnimation(rotate_backward);
        YoYo.with(Techniques.SlideOutDown).duration(500).playOn(cardView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cardView.setVisibility(View.GONE);
            }
        }, 500);
    }

    public void ButtonPressed1() {
        btnFloat1.startAnimation(rotate_forward);
        if (cardView.getVisibility() == View.GONE) {
            cardView.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInUp).duration(500).playOn(cardView);
        } else {
            closeCardView();
        }
    }

    private void setdate() {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "Date Picker");
    }

    private void setTime() {
        DialogFragment dialogFragment = new TimePickerFragment();
        dialogFragment.show(getFragmentManager(), "Time Picker");
    }

    private void animateFab(int position) {
        switch (position) {
            case 0:
                btnFloat1.show();
                btnFloat1.setEnabled(true);
                cardView.setVisibility(View.GONE);
                break;
            case 1:
                btnFloat1.hide();
                btnFloat1.setEnabled(false);
                cardView.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (cardView.getVisibility() == View.VISIBLE) {
            closeCardView();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("ValidFragment")
    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
            // Set hari ini hari minim
            dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());
            return dpd;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();
            SimpleDateFormat df = new SimpleDateFormat("y-M-d");
            String formattedDate = df.format(chosenDate);
            Log.i("AL-", formattedDate);
            if (isFromButton1) {
                button1.setText(formattedDate);
                fromDate = cal;
                if(button2.getText().toString().isEmpty()){
                    toDate = cal;
                    button2.setText(formattedDate);
                }
            } else {
                button2.setText(formattedDate);
                toDate = cal;
            }

        }
    }

    @SuppressLint("ValidFragment")
    public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(0);
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            Date chosenDate = c.getTime();
            SimpleDateFormat df = new SimpleDateFormat("kk:mm");
            String formattedDate = df.format(chosenDate);
            Log.i("AL-", formattedDate);
            if (isFromButton1) {
                button.setText(formattedDate);
            } else {
                button3.setText(formattedDate);
            }
        }
    }
}
