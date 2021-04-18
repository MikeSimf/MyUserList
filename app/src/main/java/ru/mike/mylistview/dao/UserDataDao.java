package ru.mike.mylistview.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.mike.mylistview.models.UserData;
import ru.mike.mylistview.models.UserTravelHistory;

@Dao
public interface UserDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(UserData userData);

    @Delete
    void delete(UserData userData);

    @Query("SELECT * FROM userData")
    List<UserData> getAllUserData();

    @Query("SELECT * FROM userData WHERE id = :id")
    UserData getUserDataById(Long id);
}
