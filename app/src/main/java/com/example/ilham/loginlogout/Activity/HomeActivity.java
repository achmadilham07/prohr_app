package com.example.ilham.loginlogout.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ilham.loginlogout.List;
import com.example.ilham.loginlogout.R;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;

/**
 * Created by Belal on 18/09/16.
 */


public class HomeActivity extends Fragment {

    private RecyclerView recycleView;
    private RecyclerView.LayoutManager layoutManager;
    private SlimAdapter adapter;

    private ArrayList arrayList = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_home, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Home");
        setHasOptionsMenu(true);

        recycleView = (RecyclerView) view.findViewById(R.id.recycleview);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recycleView.setLayoutManager(layoutManager);
        } else {
            recycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }

        adapter = SlimAdapter.create()
                .register(R.layout.item_list_home, new SlimInjector<List>() {
                    @Override
                    public void onInject(final List data, IViewInjector injector) {
                        injector.text(R.id.txtTitle, data.getTitle())
                                .text(R.id.txtCaption, data.getCaption())
                                .image(R.id.imageCard, data.getImage())
                                .with(R.id.item, new IViewInjector.Action() {
                                    @Override
                                    public void action(View view) {
                                        view.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View view) {
                                                //Toast.makeText(getApplicationContext(), "" + data.getTitle(), Toast.LENGTH_LONG).show();
                                                switch (data.getId()) {
                                                    case "1":
                                                        startActivity(new Intent(getContext(), PresenceActivity.class));
                                                        break;
                                                    case "2":
                                                        startActivity(new Intent(getContext(), SalaryActivity.class));
                                                        break;
                                                    case "3":
                                                        startActivity(new Intent(getContext(), EntryActivity.class));
                                                        break;
                                                    case "4":
                                                        startActivity(new Intent(getContext(), ScheduleActivity.class));
                                                        break;
                                                    default:
                                                        Toast.makeText(getContext(), "" + data.getTitle(), Toast.LENGTH_LONG).show();
                                                        break;
                                                }
                                            }
                                        });
                                    }
                                });

                    }
                })
                .attachTo(recycleView);

        // tambahkan obyek ke catatanArrayList
        arrayList.add(new List("1", "Presence", "Kehadiran", R.drawable.ic_email));
        arrayList.add(new List("2", "Salary", "Gaji", R.drawable.ic_email));
        arrayList.add(new List("3", "Entry", "Pengajuan", R.drawable.ic_email));
        arrayList.add(new List("4", "Schedule", "Jadwal", R.drawable.ic_email));
        arrayList.add(new List("5", "History", "Riwayat", R.drawable.ic_email));

        // setting data ke adapter
        adapter.updateData(arrayList);
        // ketika ada perubahan data adapter akan diset ulang
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home_setting) {
            Snackbar.make(getView(),"Setting", Snackbar.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}