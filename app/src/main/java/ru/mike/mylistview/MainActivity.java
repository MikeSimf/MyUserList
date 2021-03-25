package ru.mike.mylistview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyApp";
    private static final String PREF_SETTINGS = "setting";
    private static final String JSON_NAME = "userdata";
    private ArrayList<UserData> userDataList;
    private UserDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDataList = new ArrayList<UserData>();

        //Если данные не загружены, то сообщаем что мы их наполним:)
        if(!loadDataFromSharedPreferences()){
            setInitialData();
            saveDataInSharedPreferences();
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            FragmentManager manager = getSupportFragmentManager();
            myAlertDialog.show(manager, "dialog");
        }
        //формируем список по загруженным (заполненным) данным
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new UserDataAdapter(this, userDataList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    //загружаем данные из SharedPreferences
    private boolean loadDataFromSharedPreferences(){
        SharedPreferences preferences = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE);
        String jsonUserData = preferences.getString(JSON_NAME, null);

        if(jsonUserData == null) return false;

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        userDataList.addAll(gson.fromJson(jsonUserData, new TypeToken<ArrayList<UserData>>(){}.getType()));
        if(userDataList.size()==0) return false;
        return true;
    }

    //сохраняем данные в SharedPreferences
    public void saveDataInSharedPreferences(){
        SharedPreferences preferences = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(JSON_NAME, gson.toJson(userDataList));
        editor.commit();
    }

    //наполняем данные
    private void setInitialData(){
        userDataList.add(new UserData ("Пустяков Виталий Петрович", "pust_vp@mail.ru", "01.01.1991", "https://banner2.cleanpng.com/20180806/kxa/kisspng-android-nougat-easter-egg-cat-marshmallow-easter-e-5b685549e9b643.0945065315335642339573.jpg"));
        userDataList.add(new UserData ("Котов Василий Игоревич", "kotov92@mail.ru", "01.01.1992", "https://c.pxhere.com/photos/54/aa/cat_animal_nature_close_up-1106847.jpg!d"));
        userDataList.add(new UserData ("Пушистя Светлана Карловна", "svetik1993@mail.ru", "01.01.1993", "https://trikky.ru/wp-content/blogs.dir/1/files/2019/11/01/21-22-08-s1200.jpg"));
        userDataList.add(new UserData ("Петров Иван Александрович", "petya@mail.ru", "01.01.1995", "https://glorypets.ru/wp-content/uploads/2020/09/Ris.6-SHustryj-korgi.jpg"));
        userDataList.add(new UserData ("Мегатрон Мамкин Школьник", "megatron@mail.ru", "01.01.2012", "https://pbs.twimg.com/media/EaCAn1uUMAAYLC5.jpg"));
    }
}