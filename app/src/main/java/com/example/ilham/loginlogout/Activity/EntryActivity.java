package com.example.ilham.loginlogout.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.ViewPagerAdapter;

public class EntryActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Toolbar toolbar;
    private FloatingActionButton btnFloat1, btnFloat2;
    private CardView cardView;
    private Animation rotate_forward,rotate_backward;
    String[] Array = {"Leave", "Overtime", "Claim", "Loan & Installment"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

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
        btnFloat2 = (FloatingActionButton) findViewById(R.id.entry_fab2);

        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        ArrayAdapter adapter1 = new ArrayAdapter<>(this, R.layout.item_list_entry, Array);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                closeCardView();
                switch (position) {
                    case 0:
                        leave();
                        break;
                    case 1:
                        overtime();
                        break;
                    case 2:
                        claim();
                        break;
                    case 3:
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

        btnFloat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.item_entry_overtime, null))
                .setTitle("Overtime")
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

    private void leave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.item_entry_leave, null))
                .setTitle("Leave")
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

    private void animateFab(int position) {
        switch (position) {
            case 0:
                btnFloat1.show();
                btnFloat2.hide();
                cardView.setVisibility(View.GONE);
                break;
            case 1:
                btnFloat2.show();
                btnFloat1.hide();
                cardView.setVisibility(View.GONE);
                break;

            default:
                btnFloat1.show();
                btnFloat2.hide();
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
}
