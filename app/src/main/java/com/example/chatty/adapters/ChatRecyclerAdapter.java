package com.example.chatty.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatty.R;
import com.example.chatty.models.Message;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder> {

    List<Message> items=new ArrayList<>();
    public void setItems(List<Message> l)
    {
        items=l;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
        return new ViewHolder(itemView);
    }
    @Override
   public int getItemViewType(int pos)
    {
        super.getItemViewType(pos);
        int res=R.layout.item_chat_left;
        if(items.get(pos).getReceived())
        {
            res=R.layout.item_chat_left;
        }
        else {
            res=R.layout.item_chat_right;

        }
        return res;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message=items.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ViewHolder(View itemview)
        {
            super(itemview);



        }
        TextView tvMessage=(TextView) itemView.findViewById(R.id.tvMsg);
        TextView tvHour=(TextView) itemView.findViewById(R.id.tvHour);

        void bind(Message message)
        {
            tvMessage.setText(message.getText());
            SimpleDateFormat sdf= new SimpleDateFormat("HH:MM", Locale.getDefault());
            tvHour.setText(sdf.format(new Date(message.getTimestamp())));
           // SimpleDateFormat()
        }
    }
}
