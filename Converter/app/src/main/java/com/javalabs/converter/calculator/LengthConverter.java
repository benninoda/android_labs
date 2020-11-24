package com.javalabs.converter.calculator;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LengthConverter implements IConverter{
    Map<String, Double> dictionary = new HashMap<String, Double>();

    public LengthConverter(@NotNull String[] items){
        dictionary.put(items[0], 0.001);
        dictionary.put(items[1], 1.);
        dictionary.put(items[2], 10.);
        dictionary.put(items[3], 100.);
        dictionary.put(items[4], 1000.);
        dictionary.put(items[5], 3.2808399);
        dictionary.put(items[6], 39.3700787);
    }

    @Override
    public Double getValue(String unit) {
        return dictionary.get(unit);
    }

    @Override
    public Double getMultiplier(Double to, Double from){
        return to / from;
    }

    @Override
    public Double convert(String from, String to, Double data) {
        Double result = data * getMultiplier(this.getValue(to), this.getValue(from));
        return result;
    }

}
