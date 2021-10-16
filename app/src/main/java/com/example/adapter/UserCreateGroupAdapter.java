package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp2.R;

import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserCreateGroupAdapter extends RecyclerView.Adapter<UserCreateGroupAdapter.Holder> {
    List<User> listUser;
    Context context;
    List<User> choose;


    public UserCreateGroupAdapter(List<User> listUser, Context context) {
        this.listUser = listUser;
        this.context = context;
        choose = new ArrayList<>();
        notifyDataSetChanged();
    }

    public List<User> getListUser() {
        return listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Change layout room_roomitem to friend_frienditem layout
        View itemView = layoutInflater.inflate(R.layout.row_user_searchcreategroup,parent,false);
        return new UserCreateGroupAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (position < listUser.size()) {
            holder.fullName.setText(listUser.get(position).getFullName());
        }
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public List<User> listChoosedUser() {
        return choose;
    }

    public class Holder extends RecyclerView.ViewHolder {

        CheckBox checkbox;
        TextView fullName;
        ImageView avatar;

        public Holder(@NonNull View itemView) {
            super(itemView);
            checkbox = (CheckBox) itemView.findViewById(R.id.create_gr_checkbox);
            fullName = itemView.findViewById(R.id.fullname_gr_create_row_name);
//            avatar = itemView.findViewById(R.id.getImage);

            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkbox.isChecked()) {
                        choose.add(listUser.get(getAdapterPosition()));
                    } else {
                        choose.remove(getAdapterPosition());
                    }
                }
            });
        }
    }
}
