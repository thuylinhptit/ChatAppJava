package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp2.R;
import com.example.controller.HomeController;
import com.example.interfaces.IClickItem;


import java.util.List;

import model.ConnectionType;
import model.FriendRequest;
import model.ObjectWrapper;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder> {
    private List<FriendRequest> friendRequestList;
    private Context context;
    private IClickItem onClick;

    public FriendRequestAdapter(List<FriendRequest> friendRequestList, Context context, IClickItem onClick) {
        this.friendRequestList = friendRequestList;
        this.context = context;
        this.onClick = onClick;
    }

    public List<FriendRequest> getFriendRequestList() {
        return friendRequestList;
    }

    public void setFriendRequestList(List<FriendRequest> friendRequestList) {
        this.friendRequestList = friendRequestList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Change layout room_roomitem to friendrequest_friendrequestitem layout
        View itemView = layoutInflater.inflate(R.layout.tai_friendrequest_row, parent, false);
        return new FriendRequestViewHolder(itemView, onClick);
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
        private IClickItem onClick;
        public FriendRequestViewHolder(@NonNull View itemView, IClickItem onClick) {
            super(itemView);
            this.onClick = onClick;
            init(itemView);
//            setActionListener();
        }

        private void init(View view) {
            senderNameTxt = (TextView) view.findViewById(R.id.friend_request_row_name);
            acceptBtn = (Button)view.findViewById(R.id.friend_request_row_btn_accept_id);
            declineBtn = (Button)view.findViewById(R.id.friend_request_row_btn_decline_id);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickListener(friendRequestList.get(getAdapterPosition()).getSender().getId());
                }
            });

            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Accept Friend");
                    ObjectWrapper objectWrapper = new ObjectWrapper(friendRequestList.get(getAdapterPosition()), ConnectionType.ADDFRIEND);
//                    friendRequestList.remove(getAdapterPosition());
                    HomeController.getInstance().sendData(objectWrapper);
                }
            });

            declineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Decline Friend");
                    ObjectWrapper objectWrapper = new ObjectWrapper(friendRequestList.get(getAdapterPosition()), ConnectionType.DECLINEFRIEND);
//                    friendRequestList.remove(getAdapterPosition());

                    HomeController.getInstance().sendData(objectWrapper);
                }
            });
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
