package com.example.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp2.R;
import com.example.controller.HomeController;
import com.example.controller.SocketCurrent;
import com.example.interfaces.IClickItem;

import java.util.ArrayList;
import java.util.List;

import model.ConnectionType;
import model.ObjectWrapper;
import model.User;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private List<User> friendList;
    private Context context;
    IClickItem eventClick;
    public FriendAdapter(List<User> friendList, Context context, IClickItem eventClick) {
        this.friendList = friendList;
        this.context = context;
        this.eventClick = eventClick;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Change layout room_roomitem to friend_frienditem layout
        View itemView = layoutInflater.inflate(R.layout.tai_row_friend,parent,false);
        return new FriendViewHolder(itemView, eventClick);
    }

    public int getUserOnPosition(int position) {
        return friendList.get(position).getId();
    }
    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.nameFriendTxt.setText(friendList.get(position).getFullName());
        boolean isOnline=friendList.get(position).getStatus() == 1 ? true : false;
        if (isOnline) {
            holder.iconOnlineImg.setBackgroundColor(Color.parseColor("#68ce37"));
        } else {
            holder.iconOnlineImg.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public void changeStatus(int userId, boolean online) {


        notifyItemChanged(0);
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatarFriendImg;
        private TextView nameFriendTxt;
        private Button btn;
        private ImageView iconOnlineImg;
        public FriendViewHolder(@NonNull View itemView, IClickItem event) {
            super(itemView);
            nameFriendTxt = (TextView)itemView.findViewById(R.id.friend_row_name);
            btn = (Button)itemView.findViewById(R.id.friend_row_btnchat_id);
            iconOnlineImg = (ImageView)itemView.findViewById(R.id.friend_row_online_id);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Object> list = new ArrayList<>();
                    list.add(SocketCurrent.instance.getClient().getId());
                    list.add(getUserOnPosition(getAdapterPosition()));
                    ObjectWrapper o = new ObjectWrapper(list, ConnectionType.GETROOMFRIEND);
                    HomeController.getInstance().sendData(o);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    event.onClickListener(getUserOnPosition( getAdapterPosition() ));
                }
            });
        }
    }
}
