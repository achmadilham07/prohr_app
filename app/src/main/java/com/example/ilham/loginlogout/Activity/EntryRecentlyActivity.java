package com.example.ilham.loginlogout.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ilham.loginlogout.R;

public class EntryRecentlyActivity extends Fragment {
    View v;

    public EntryRecentlyActivity() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_entry_recently, container, false);
        return v;
    }
}
