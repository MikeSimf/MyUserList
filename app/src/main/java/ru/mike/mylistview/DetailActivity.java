package ru.mike.mylistview;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    private static final String IMAGE_URL = "image_url";
    private static final String USER_FIO = "user_fio";
    private static final String USER_BIRTH = "user_birth";
    private static final String USER_MAIL = "user_mail";

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
        if(getIntent().hasExtra(IMAGE_URL) && getIntent().hasExtra(USER_FIO)){

            String imageUrl = getIntent().getStringExtra(IMAGE_URL);
            String userFio = getIntent().getStringExtra(USER_FIO);
            String userBirth = getIntent().getStringExtra(USER_BIRTH);
            String userMail = getIntent().getStringExtra(USER_MAIL);

            setImage(imageUrl, userFio, userBirth, userMail);
        }
    }

    private void setImage(String imageUrl, String userFio, String userBirth, String userMail) {
        fio.setText(userFio);
        birth.setText(userBirth);
        mail.setText(userMail);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }
}
