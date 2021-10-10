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
import com.example.controller.SocketCurrent;
import com.example.interfaces.IClickItem;

import java.util.List;

import model.Room;
import model.User;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewRoomHolder> {

    private List<Room> room;
    private Context context;
    private IClickItem onClickRoomListener;
    public RoomAdapter(List<Room> room, Context context, IClickItem onClickRoom) {
        this.room = room;
        this.context = context;
        this.onClickRoomListener = onClickRoom;
    }

    @NonNull
    @Override
    public ViewRoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_roomitem,parent,false);
        return new ViewRoomHolder(itemView, onClickRoomListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewRoomHolder holder, int position) {
        Room roomCurrent = room.get(position);
        String nameRoom = room.get(position).getName();
        String countPeople="";

        holder.roomNameTxt.setText(nameRoom);
        holder.countPeopleTxt.setText(countPeople);
    }


    @Override
    public int getItemCount() {
        return room.size();
    }

    public List<Room> getRoom() {
        return room;
    }

    public void setRoom(List<Room> room) {
        this.room = room;
        notifyDataSetChanged();
    }

    class ViewRoomHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView roomNameTxt;
        private TextView countPeopleTxt;
        private ImageView avatarRoom;
        private IClickItem onClickListener;
        public ViewRoomHolder(@NonNull View itemView, IClickItem onClickRoom) {
            super(itemView);
            roomNameTxt = (TextView) itemView.findViewById(R.id.roomnameid);
            countPeopleTxt = (TextView) itemView.findViewById(R.id.peoplecountroomid);
            avatarRoom = (ImageView) itemView.findViewById(R.id.avatar_roomid);
            this.onClickListener = onClickRoom;
            itemView.setOnClickListener(this);

        }




        public TextView getRoomNameTxt() {
            return roomNameTxt;
        }

        public void setRoomNameTxt(TextView roomNameTxt) {
            this.roomNameTxt = roomNameTxt;
        }

        public TextView getCountPeopleTxt() {
            return countPeopleTxt;
        }

        public void setCountPeopleTxt(TextView countPeopleTxt) {
            this.countPeopleTxt = countPeopleTxt;
        }

        public ImageView getAvatarRoom() {
            return avatarRoom;
        }

        public void setAvatarRoom(ImageView avatarRoom) {
            this.avatarRoom = avatarRoom;
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClickListener(getRoom().get(getAdapterPosition()).getId());
        }
    }
}
