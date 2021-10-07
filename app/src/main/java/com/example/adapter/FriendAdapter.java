package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp2.R;

import java.util.List;

import model.User;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private List<User> friendList;
    private Context context;

    public FriendAdapter(List<User> friendList, Context context) {
        this.friendList = friendList;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Change layout room_roomitem to friend_frienditem layout
        View itemView = layoutInflater.inflate(R.layout.row_roomitem,parent,false);
        return new FriendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.nameFriendTxt.setText(friendList.get(position).getFullName());
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatarFriendImg;
        private TextView nameFriendTxt;
        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
