package com.example.ilham.loginlogout.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
<<<<<<< HEAD
import android.widget.Spinner;
=======
>>>>>>> parent of 99bc055... Modify leave and overtime
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.ViewPagerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EntryActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Toolbar toolbar;
    private FloatingActionButton btnFloat1;
    private CardView cardView;
    private Calendar calendar;
<<<<<<< HEAD
    private Spinner spinner_type;
    private Button button1, button2;
    boolean isFromButton1 = false, gooddate, datepressed = false;
    private Animation rotate_forward, rotate_backward;
    String[] Array = {"Permit", "Leave", "Overtime", "Claim", "Loan & Installment"};
=======
    private Button button1, button2;
    boolean isFromButton1 = false, gooddate, datepressed = false;
    private Animation rotate_forward, rotate_backward;
    String[] Array = {"Leave", "Overtime", "Claim", "Loan & Installment"};
>>>>>>> parent of 99bc055... Modify leave and overtime
    private Calendar fromDate;
    private Calendar toDate;

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

    private void permit() {
        final LayoutInflater inflater = getLayoutInflater();
        final View mView = inflater.inflate(R.layout.item_entry_permit, null);
        final TextInputEditText information = (TextInputEditText) mView.findViewById(R.id.permit_info);
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
                        gooddate = true;
                        if (datepressed && (toDate.getTimeInMillis() - fromDate.getTimeInMillis() < 0)) {
                            Toast.makeText(getApplicationContext(), "please fix the date", Toast.LENGTH_SHORT).show();
                            gooddate = false;
                        }
                        if (!information.getText().toString().isEmpty()
                                && !button1.getText().toString().isEmpty()
                                && !button2.getText().toString().isEmpty()
                                && !spinner_type.getSelectedItem().toString().isEmpty()
                                && gooddate) {
                            String type = spinner_type.getSelectedItem().toString();
                            String Information = information.getText().toString();
                            String DateFrom = button1.getText().toString();
                            String DateTo = button2.getText().toString();
                            Log.i("AL-", type + " " + Information + " " + DateFrom + " " + DateTo);
                            // ^^^^^^^^^^ data yang dikirim ke database.. NOW seharusnya ngambil nilai di database
                            builder.dismiss(); //close builder(dialog)
                        } else if (gooddate)
                            Toast.makeText(getApplicationContext(), "Please fill the context", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        spinner_type = (Spinner) mView.findViewById(R.id.permit_type);
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
                .setTitle("Loan & Installment")
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
        final TextInputEditText name = (TextInputEditText) mView.findViewById(R.id.overtime_name);
        final TextInputEditText division = (TextInputEditText) mView.findViewById(R.id.overtime_division);
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
<<<<<<< HEAD
                        if (!information.getText().toString().isEmpty()
                                && !button1.getText().toString().isEmpty()) {
                            String Information = information.getText().toString();
                            String Date = button1.getText().toString();
                            Log.i("AL-", Information + " " + Date);
=======
                        if (!name.getText().toString().isEmpty() //wajib diisi
                                && !division.getText().toString().isEmpty()
                                && !information.getText().toString().isEmpty()
                                && !button1.getText().toString().isEmpty()) {
                            String Name = name.getText().toString();
                            String Division = division.getText().toString();
                            String Information = information.getText().toString();
                            String Date = button1.getText().toString();
                            Log.i("AL-", Name+" "+Division+" "+Information+" "+Date);
>>>>>>> parent of 99bc055... Modify leave and overtime
                            // ^^^^^^^^^^ data yang dikirim ke database.. NOW seharusnya ngambil nilai di database
                            builder.dismiss(); //close builder(dialog)
                        } else
                            Toast.makeText(getApplicationContext(), "Please fill the context", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        button1 = (Button) mView.findViewById(R.id.overtime_date);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AL-", "Hello");
                isFromButton1 = true;
                setdate();
            }
        });
        builder.setView(mView);
        builder.show();
    }

    private void leave() {
        final LayoutInflater inflater = getLayoutInflater();
        final View mView = inflater.inflate(R.layout.item_entry_leave, null);
<<<<<<< HEAD
=======
        final TextInputEditText name = (TextInputEditText) mView.findViewById(R.id.leave_name);
        final TextInputEditText division = (TextInputEditText) mView.findViewById(R.id.leave_division);
>>>>>>> parent of 99bc055... Modify leave and overtime
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
                        gooddate = true;
                        if (datepressed && (toDate.getTimeInMillis() - fromDate.getTimeInMillis() < 0)) {
                            Toast.makeText(getApplicationContext(), "please fix the date", Toast.LENGTH_SHORT).show();
                            gooddate = false;
                        }
                        if (!name.getText().toString().isEmpty() //wajib diisi
                                && !division.getText().toString().isEmpty()
                                && !information.getText().toString().isEmpty()
                                && !button1.getText().toString().isEmpty()
                                && !button2.getText().toString().isEmpty()
                                && !spinner_type.getSelectedItem().toString().isEmpty()
                                && gooddate) {
<<<<<<< HEAD
                            String type = spinner_type.getSelectedItem().toString();
                            String Information = information.getText().toString();
                            String DateFrom = button1.getText().toString();
                            String DateTo = button2.getText().toString();
                            Log.i("AL-", type + " " + Information + " " + DateFrom + " " + DateTo);
=======
                            String Name = name.getText().toString();
                            String Division = division.getText().toString();
                            String Information = information.getText().toString();
                            String DateFrom = button1.getText().toString();
                            String DateTo = button2.getText().toString();
                            Log.i("AL-", Name+" "+Division+" "+Information+" "+DateFrom+" "+DateTo);
>>>>>>> parent of 99bc055... Modify leave and overtime
                            // ^^^^^^^^^^ data yang dikirim ke database.. NOW seharusnya ngambil nilai di database
                            builder.dismiss(); //close builder(dialog)
                        } else if (gooddate)
                            Toast.makeText(getApplicationContext(), "Please fill the context", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        spinner_type = (Spinner) mView.findViewById(R.id.leave_cuti);
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

    private void animateFab(int position) {
        switch (position) {
            case 0:
                btnFloat1.show();
                cardView.setVisibility(View.GONE);
                break;
            case 1:
                btnFloat1.hide();
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
            DateFormat df = new SimpleDateFormat("d-M-y");
            String formattedDate = df.format(chosenDate);
            Log.i("AL-", formattedDate);
            datepressed = true;
            if (isFromButton1) {
                button1.setText(formattedDate);
                fromDate = cal;
            } else {
                button2.setText(formattedDate);
                toDate = cal;
            }

        }
    }
}
