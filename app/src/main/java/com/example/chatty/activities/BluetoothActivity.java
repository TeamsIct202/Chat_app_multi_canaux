package com.example.chatty.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chatty.R;
import com.example.chatty.models.Bluetooth;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;
    Set<BluetoothDevice> set_pairedDevices;
    List<Bluetooth> adapter_paired_devices=new ArrayList<>();
    ListView lvdevices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        lvdevices=(ListView) findViewById(R.id.list);
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        initialize_bluetooth();
        lvdevices.setAdapter(new com.example.chatty.adapters.BluetoothAdapter(getApplicationContext(), (ArrayList<Bluetooth>) adapter_paired_devices));
        lvdevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getApplicationContext(),ConnectActivity.class);
                String dvName=adapter_paired_devices.get(position).getName();
                String dvAdresse=adapter_paired_devices.get(position).getAdresse();
                i.putExtra("name",dvName);
                i.putExtra("adresse",dvAdresse);
                Toast.makeText(BluetoothActivity.this, dvAdresse, Toast.LENGTH_SHORT).show();
                startActivity(i);

            }
        });
    }
    public void initialize_bluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Toast.makeText(getApplicationContext(), "Your Device doesn't support bluetooth. you can play as Single player", Toast.LENGTH_SHORT).show();
            finish();
        }

        //Add these permisions before
//        <uses-permission android:name="android.permission.BLUETOOTH" />
//        <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
//        <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
//        <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions

                return;
            }
           // startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            startActivity(enableBtIntent);
        } else {
            set_pairedDevices = bluetoothAdapter.getBondedDevices();

            if (set_pairedDevices.size() > 0) {

                for (BluetoothDevice device : set_pairedDevices) {
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address
                    adapter_paired_devices.add(new Bluetooth(deviceName,deviceHardwareAddress));

                    //adapter_paired_devices.add(device.getName() + "\n" + device.getAddress());
                }
            }
        }
    }
}