package com.example.ilham.loginlogout.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.ViewPagerAdapter;

public class EntryActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Toolbar toolbar;
    private FloatingActionButton btnFloat1, btnFloat2;


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

        btnFloat1 = (FloatingActionButton) findViewById(R.id.entry_fab1);
        btnFloat2 = (FloatingActionButton) findViewById(R.id.entry_fab2);
        btnFloat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Buka Snackbar 1", Snackbar.LENGTH_SHORT).show();
            }
        });

        btnFloat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Buka Snackbar 2", Snackbar.LENGTH_SHORT).show();
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

    private void animateFab(int position) {
        switch (position) {
            case 0:
                btnFloat1.show();
                btnFloat2.hide();
                break;
            case 1:
                btnFloat2.show();
                btnFloat1.hide();
                break;

            default:
                btnFloat1.show();
                btnFloat2.hide();
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
