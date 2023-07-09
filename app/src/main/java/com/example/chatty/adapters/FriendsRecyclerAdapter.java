package com.example.chatty.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatty.R;
import com.example.chatty.activities.ChatActivity;
import com.example.chatty.models.Friends;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FriendsRecyclerAdapter extends RecyclerView.Adapter<FriendsRecyclerAdapter.ViewHolder> {
    List<Friends> items=new ArrayList<Friends>();
    LayoutInflater minflater;
    public void setItems(List<Friends> l)
    {
        items=l;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FriendsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsRecyclerAdapter.ViewHolder holder, int position) {
        Friends friend=items.get(position);
        holder.bind(friend);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder
    {
        ShapeableImageView ivFriend=(ShapeableImageView) itemView.findViewById(R.id.ivFriend);
        TextView tvName=(TextView) itemView.findViewById(R.id.tvName);
        TextView tvLastMsg=(TextView) itemView.findViewById(R.id.tvLastMsg);
        TextView tvHour=(TextView) itemView.findViewById(R.id.tvHour);


        ViewHolder(View itemview)
        {
            super(itemview);



        }
        void bind(Friends friend)
        {
            tvName.setText(friend.getName());
            tvLastMsg.setText(friend.getLastmsg());
            SimpleDateFormat sdf= new SimpleDateFormat("HH:MM", Locale.getDefault());
            tvHour.setText(sdf.format(new Date(friend.getTimestamp())));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i =new Intent(itemView.getContext(), ChatActivity.class);
                    i.putExtra("friend",friend.getName());
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}
