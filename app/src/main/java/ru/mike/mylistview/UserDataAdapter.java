package ru.mike.mylistview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder>{
    private static final String IMAGE_URL = "image_url";
    private static final String USER_FIO = "user_fio";
    private static final String USER_BIRTH = "user_birth";
    private static final String USER_MAIL = "user_mail";
    private Context mContext;
    private List<UserData> userDataList;


    UserDataAdapter(Context context, List<UserData> userDataList) {
        this.mContext = context;
        this.userDataList = userDataList;
    }

    @Override
    public UserDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserDataAdapter.ViewHolder holder, int position) {
        UserData userData = userDataList.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(userData.getImageUrl())
                .into(holder.photoView);
        holder.fioView.setText(userData.getFio());
        holder.birthView.setText(userData.getBirth());
        holder.mailView.setText(userData.getMail());

        //отображаем подробную инфу при нажатии
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(IMAGE_URL, userDataList.get(position).getImageUrl());
                intent.putExtra(USER_FIO, userDataList.get(position).getFio());
                intent.putExtra(USER_BIRTH, userDataList.get(position).getBirth());
                intent.putExtra(USER_MAIL, userDataList.get(position).getMail());
                mContext.startActivity(intent);
            }
        });
        //удаляем при длительном нажатии
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MainActivity activity = (MainActivity) mContext;
                MyAlertRemoveDialog myAlertRemoveDialog = new MyAlertRemoveDialog();
                myAlertRemoveDialog.setOnPositiveClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userDataList.remove(position);
                        notifyDataSetChanged();
                        activity.saveDataInSharedPreferences();
                    }
                });
                FragmentManager manager = activity.getSupportFragmentManager();
                myAlertRemoveDialog.show(manager, "dialog");
                return true;
            };
        });
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView photoView;
        final TextView fioView, birthView, mailView;
        ViewHolder(View view){
            super(view);
            photoView = view.findViewById(R.id.photo);
            fioView = view.findViewById(R.id.fio);
            birthView = view.findViewById(R.id.birth);
            mailView = view.findViewById(R.id.mail);
        }
    }
}
