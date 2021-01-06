package com.javalabs.tabatatimer.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.javalabs.tabatatimer.database.dao.TimerDao;
import com.javalabs.tabatatimer.database.enities.Timer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Timer.class}, version = TimerDatabase.VERSION)
public abstract class   TimerDatabase extends RoomDatabase {

    static final int VERSION = 1;

    public abstract TimerDao getTimerDao();
    private static TimerDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TimerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TimerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TimerDatabase.class, "timer_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
        }
    };

}

