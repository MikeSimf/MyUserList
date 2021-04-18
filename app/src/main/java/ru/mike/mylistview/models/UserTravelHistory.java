package ru.mike.mylistview.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;


public class UserTravelHistory implements ClusterItem {
    @Embedded
    public TravelHistory travelHistory;

    @Relation(parentColumn = "userData_id", entity = UserData.class, entityColumn = "id")
    public UserData userData;

    public UserData getUserData() {
        return userData;
    }

    public TravelHistory getTravelHistory() {
        return travelHistory;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return travelHistory.getPosition();
    }

    @Nullable
    @Override
    public String getTitle() {
        return userData.getFio();
    }

    @Nullable
    @Override
    public String getSnippet() {
        return null;
    }
}