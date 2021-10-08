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

import java.util.List;

import model.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private List<Message> messageList;
    private Context context;

    public MessageAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //Change layout room_roomitem to message_messageitem layout
        if (viewType == LEFT) {
            View itemView = layoutInflater.inflate(R.layout.tai_message_item_left, parent, false);
            return new MessageViewHolder(itemView);

        } else {
            View itemView = layoutInflater.inflate(R.layout.tai_message_item_right, parent, false);
            return new MessageViewHolder(itemView);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if (getItemViewType(position) == LEFT) {
//            holder.authorNameTxt.setText(messageList.get(position).getAuthor().getFullName());
            holder.messageTxt.setText(messageList.get(position).getContent());
        } else {
            holder.messageTxt.setText(messageList.get(position).getContent());
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message mess = messageList.get(position);
        if (mess.getAuthor().getId() == SocketCurrent.instance.getClient().getId()) {
            return RIGHT;
        }
        return LEFT;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTxt;
        private TextView authorNameTxt;
        private ImageView avatarImg;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            messageTxt = (TextView) itemView.findViewById(R.id.message_content_id);
            avatarImg = (ImageView) itemView.findViewById(R.id.message_avatar_id);
        }
    }
}
