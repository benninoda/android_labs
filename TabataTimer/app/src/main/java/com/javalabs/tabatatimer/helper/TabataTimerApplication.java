package com.javalabs.tabatatimer.helper;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

public class TabataTimerApplication extends Application {

        SharedPreferences sharedPreferences;

        @Override
        public void onCreate() {
            super.onCreate();
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            loadTheme();
        }

        public static void updateTheme(boolean darkTheme) {
            if (darkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        private void loadTheme() {
            boolean theme = sharedPreferences.getBoolean("theme", false);
            if (!theme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }


}