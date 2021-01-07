package com.javalabs.tabatatimer.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.javalabs.tabatatimer.R;
import com.javalabs.tabatatimer.helper.LocaleHelper;


public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TabataTimer);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("D", "ONCREATE MAINACTIVITY");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        editor = sharedPreferences.edit();
        String coef = sharedPreferences.getString("fontSize", "2");
        float size_coef = 0;
        switch (coef) {
            case "1": {
                size_coef = 0.85f;
                break;
            }
            case "2": {
                size_coef = 1.0f;
                break;
            }
            case "3": {
                size_coef = 1.15f;
                break;
            }
        }

        Configuration configuration = getResources().getConfiguration();
        configuration.fontScale = size_coef;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);

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