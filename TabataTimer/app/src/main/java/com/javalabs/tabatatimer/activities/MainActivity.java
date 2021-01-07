package com.javalabs.tabatatimer.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.javalabs.tabatatimer.R;
import com.javalabs.tabatatimer.helper.LocaleHelper;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TabataTimer);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("D", "ONCREATE MAINACTIVITY");



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