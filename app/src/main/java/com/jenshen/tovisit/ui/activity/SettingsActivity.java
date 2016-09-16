package com.jenshen.tovisit.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.jenshen.tovisit.ui.fragment.SettingsFragment;
import com.jenshen.tovisit.R;


public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (savedInstanceState == null) {
            Fragment preferenceFragment = new SettingsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, preferenceFragment)
                    .commit();
        }
    }
}
