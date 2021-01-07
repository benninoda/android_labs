package com.javalabs.tabatatimer.models;

public class TimerSequenceModel {
    int id;
    int nameRes;
    long stageTime;

    public TimerSequenceModel(){}

    public TimerSequenceModel(int id, int nameRes, long stageTime){
        this.id = id;
        this.nameRes = nameRes;
        this.stageTime = stageTime;
    }

    public void setValues(int id, int nameRes, long stageTime){
        this.id = id;
        this.nameRes = nameRes;
        this.stageTime = stageTime;
    }

    public int getId() {
        return id;
    }

    public int getName() {
        return nameRes;
    }

    public long getStageTime() {
        return stageTime;
    }
}
