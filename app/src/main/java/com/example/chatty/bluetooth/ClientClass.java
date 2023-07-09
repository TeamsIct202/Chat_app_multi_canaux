package com.example.chatty.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.chatty.activities.ConnectActivity;

import java.io.IOException;

public class ClientClass extends Thread {

    // BluetoothDevice nesnesi
    private BluetoothDevice device;

    // BluetoothSocket nesnesi
    private BluetoothSocket socket;

    Context context;
   private boolean isConnected;

    // ClientClass yapıcı sınıfı
    public ClientClass(BluetoothDevice device1,Context c) {

        // nesneye device değerine device1'i ata
        device = device1;
        context=c;
        isConnected=false;
        try {
            // uuid değerine socket işine ata
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                socket = device.createRfcommSocketToServiceRecord(Uuid.MY_UUID);
                Toast.makeText(context, device.getName(), Toast.LENGTH_SHORT).show();

            }

            // hatayı yakala
        } catch (IOException e) {
            // hatayı yazdır
            e.printStackTrace();
        }
    }

    // run fonksiyonunda yapılacak işler
    public void run() {
        try {
            // socket'i connect et.
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                socket.connect();
                isConnected=true;


                // bir mesaj örneği alabilmek için bu metot kullanılır. Alınan mesaja istediğimiz değerler verilir.
                // Message message = Message.obtain();

                // kullanıcı tanımlı mesaj kodudur. Int tipinde tanımlanır.
                //  message.what = STATE_CONNECTED;

                // mesaj kuyruğunun sonuna bir mesaj eklemeyi sağlar.
                //  handler.sendMessage(message);

                // sendReceive nesnesi işlemi
                SendReceive sendReceive = SendReceive.getInstance(socket);

                // sendReceive başlat
                //sendReceive.start();
            }

        } catch (IOException e) {
            e.printStackTrace();

            // Alınan message istediğimiz değerler verilir.
            // Message message = Message.obtain();

            // eğer fail olduysa değer
            //  message.what = STATE_CONNECTION_FAILED;

            // handler sendmessage işlemi
            // handler.sendMessage(message);
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
