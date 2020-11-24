package com.javalabs.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.javalabs.converter.DataFragment;


public class MainActivity extends AppCompatActivity {
    DataFragment dataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("D", "ONCREATE");
    }

//    public void swapValues(View v){
//        dataFragment.swapValues();
//    }



}
