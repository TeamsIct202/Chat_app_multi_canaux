package com.example.chatty.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

import com.example.chatty.activities.AcceptConn;

import java.io.IOException;

public class ServerClass extends Thread {

    // BluetoothServerSocket nesnesi
    private BluetoothServerSocket serverSocket;
    private BluetoothAdapter bluetoothAdapter;

    // ServerClass yapıcı fonksiyonu
    @SuppressLint("MissingPermission")
    public ServerClass() {

        try {
            // serverSocket değerine uuid değerini kayıtla
            bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
            serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord("remote", Uuid.MY_UUID);
            //Toast.makeText(context, "Succesfull connection", Toast.LENGTH_SHORT).show();

            // IOException yakalama
        } catch (IOException e) {
            // hatayı bastır
            e.printStackTrace();
        }
    }

    // run fonksiyonunun işlemleri
    public void run() {
        // BluetoothSocket nesnesine null verildi.
        BluetoothSocket socket = null;

        // socket değeri null ise
        while (true) {

            try {
                // Alınan mesajı istediğimiz değerleri verebiliriz.
                // Message message = Message.obtain();

                // bağlantı oluştuğunda
                //  message.what = STATE_CONNECTING;

                // mesajı yollama işi
                //handler.sendMessage(message);

                // socket işini kabul et
                socket = serverSocket.accept();

            } catch (IOException e) {
                e.printStackTrace();

                // mesaj örneğini alma işi
                //Message message = Message.obtain();

                // STATE_CONNECTION_FAILED olayı olunca
                // message.what = STATE_CONNECTION_FAILED;

                // mesajı yollama
                //  handler.sendMessage(message);
            }

            // socket değeri null değilse
            if (socket != null) {

                // Alınan mesajı istediğimiz değerleri verebiliriz.
                // Message message = Message.obtain();

                // bağlantı oluştuğunu anlama işi
                //message.what = STATE_CONNECTED;

                // mesajı yollama işini handler ile yap
                // handler.sendMessage(message);

                // sendReceive nesnesi tanımı
                /*
                sendReceive = new AcceptConn.SendReceive(socket);
                */
                //sendReceive.start();

                break;
            }
        }
    }
}