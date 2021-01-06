package com.javalabs.tabatatimer.models;

public class TimerSequenceModel {
    int id;
    String name;
    long stageTime;

    public TimerSequenceModel(){}

    public TimerSequenceModel(int id, String name, long stageTime){
        this.id = id;
        this.name = name;
        this.stageTime = stageTime;
    }

    public void setValues(int id, String name, long stageTime){
        this.id = id;
        this.name = name;
        this.stageTime = stageTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getStageTime() {
        return stageTime;
    }
}
