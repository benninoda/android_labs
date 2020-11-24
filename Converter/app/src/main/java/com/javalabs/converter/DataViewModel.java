package com.javalabs.converter;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.javalabs.converter.calculator.IConverter;
import com.javalabs.converter.calculator.LengthConverter;
import com.javalabs.converter.calculator.MassConverter;
import com.javalabs.converter.calculator.SpeedConverter;
import com.javalabs.converter.calculator.SquareConverter;
import com.javalabs.converter.calculator.TimeConverter;

public class DataViewModel extends ViewModel {
    private IConverter unitConverter;
    private MutableLiveData<String> dataFrom = new MutableLiveData<String>("0");
    private MutableLiveData<String> dataTo = new MutableLiveData<String>("0");
    private MutableLiveData<String> measurementFrom = new MutableLiveData<String>("Километр");
    private MutableLiveData<String> measurementTo = new MutableLiveData<String>("Километр");
    private MutableLiveData<Integer> positionFrom = new MutableLiveData<Integer>(0);
    private MutableLiveData<Integer> positionTo = new MutableLiveData<Integer>(0);


    public void setUtilities(String[] categories){
        Log.e("D", "DATAVIEMODEL");
        unitConverter = new LengthConverter(categories);
    }

    public void selectDataFrom(String item) {
        dataFrom.setValue(item);
        convert();
    }

    public LiveData<String> getSelectedDataFrom() {
        return dataFrom;
    }

    public LiveData<String> getSelectedDataTo() {
        return dataTo;
    }

    public void selectMeasurementFrom(String item, int pos){
        measurementFrom.setValue(item);
        positionFrom.setValue(pos);
    }

    public void selectMeasurementTo(String item, int pos){
        measurementTo.setValue(item);
        positionTo.setValue(pos);
    }

    public LiveData<Integer> getPositionFrom(){
        return positionFrom;
    }

    public LiveData<Integer> getPositionTo(){
        return positionTo;
    }



    public void update(){
        convert();
    }

    public String convert(){
        Double result = unitConverter.convert(measurementFrom.getValue(),
                                measurementTo.getValue(),
                                Double.parseDouble(dataFrom.getValue()));
//        Log.e("D", dataFrom.getValue());
        dataTo.setValue(result.toString());
        return result.toString();
    }

    public void setUnitConverter(String converterName, String[] converterUnits) {
        switch (converterName) {
            case "Площадь":
                unitConverter = new SquareConverter(converterUnits);
                measurementFrom.setValue("км²");
                measurementTo.setValue("км²");
                break;
            case "Длина":
                unitConverter = new LengthConverter(converterUnits);
                measurementFrom.setValue("Километр");
                measurementTo.setValue("Метр");
                break;
            case "Масса":
                unitConverter = new MassConverter(converterUnits);
                measurementFrom.setValue("Килограмм");
                measurementTo.setValue("Килограмм");
                break;
            case "Скорость":
                unitConverter = new SpeedConverter(converterUnits);
                measurementFrom.setValue("м/c");
                measurementTo.setValue("м/c");
                break;
            case "Время":
                unitConverter = new TimeConverter(converterUnits);
                measurementFrom.setValue("День");
                measurementTo.setValue("День");
                break;
            default:
                unitConverter = new LengthConverter(converterUnits);
                break;
        }
    }

    public void swapValues(){
        dataFrom.setValue(dataTo.getValue());
//        this.selectDataFrom(this.getSelectedDataTo().getValue());
//        convert();
    }

}
