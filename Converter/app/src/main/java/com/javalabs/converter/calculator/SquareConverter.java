package com.javalabs.converter.calculator;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SquareConverter implements IConverter{
    Map<String, Double> dictionary = new HashMap<String, Double>();

    public SquareConverter(@NotNull String[] items){
        dictionary.put(items[0], 1.0);
        dictionary.put(items[1], 1000000.);
        dictionary.put(items[2], 100.);
        dictionary.put(items[3], 10000.);
    }

    @Override
    public Double getValue(String unit) {
        return dictionary.get(unit);
    }


}
