package com.javalabs.tabatatimer.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.javalabs.tabatatimer.database.dao.TimerDao;
import com.javalabs.tabatatimer.database.enities.Timer;

import java.util.List;

public class TimerRepository {
    private TimerDao mTimerDao;
    private LiveData<List<Timer>> mAllTimers;
    private MutableLiveData<List<Timer>> searchResults =
            new MutableLiveData<>();
    private static MutableLiveData<Timer> resultById =
            new MutableLiveData<>();

    public TimerRepository(Application application){
        TimerDatabase db = TimerDatabase.getDatabase(application);
        mTimerDao = db.getTimerDao();
        mAllTimers = mTimerDao.getAll();
    }

    public LiveData<List<Timer>> getAllTimers(){
        return mAllTimers;
    }

    public MutableLiveData<List<Timer>> getSearchResults(){
        return searchResults;
    }

    public void insert (Timer timer) {
        new InsertAsyncTask(mTimerDao).execute(timer);
    }

    public LiveData<Timer> findTimerById(int timerId) {
        TimerDatabase.databaseWriteExecutor.execute(()-> {
            resultById.postValue(mTimerDao.findById(timerId));
        });
        return resultById;
    }

    public void deleteTimer(Timer timer){
        TimerDatabase.databaseWriteExecutor.execute(()->{
           mTimerDao.deleteTimer(timer);
        });
    }

    public void update(Timer timer){
        TimerDatabase.databaseWriteExecutor.execute(()->{
            mTimerDao.update(timer);
        });
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(mTimerDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<Timer, Void, Void> {

        private TimerDao mAsyncTaskDao;

        InsertAsyncTask(TimerDao dao) {
            Log.e("D", "IN INSERT REPOSITORY");
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Timer... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    private static class DeleteAllAsyncTask extends AsyncTask<Timer, Void, Void> {

        private TimerDao mAsyncTaskDao;

        DeleteAllAsyncTask(TimerDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Timer... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


}

