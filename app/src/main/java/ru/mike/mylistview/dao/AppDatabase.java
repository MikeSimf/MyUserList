package ru.mike.mylistview.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.mike.mylistview.models.TravelHistory;
import ru.mike.mylistview.models.UserData;

@Database(entities = {UserData.class , TravelHistory.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDataDao getUserDataDao();
    public abstract TravelHistoryDao getTravelHistoryDao();
}
