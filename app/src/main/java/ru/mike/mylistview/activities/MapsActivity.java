package ru.mike.mylistview.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;
import java.util.List;

import ru.mike.mylistview.R;
import ru.mike.mylistview.dao.AppDatabase;
import ru.mike.mylistview.dao.TravelHistoryDao;
import ru.mike.mylistview.models.UserTravelHistory;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TravelHistoryDao travelHistoryDao;
    private AppDatabase db;
    private List<UserTravelHistory> markers;
    private ClusterManager<UserTravelHistory> clusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mymap);
        mapFragment.getMapAsync(this);

        db = MyApp.getInstance().getDatabase();
        travelHistoryDao = db.getTravelHistoryDao();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40, 40), 4));

        prepareMarkers();
        clusterManager = new ClusterManager<UserTravelHistory>(this.getApplicationContext(), mMap);
        mMap.setOnCameraIdleListener(clusterManager);

        clusterManager.addItems(markers);
        //clusterManager.cluster();
        clusterManager.setRenderer(new OwnIconRendered(getApplicationContext(), mMap, clusterManager));

    }

    private void prepareMarkers() {
        List<UserTravelHistory> userTravelHistoryList = travelHistoryDao.getAllUserTravelHistory();
        markers = new ArrayList<UserTravelHistory>();
        for (UserTravelHistory userTravelHistory : userTravelHistoryList){
            Log.d("MyApp", userTravelHistory.getUserData().getFio() + String.valueOf(userTravelHistory.getTravelHistory().getLng()));
            markers.add(userTravelHistory);
        }
    }

    public class OwnIconRendered extends DefaultClusterRenderer<UserTravelHistory> {

        public OwnIconRendered(Context context, GoogleMap map,
                               ClusterManager<UserTravelHistory> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(UserTravelHistory item,
                                                   MarkerOptions markerOptions) {
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(item.getUserData().getImageUrl())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Bitmap bitmap = Bitmap.createScaledBitmap(resource, 50, 50 , true);
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                        }
                    });

        }

    }
}