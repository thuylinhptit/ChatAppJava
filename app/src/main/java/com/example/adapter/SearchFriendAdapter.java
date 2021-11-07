package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.controller.HomeController;
import com.example.controller.RMIController;
import com.example.controller.SocketCurrent;
import com.example.interfaces.IClickItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import model.ConnectionType;
import model.FriendRequest;
import model.ObjectWrapper;
import model.User;

public class SearchFriendAdapter extends RecyclerView.Adapter<SearchFriendAdapter.SearchFriendAdapterViewHolder> {
    List<User> list;
    Context context;
    IClickItem onClick;

    public SearchFriendAdapter(List<User> list, Context context, IClickItem onClick) {
        this.list = list;
        this.context = context;
        this.onClick = onClick;
    }


    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchFriendAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(com.example.chatapp2.R.layout.tai_friend_search_row,parent,false);
        return new SearchFriendAdapterViewHolder(itemView, onClick);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFriendAdapterViewHolder holder, int position) {
        if (list.get(position).getId() == SocketCurrent.instance.getClient().getId()) {
            holder.itemView.setVisibility(View.INVISIBLE);
            return;
        }
        holder.sendFriendRqBtn.setVisibility(View.VISIBLE);
        holder.acceptFriendBtn.setVisibility(View.GONE);
        holder.declineFriendBtn.setVisibility(View.GONE);
        holder.fullnameTxt.setText(list.get(position).getFullName());
        // check friend
        for (User u : SocketCurrent.instance.getClient().getFriendList()) {
            if (u.getId() == list.get(position).getId()) {
                holder.sendFriendRqBtn.setText("Friend");
                holder.sendFriendRqBtn.setClickable(false);
                holder.sendFriendRqBtn.setVisibility(View.VISIBLE);
                return;
            }
        }
        if (checkFriendSentRequest(list.get(position))) {
            holder.sendFriendRqBtn.setVisibility(View.GONE);
            holder.acceptFriendBtn.setVisibility(View.VISIBLE);
            holder.declineFriendBtn.setVisibility(View.VISIBLE);
            return;
        }
        if (checkMeSentARequest(list.get(position))) {
            holder.sendFriendRqBtn.setVisibility(View.VISIBLE);
            holder.sendFriendRqBtn.setText("Sent");
            holder.sendFriendRqBtn.setClickable(false);
            holder.acceptFriendBtn.setVisibility(View.GONE);
            holder.declineFriendBtn.setVisibility(View.GONE);
            return;
        }

    }

    private boolean checkFriendSentRequest(User user) {
        for (FriendRequest u : SocketCurrent.instance.getClient().getFriendRequestList()) {
            if (u.getSender().getId() == user.getId() && u.getReceiver().getId() == SocketCurrent.instance.getClient().getId()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkMeSentARequest(User user) {
        for (FriendRequest u : SocketCurrent.instance.getClient().getFriendRequestList()) {
            if (u.getReceiver().getId() == user.getId() && u.getSender().getId() == SocketCurrent.instance.getClient().getId()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SearchFriendAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView fullnameTxt;
        Button sendFriendRqBtn;
        Button acceptFriendBtn;
        Button declineFriendBtn;
        IClickItem onClick;
        public SearchFriendAdapterViewHolder(@NonNull View itemView, IClickItem onClick) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(com.example.chatapp2.R.id.avatar_search_row_id);
            fullnameTxt = (TextView) itemView.findViewById(com.example.chatapp2.R.id.name_search_row_id);
            sendFriendRqBtn = (Button) itemView.findViewById(com.example.chatapp2.R.id.send_request_friend_search_row_id);
            acceptFriendBtn = (Button) itemView.findViewById(com.example.chatapp2.R.id.accept_request_search_row_id);
            declineFriendBtn = (Button) itemView.findViewById(com.example.chatapp2.R.id.decline_request_search_row_id);

            this.onClick = onClick;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickListener(getAdapterPosition());
                }
            });

            sendFriendRqBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FriendRequest fr = new FriendRequest();
                    fr.setSender(SocketCurrent.instance.getClient());
                    fr.setReceiver(list.get(getAdapterPosition()));
//                    HomeController.getInstance().sendData(new ObjectWrapper(fr, ConnectionType.FRIENDREQUEST));
                    sendFriendRqBtn.setVisibility(View.VISIBLE);
                    sendFriendRqBtn.setText("Sent");
                    sendFriendRqBtn.setClickable(false);

                    //RMI
                    RMIController.Instance.getiService().sendRequest(fr);
                }
            });
            acceptFriendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FriendRequest fr = new FriendRequest();
                    fr.setReceiver(SocketCurrent.instance.getClient());
                    fr.setSender(list.get(getAdapterPosition()));
//                    HomeController.getInstance().sendData(new ObjectWrapper(fr, ConnectionType.ADDFRIEND));
                    acceptFriendBtn.setVisibility(View.GONE);
                    declineFriendBtn.setVisibility(View.GONE);
                    sendFriendRqBtn.setText("Friend");
                    sendFriendRqBtn.setEnabled(false);

                    //RMI
                    RMIController.Instance.getiService().acceptRequest(fr);
                }
            });
            declineFriendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FriendRequest fr = new FriendRequest();
                    fr.setReceiver(SocketCurrent.instance.getClient());
                    fr.setSender(list.get(getAdapterPosition()));
//                    HomeController.getInstance().sendData(new ObjectWrapper(fr, ConnectionType.DECLINEFRIEND));
                    sendFriendRqBtn.setText("Add Friend");
                    sendFriendRqBtn.setEnabled(true);
                    acceptFriendBtn.setVisibility(View.GONE);
                    declineFriendBtn.setVisibility(View.GONE);
                    //RMI
                    RMIController.Instance.getiService().deleteRequest(fr);
                }
            });
        }
    }
}
