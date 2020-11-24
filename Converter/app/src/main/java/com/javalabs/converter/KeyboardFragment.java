package com.javalabs.converter;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.javalabs.test.R;

import java.util.Objects;

public class KeyboardFragment extends Fragment {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnComma, btnDel;
    TextView dataTo, dataFrom;
    DataViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.e("D", "ONCREATEVIEW");
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(DataViewModel.class);
        return inflater.inflate(R.layout.keyboard_fragment, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
        assert view != null;
        super.onViewCreated(view, savedInstanceState);

        // TODO: Use the ViewModel
        dataFrom = (TextView)view.findViewById(R.id.dataFrom);
        dataTo = (TextView) view.findViewById(R.id.dataTo);

        btn1 = (Button)view.findViewById(R.id.button1);
        btn2 = (Button)view.findViewById(R.id.button2);
        btn3 = (Button)view.findViewById(R.id.button3);
        btn4 = (Button)view.findViewById(R.id.button4);
        btn5 = (Button)view.findViewById(R.id.button5);
        btn6 = (Button)view.findViewById(R.id.button6);
        btn7 = (Button)view.findViewById(R.id.button7);
        btn8 = (Button)view.findViewById(R.id.button8);
        btn9 = (Button)view.findViewById(R.id.button9);
        btn0 = (Button)view.findViewById(R.id.button0);
        btnComma = (Button)view.findViewById(R.id.button_comma);
        btnDel = (Button)view.findViewById(R.id.button_del);

        btn1.setOnClickListener(v -> changeTextView("1"));
        btn2.setOnClickListener(v -> changeTextView("2"));
        btn3.setOnClickListener(v -> changeTextView("3"));
        btn4.setOnClickListener(v -> changeTextView("4"));
        btn5.setOnClickListener(v -> changeTextView("5"));
        btn6.setOnClickListener(v -> changeTextView("6"));
        btn7.setOnClickListener(v -> changeTextView("7"));
        btn8.setOnClickListener(v -> changeTextView("8"));
        btn9.setOnClickListener(v -> changeTextView("9"));
        btn0.setOnClickListener(v -> changeTextView("0"));
        btnComma.setOnClickListener(v -> addComma());
        btnDel.setOnClickListener(v -> backspaceTextView());
        btnDel.setOnLongClickListener(v -> {
            if (v.getId() == R.id.button_del)
                clearTextView();
            return true;
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void changeTextView(String addString){
        String number = mViewModel.getSelectedDataFrom().getValue();
        if(number.equals("0")){
            number = addString;
        }
        else {
            number += addString;
        }
        mViewModel.selectDataFrom(number);
    }

    private void addComma(){
        String number = mViewModel.getSelectedDataFrom().getValue();
        if (number.contains(".")){
            Toast.makeText(getActivity(), "The number already contains a comma", Toast.LENGTH_SHORT).show();
        }
        else {
            number += ".";
        }
        mViewModel.selectDataFrom(number);
    }

    private void backspaceTextView(){
        String number = mViewModel.getSelectedDataFrom().getValue();
        if(!number.equals("0")){
            if (number.length() == 1) {
                number = "0";
            }
            else {
                number = number.substring(0, number.length() - 1);
            }
        }
        mViewModel.selectDataFrom(number);
    }

    private void clearTextView() {
        mViewModel.selectDataFrom("0");
    }
}