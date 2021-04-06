package ru.mike.mylistview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder>{

    private Context mContext;
    private List<UserData> userDataList;


    UserDataAdapter(Context context, List<UserData> userDataList) {
        this.mContext = context;
        this.userDataList = userDataList;
    }

    @NonNull
    @Override
    public UserDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        holder.itemView.setOnClickListener(v -> ((MainActivity) mContext).showDetailInfo(position));

        //удаляем при длительном нажатии
        holder.itemView.setOnLongClickListener(v -> true);
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
