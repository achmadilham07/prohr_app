package com.example.ilham.loginlogout.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ilham.loginlogout.R;
import com.example.ilham.loginlogout.ViewPagerAdapter;

public class ContactActivity extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private FloatingActionButton btnFloat1, btnFloat2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_contact, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Contact");

        tabLayout = (TabLayout) view.findViewById(R.id.contact_tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.contact_viewpager);
        adapter = new ViewPagerAdapter(getChildFragmentManager());

        btnFloat1 = (FloatingActionButton) view.findViewById(R.id.contact_fab1);
        btnFloat2 = (FloatingActionButton) view.findViewById(R.id.contact_fab2);
        btnFloat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Buka Employee", Snackbar.LENGTH_SHORT).show();
            }
        });

        btnFloat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Buka Company", Snackbar.LENGTH_SHORT).show();
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
        adapter.AddFragment(new ContactEmpActivity(), "Employee");
        adapter.AddFragment(new ContactComActivity(), "Company");

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


}
