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

import model.Room;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewRoomHolder> {

    private List<Room> room;
    private Context context;

    public RoomAdapter(List<Room> room, Context context) {
        this.room = room;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewRoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_roomitem,parent,false);
        return new ViewRoomHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewRoomHolder holder, int position) {
        holder.roomNameTxt.setText(room.get(position).getName());
        holder.countPeopleTxt.setText(room.get(position).getUserList().size() + " People");
    }

    @Override
    public int getItemCount() {
        return room.size();
    }

    class ViewRoomHolder extends RecyclerView.ViewHolder {
        private TextView roomNameTxt;
        private TextView countPeopleTxt;
        private ImageView avatarRoom;
        public ViewRoomHolder(@NonNull View itemView) {
            super(itemView);
            roomNameTxt = (TextView) itemView.findViewById(R.id.roomnameid);
            countPeopleTxt = (TextView) itemView.findViewById(R.id.peoplecountroomid);
            avatarRoom = (ImageView) itemView.findViewById(R.id.avatar_roomid);

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
    }
}
