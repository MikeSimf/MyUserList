package ru.mike.mylistview.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.mike.mylistview.models.TravelHistory;
import ru.mike.mylistview.models.UserData;
import ru.mike.mylistview.models.UserTravelHistory;

@Dao
public interface TravelHistoryDao {
    @Insert
    void insert(TravelHistory travelHistory);

    @Delete
    void delete(TravelHistory travelHistory);

    @Query("SELECT * FROM travelHistory")
    List<TravelHistory> getAllTravelHistory();

    @Query("SELECT * FROM travelHistory WHERE id = :id")
    TravelHistory getTravelHistoryById(Long id);

    @Query("SELECT * FROM travelHistory")
    List<UserTravelHistory> getAllUserTravelHistory();

    @Query("SELECT * FROM travelHistory WHERE id = :id")
    UserTravelHistory getUserTravelHistoryById(Long id);
}
