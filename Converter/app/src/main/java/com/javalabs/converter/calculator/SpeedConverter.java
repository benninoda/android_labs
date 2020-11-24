package com.javalabs.converter.calculator;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SpeedConverter implements IConverter{
    Map<String, Double> dictionary = new HashMap<String, Double>();

    public SpeedConverter(@NotNull String[] items){
        dictionary.put(items[0], 1.);
        dictionary.put(items[1], 3.6);
        dictionary.put(items[2], 0.001);
        dictionary.put(items[3], 39.3700787);
        dictionary.put(items[4], 3.2808399);
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