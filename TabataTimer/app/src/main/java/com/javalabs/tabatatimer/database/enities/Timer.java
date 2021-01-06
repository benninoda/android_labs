package com.javalabs.tabatatimer.database.enities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Timer {

    public Timer(){};

    public Timer(String timerName, String timerColor, int warmup, int workout,
                 int rest, int cooldown, int repeat, int cycle){
        this.timer_name = timerName;
        this.color = timerColor;
        this.warmup = warmup;
        this.workout = workout;
        this.rest = rest;
        this.cooldown = cooldown;
        this.repeat = repeat;
        this.cycle = cycle;
    }

    public void updateAll(String timerName, String timerColor, int warmup, int workout,
                          int rest, int cooldown, int repeat, int cycle){
        this.timer_name = timerName;
        this.color = timerColor;
        this.warmup = warmup;
        this.workout = workout;
        this.rest = rest;
        this.cooldown = cooldown;
        this.repeat = repeat;
        this.cycle = cycle;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "timer_name")
    public String timer_name;

    @ColumnInfo(name = "color")
    public String color;

    @ColumnInfo(name = "warmup")
    public int warmup;

    @ColumnInfo(name = "workout")
    public int workout;

    @ColumnInfo(name = "rest")
    public int rest;

    @ColumnInfo(name = "cooldown")
    public int cooldown;

    @ColumnInfo(name = "repeat")
    public int repeat;

    @ColumnInfo(name = "cycle")
    public int cycle;

}
