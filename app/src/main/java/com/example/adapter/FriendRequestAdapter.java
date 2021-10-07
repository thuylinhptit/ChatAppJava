package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp2.R;


import java.util.List;

import model.FriendRequest;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder> {
    private List<FriendRequest> friendRequestList;
    private Context context;

    public FriendRequestAdapter(List<FriendRequest> friendRequestList, Context context) {
        this.friendRequestList = friendRequestList;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Change layout room_roomitem to friendrequest_friendrequestitem layout
        View itemView = layoutInflater.inflate(R.layout.row_roomitem, parent, false);
        return new FriendRequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestViewHolder holder, int position) {
        holder.senderNameTxt.setText(friendRequestList.get(position).getSender().getFullName());
    }

    @Override
    public int getItemCount() {
        return friendRequestList.size();
    }

    public class FriendRequestViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatarSenderImg;
        private TextView senderNameTxt;
        private Button acceptBtn, declineBtn;
        public FriendRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            init();
            setActionListener();
        }

        private void init() {

        }

        private void setActionListener() {
            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Request Server Accept Friend
                }
            });

            declineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Request Server Decline Friend
                }
            });
        }
    }
}
