package com.javalabs.tabatatimer.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.javalabs.tabatatimer.database.TimerRepository;
import com.javalabs.tabatatimer.database.enities.Timer;

import java.util.List;

public class TimerViewModel extends AndroidViewModel {
    private TimerRepository mRepository;

    private LiveData<List<Timer>> mAllWords;

    public TimerViewModel (Application application) {
        super(application);
        mRepository = new TimerRepository(application);
        mAllWords = mRepository.getAllTimers();
    }

    public LiveData<List<Timer>> getAllTimers() {
        return mAllWords;
    }

    public void insert(Timer timer) {
        mRepository.insert(timer);
    }

    public void deleteAll(){ mRepository.deleteAll();}

    public void deleteTimer(Timer timer){ mRepository.deleteTimer(timer);}

    public LiveData<Timer> findById(int timerId) {return mRepository.findTimerById(timerId);}

    public void update(Timer timer){ mRepository.update(timer);}
}
