package ru.mike.mylistview.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.Random;

import ru.mike.mylistview.dao.AppDatabase;
import ru.mike.mylistview.R;
import ru.mike.mylistview.dao.TravelHistoryDao;
import ru.mike.mylistview.models.TravelHistory;
import ru.mike.mylistview.models.UserTravelHistory;
import ru.mike.mylistview.modules.recyclerView.UserDataAdapter;
import ru.mike.mylistview.dao.UserDataDao;
import ru.mike.mylistview.dialogs.MyAlertDialog;
import ru.mike.mylistview.dialogs.MyAlertRemoveDialog;
import ru.mike.mylistview.models.UserData;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyApp";
    private static final String USER_DATA_OBJ = "userDataObj";
    private static final String USER_TRAVEL_HISTORY_OBJ = "userTravelHistoryObj";
    private static final String REMOVE_DIALOG_TAG = "remove_dialog";
    private static final String ALERT_DIALOG_TAG = "alert_dialog";

    List<UserData> userDataList;
    UserDataAdapter adapter;
    FragmentManager manager;
    AppDatabase db;
    UserDataDao userDataDao;
    TravelHistoryDao travelHistoryDao;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        db = MyApp.getInstance().getDatabase();
        userDataDao = db.getUserDataDao();
        travelHistoryDao = db.getTravelHistoryDao();

        userDataList = userDataDao.getAllUserData();

        //Если данные не загружены, то наполняем и сообщаем что мы их наполнили:)
        if(userDataList.size()==0){
            generateInitialDate();
            userDataList = userDataDao.getAllUserData();
            showInfoMsg();
        }

        initRecyclerView();

        //checkTravelHistories();
    }

    private void checkTravelHistories() {
        List<UserTravelHistory> userTravelHistoryList = travelHistoryDao.getAllUserTravelHistory();
        for (UserTravelHistory userTravelHistory : userTravelHistoryList){
            Log.d(TAG, userTravelHistory.getUserData().getFio() + String.valueOf(userTravelHistory.getTravelHistory().getLng()));
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new UserDataAdapter(this, userDataList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //сообщение о том, что мы наполнили данные
    private void showInfoMsg(){
        MyAlertDialog myAlertDialog = new MyAlertDialog();
        myAlertDialog.show(manager, ALERT_DIALOG_TAG);
    }

    public void showDetailInfo(int position){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(USER_DATA_OBJ, userDataList.get(position));
        startActivity(intent);
    }

    public void showTravelHistory(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void longClick(int position){
        //deleteUserData(position);
        showTravelHistory();
    }

    private void deleteUserData(int position){
        MyAlertRemoveDialog myAlertRemoveDialog = new MyAlertRemoveDialog();
        myAlertRemoveDialog.setOnPositiveClickListener((dialog, which) -> {
            userDataDao.delete(userDataList.get(position));
            userDataList.remove(position);
            adapter.notifyDataSetChanged();
        });
        myAlertRemoveDialog.show(manager, REMOVE_DIALOG_TAG);
    }

    //наполняем данные
    private void generateInitialDate(){
        generateUserInfo("Пустяков Виталий Петрович", "pust_vp@mail.ru", "01.01.1991", "https://banner2.cleanpng.com/20180806/kxa/kisspng-android-nougat-easter-egg-cat-marshmallow-easter-e-5b685549e9b643.0945065315335642339573.jpg");
        generateUserInfo("Котов Василий Игоревич", "kotov92@mail.ru", "01.01.1992", "https://c.pxhere.com/photos/54/aa/cat_animal_nature_close_up-1106847.jpg!d");
        generateUserInfo("Пушистя Светлана Карловна", "svetik1993@mail.ru", "01.01.1993", "https://trikky.ru/wp-content/blogs.dir/1/files/2019/11/01/21-22-08-s1200.jpg");
        generateUserInfo("Петров Иван Александрович", "petya@mail.ru", "01.01.1995", "https://glorypets.ru/wp-content/uploads/2020/09/Ris.6-SHustryj-korgi.jpg");
        generateUserInfo("Мегатрон Мамкин Школьник", "megatron@mail.ru", "01.01.2012", "https://pbs.twimg.com/media/EaCAn1uUMAAYLC5.jpg");
    }

    private void generateUserInfo(String fio, String mail, String birth, String imageUrl){
        UserData userData = new UserData(fio, mail, birth, imageUrl);

        generateHistoryTravel(userDataDao.insert(userData), 20);
    }

    //генерация случайных координат в пределах min и max
    private void generateHistoryTravel(Long userData_id, int pointCount){
        int min = 30;
        int max = 50;
        int diff = max - min;
        Random random = new Random();
        for(int i = 0; i<pointCount; i++){
            double lat = min + random.nextInt(diff + 1);
            double lng = min + random.nextInt(diff + 1);
            travelHistoryDao.insert(new TravelHistory(userData_id, lat, lng));
        }
    }


}