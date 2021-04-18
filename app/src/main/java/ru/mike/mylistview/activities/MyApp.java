package ru.mike.mylistview.activities;

import android.app.Application;

import androidx.room.Room;

import ru.mike.mylistview.dao.AppDatabase;
import ru.mike.mylistview.R;


public class MyApp extends Application {
    public static MyApp instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, getString(R.string.db_name))
                .allowMainThreadQueries()
                .build();
    }

    public static MyApp getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
