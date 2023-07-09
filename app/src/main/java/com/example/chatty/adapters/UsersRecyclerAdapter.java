package com.example.chatty.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatty.R;
import com.example.chatty.activities.BluetoothActivity;
import com.example.chatty.activities.ChatActivity;
import com.example.chatty.models.Friends;
import com.example.chatty.models.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder> implements Filterable {
    List<Users> items=new ArrayList<>();
    List<Users> usersFilteredList=new ArrayList<>();

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSearch=constraint.toString();
                if(charSearch.isEmpty())
                {
                    usersFilteredList=items;
                }
                else {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        Stream<Users> resultList=items.stream().filter(i-> i.getFullName().toLowerCase().contains(charSearch.toLowerCase()));
                        usersFilteredList=resultList.collect(Collectors.toList()); ;
                    }

                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=usersFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
            usersFilteredList=(List<Users>) results.values;
            notifyDataSetChanged();

                        }
        };

    }
    public void setItems(List<Users> l)
    {
        items=l;
        usersFilteredList=l;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user=usersFilteredList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return usersFilteredList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvShortName=(TextView) itemView.findViewById(R.id.tvShortName);
        TextView tvName=(TextView) itemView.findViewById(R.id.tvName);

        ViewHolder(View itemview)
        {
            super(itemview);
        }
        void bind(Users users)
        {
            tvName.setText(users.getFullName());
            tvShortName.setText(String.valueOf(users.getFullName().charAt(0)));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(itemView.getContext(), ChatActivity.class);
                    i.putExtra("friend", users.getUuid());
                    itemView.getContext().startActivity(i);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent i=new Intent(itemView.getContext(), BluetoothActivity.class);
                   // i.putExtra("friend", users.getUuid());
                    itemView.getContext().startActivity(i);
                    return false;
                }
            });

        }
    }

}
