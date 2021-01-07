package com.javalabs.tabatatimer.utils;

import android.os.Vibrator;

public class SimpleVibration implements IVibration{

    @Override
    public void vibro(Vibrator vibrator) {
        vibrator.vibrate(100);
    }
}
