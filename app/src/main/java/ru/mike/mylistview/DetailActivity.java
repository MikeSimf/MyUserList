package ru.mike.mylistview;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    private static final String USER_DATA_OBJ = "userDataObj";

    TextView fio, mail, birth;
    ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        fio = findViewById(R.id.userFio);
        mail = findViewById(R.id.userMail);
        birth = findViewById(R.id.userBirth);
        image = findViewById(R.id.userPhoto);

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra(USER_DATA_OBJ)){
            UserData userData = (UserData) getIntent().getSerializableExtra(USER_DATA_OBJ);
            showUserData(userData);
        }
    }

    private void showUserData(UserData userData) {
        fio.setText(userData.getFio());
        birth.setText(userData.getBirth());
        mail.setText(userData.getMail());
        Glide.with(this)
                .asBitmap()
                .load(userData.getImageUrl())
                .into(image);
    }
}
