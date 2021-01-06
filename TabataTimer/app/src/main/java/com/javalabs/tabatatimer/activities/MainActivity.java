package com.javalabs.tabatatimer.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.javalabs.tabatatimer.R;
import com.javalabs.tabatatimer.helper.LocaleHelper;
import com.javalabs.tabatatimer.helper.TabataTimerApplication;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("fg", "strdyu");
        setTheme(R.style.Theme_TabataTimer);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("D", "ONCREATE MAINACTIVITY");


        boolean isDarkTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("theme", false);
        TabataTimerApplication.updateTheme(isDarkTheme);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocaleHelper.setLocale(MainActivity.this, LocaleHelper.getLanguage(MainActivity.this));
    }
}