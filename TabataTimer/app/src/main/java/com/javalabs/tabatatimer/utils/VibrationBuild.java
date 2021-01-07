package com.javalabs.tabatatimer.utils;

import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;

public class VibrationBuild implements IVibration {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void vibro(Vibrator vibrator) {
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}
