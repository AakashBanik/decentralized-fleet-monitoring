package com.example.fleetmonitoring;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private ActionBar actionBar;
    private int colors;
    SwitchCompat switchCompatUpdate, switchCompatUpdateNotification, switchCompatDarkMode, switchCompatLoading, switchCompatEngine;
    public static final String SHARED_PREF_NAME = "SETTINGS";
    public boolean switchUpdateToggle, switchNotificationToggle, switchDarkMode, switchLoading, switchEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switchCompatUpdate = (SwitchCompat) findViewById(R.id.updateSwitch);
        switchCompatUpdateNotification = (SwitchCompat) findViewById(R.id.updateNotificationSwitch);
        switchCompatDarkMode = (SwitchCompat) findViewById(R.id.darkMode);
        switchCompatLoading = (SwitchCompat) findViewById(R.id.loading);
        switchCompatEngine = (SwitchCompat) findViewById(R.id.engine);
        setTitle("Developer Settings");

        switchCompatUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveUpdateValues();
            }
        });
        switchCompatUpdateNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveUpdateValues();
            }
        });
        switchCompatDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveUpdateValues();
            }
        });
        switchCompatLoading.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveUpdateValues();
            }
        });
        switchCompatEngine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveUpdateValues();
            }
        });

        getValues();
        setValues();
        actionBar = getSupportActionBar();
        colors = getColor(R.color.black);
        actionBar.setBackgroundDrawable(new ColorDrawable(colors));
    }

    public void getValues(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        switchUpdateToggle = sharedPreferences.getBoolean("Update", false);
        switchNotificationToggle = sharedPreferences.getBoolean("Notification", false);
        switchDarkMode = sharedPreferences.getBoolean("DarkMode", false);
        switchLoading = sharedPreferences.getBoolean("Loading", false);
        switchEngine = sharedPreferences.getBoolean("Engine", false);
    }

    public void saveUpdateValues(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Update", switchCompatUpdate.isChecked());
        editor.putBoolean("Notification", switchCompatUpdateNotification.isChecked());
        editor.putBoolean("DarkMode", switchCompatDarkMode.isChecked());
        editor.putBoolean("Loading", switchCompatLoading.isChecked());
        editor.putBoolean("Engine", switchCompatEngine.isChecked());
        editor.commit();
        Toast.makeText(this, "Update Data Saved.", Toast.LENGTH_SHORT).show();
    }

    public void setValues(){
        switchCompatUpdate.setChecked(switchUpdateToggle);
        switchCompatUpdateNotification.setChecked(switchNotificationToggle);
        switchCompatDarkMode.setChecked(switchDarkMode);
        switchCompatLoading.setChecked(switchLoading);
        switchCompatEngine.setChecked(switchEngine);
    }
}
