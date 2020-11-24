package com.javalabs.converter.calculator;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MassConverter implements IConverter{
    Map<String, Double> dictionary = new HashMap<String, Double>();

    public MassConverter(@NotNull String[] items){
        dictionary.put(items[0], 0.000001);
        dictionary.put(items[1], 0.001);
        dictionary.put(items[2], 1.);
        dictionary.put(items[3], 100.);
        dictionary.put(items[4], 1000.);
        dictionary.put(items[5], 2.20462262);
        dictionary.put(items[6], 35.2739619);
        dictionary.put(items[7], 5000.);
    }

    @Override
    public Double getValue(String unit) {
        return dictionary.get(unit);
    }

    @Override
    public Double getMultiplier(Double to, Double from){
        Double jj = to / from;
        return to / from;
    }

    @Override
    public Double convert(String from, String to, Double data) {
        return data * getMultiplier(this.getValue(to), this.getValue(from));
    }

}