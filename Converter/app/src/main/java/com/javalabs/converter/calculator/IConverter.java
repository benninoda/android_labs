package com.javalabs.converter.calculator;

public interface IConverter {
    Double getValue(String unit);

    default Double getMultiplier(Double to, Double from){
        return to / from;
    }

    default Double convert(String from, String to, Double data){
        Double multiplier = getMultiplier(this.getValue(to), this.getValue(from));
        return multiplier * data;
    }
}
