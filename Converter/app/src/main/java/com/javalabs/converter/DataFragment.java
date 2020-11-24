package com.javalabs.converter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.widget.AdapterView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.javalabs.test.R;

import java.util.Objects;

public class DataFragment extends Fragment {

    TextView dataFrom, dataTo;
    Spinner spinner, spinnerTo, spinnerFrom;
    DataViewModel mViewModel;
    ImageButton btnSwapValue, btnCopyInput, btnCopyOutput;

    public static DataFragment newInstance() {
        return new DataFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container,savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(DataViewModel.class);
        return inflater.inflate(R.layout.data_fragment, container, false);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
        assert view != null;
        super.onViewCreated(view, savedInstanceState);

        dataFrom = (TextView)view.findViewById(R.id.dataFrom);
        dataTo = (TextView)view.findViewById(R.id.dataTo);

        mViewModel.setUtilities(getResources().getStringArray(R.array.length));
        mViewModel.getSelectedDataFrom().observe(getViewLifecycleOwner(), s -> {
            dataFrom.setText(s);
        });

        mViewModel.getSelectedDataTo().observe(getViewLifecycleOwner(), s -> {
            dataTo.setText(s);
        });

        spinner = (Spinner) view.findViewById(R.id.unit_category);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.convertCategories,
                R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.dropdown_item);
        spinner.setAdapter(adapter);

        spinnerFrom = (Spinner) view.findViewById(R.id.data_from_unit);
        spinnerTo = (Spinner) view.findViewById(R.id.data_to_unit);

        setSpinnerListener();
        setSpinnerToListener();
        setSpinnerFromListener();


            if (view.findViewById(R.id.btn_swap) != null) {
                btnSwapValue = (ImageButton) view.findViewById(R.id.btn_swap);
                btnCopyInput = (ImageButton) view.findViewById(R.id.btn_copy_input);
                btnCopyOutput = (ImageButton) view.findViewById(R.id.btn_copy_output);
            }
            btnSwapValue.setOnClickListener(v -> swapValues());
            btnCopyInput.setOnClickListener(v -> copyInput());
            btnCopyOutput.setOnClickListener(v -> copyOutput());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void swapValues(){
        mViewModel.swapValues();
        int temp_pos = mViewModel.getPositionTo().getValue();
        spinnerTo.setSelection(mViewModel.getPositionFrom().getValue());
        spinnerFrom.setSelection(temp_pos);
    }


    private void setSpinnerListener(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position){
                    case 0:
                        changeSpinner(R.array.length);
                        mViewModel.setUnitConverter(parent.getItemAtPosition(position).toString(),
                                                    getResources().getStringArray(R.array.length));
                        break;
                    case 1:
                        changeSpinner(R.array.square);
                        mViewModel.setUnitConverter(parent.getItemAtPosition(position).toString(),
                                getResources().getStringArray(R.array.square));
                        break;
                    case 2:
                        changeSpinner(R.array.time);
                        mViewModel.setUnitConverter(parent.getItemAtPosition(position).toString(),
                                getResources().getStringArray(R.array.time));
                        break;
                    case 3:
                        changeSpinner(R.array.speed);
                        mViewModel.setUnitConverter(parent.getItemAtPosition(position).toString(),
                                getResources().getStringArray(R.array.speed));
                        break;
                    case 4:
                        changeSpinner(R.array.mass);
                        mViewModel.setUnitConverter(parent.getItemAtPosition(position).toString(),
                                getResources().getStringArray(R.array.mass));
                        break;
                    default:
                        changeSpinner(R.array.length);
                        Toast.makeText(getActivity(), "Position = " + position, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    private void setSpinnerToListener(){
        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mViewModel.selectMeasurementTo(parent.getItemAtPosition(position).toString(), position);
                mViewModel.update();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void setSpinnerFromListener(){
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mViewModel.selectMeasurementFrom(parent.getItemAtPosition(position).toString(), position);
                mViewModel.update();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void changeSpinner(int category_unit){
        ArrayAdapter<?> adapter_date_from = ArrayAdapter.createFromResource(getContext(),
                                                                                category_unit,
                                                                                R.layout.spinner_item);
        adapter_date_from.setDropDownViewResource(R.layout.dropdown_item);
        spinnerFrom.setAdapter(adapter_date_from);
        spinnerTo.setAdapter(adapter_date_from);
    }

    public void copyInput() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(getString(R.string.input_value),
                                                    mViewModel.getSelectedDataFrom().getValue());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), R.string.toast_copy, Toast.LENGTH_SHORT).show();
    }

    public void copyOutput() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(getString(R.string.output_value), mViewModel.getSelectedDataTo().getValue());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), R.string.toast_copy, Toast.LENGTH_SHORT).show();
    }
}
