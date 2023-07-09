package com.example.chatty.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chatty.R;
import com.example.chatty.models.Bluetooth;

import java.util.ArrayList;

public class  BluetoothAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Bluetooth> arrayList;
    private TextView m_name,m_adrese,m_rssi;
    public BluetoothAdapter(Context context,ArrayList<Bluetooth> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }
    @Override
    public int getCount()
    {
        return arrayList.size();
    }
    @Override
    public  Object getItem(int position)
    {
        return arrayList.get(position);
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView= LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        m_name=convertView.findViewById(R.id.name);
        m_adrese=convertView.findViewById(R.id.adresse);
        m_rssi=convertView.findViewById(R.id.rssi);
        m_name.setText("Name:"+arrayList.get(position).getName());
        m_adrese.setText("Adresse:"+arrayList.get(position).getAdresse());
        m_rssi.setText("RSSI:"+arrayList.get(position).getRssi());
        return convertView;

    }
}
