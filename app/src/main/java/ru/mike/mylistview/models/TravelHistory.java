package ru.mike.mylistview.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

@Entity(foreignKeys = @ForeignKey(entity = UserData.class,
                parentColumns = "id",
                childColumns = "userData_id",
                onDelete = ForeignKey.CASCADE))
public class TravelHistory {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @NonNull
    private Long userData_id;
    private double lat;
    private double lng;

    public TravelHistory(@NonNull Long userData_id, double lat, double lng) {
        this.userData_id = userData_id;
        this.lat = lat;
        this.lng = lng;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public Long getUserData_id() {
        return userData_id;
    }

    public void setUserData_id(@NonNull Long userData_id) {
        this.userData_id = userData_id;
    }

    public LatLng getPosition() {
        return new LatLng(lat, lng);
    }

    public void setPosition(LatLng position) {
        this.lat = position.latitude;
        this.lng = position.longitude;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
