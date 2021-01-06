package com.javalabs.tabatatimer.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.javalabs.tabatatimer.database.enities.Timer;

import java.util.List;

@Dao
public interface TimerDao {
    @Query("SELECT * FROM timer")
    LiveData<List<Timer>> getAll();

    @Query("SELECT * FROM timer WHERE uid IN (:timerIds)")
    LiveData<List<Timer>> loadAllByIds(int[] timerIds);

    @Query("SELECT * FROM timer WHERE timer_name LIKE :timerName")
    LiveData<List<Timer>> findByName(String timerName);

    @Query("SELECT * FROM timer WHERE uid = :timerId")
    Timer findById(int timerId);

    @Insert
    void insert(Timer timer);

    @Update
    void update(Timer timer);

    @Delete
    void deleteTimer(Timer timer);

    @Query("DELETE FROM timer")
    void deleteAll();
}
