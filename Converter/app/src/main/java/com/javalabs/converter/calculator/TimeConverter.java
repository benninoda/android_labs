package com.javalabs.converter.calculator;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TimeConverter implements IConverter{
    Map<String, Double> dictionary = new HashMap<String, Double>();

    public TimeConverter(@NotNull String[] items){
        dictionary.put(items[0], 1.);
        dictionary.put(items[1], 24.);
        dictionary.put(items[2], 1440.);
        dictionary.put(items[3], 86400.);
        dictionary.put(items[4], 86400000.);
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
        Double result = data * getMultiplier(this.getValue(to), this.getValue(from));
        return result;
    }

}
