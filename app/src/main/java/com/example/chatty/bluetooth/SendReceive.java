package com.example.chatty.bluetooth;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SendReceive extends Thread {
    // InputStream değişkeni
    private final InputStream inputStream;

    // OutputStream değişkeni
    private final OutputStream outputStream;
    private static SendReceive single_instance = null;


    // SendReceive constructor
    private SendReceive(BluetoothSocket socket) {

        // socket'i atadık.
        // BluetoothSocket değişkeni

        // tempInput null değeri
        InputStream tempInput = null;

        // tempOutput null değeri
        OutputStream tempOutput = null;

        try {
            // tempInput getInputStream metodu
            tempInput = socket.getInputStream();

            // tempOutput getOutputStream metodu
            tempOutput = socket.getOutputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // inputStream değerine tempInput değerini ata
        inputStream = tempInput;

        // outputStream değerine tempOutput değerini ata
        outputStream = tempOutput;
    }
    public static synchronized SendReceive getInstance(BluetoothSocket socket)
    {
        if (single_instance == null)
            single_instance = new SendReceive(socket);

        return single_instance;
    }
    // run fonksiyonunda yapılacak işler
    public void run() {
        // buffer nesnesi oluşturma
        byte[] buffer = new byte[1024];

        // bytes değişkeni
        int bytes;

        // true değeri döndükçe
        while (true) {

            try {
                // bytes değişkenine read buffer yap.
                bytes = inputStream.read(buffer);

                // parametreleri tutan bir mesaj oluşturmak için kullanılır.
                // handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1, buffer).sendToTarget();

                // hatayı yakalama işlemi
            } catch (IOException e) {
                // hatayı yazdır
                e.printStackTrace();
            }
        }
    }

    // write işleminin yapıldığı function
    public void write(byte[] bytes) {
        try {
            // outputStream değerini yaz
            outputStream.write(bytes);
            // hata yakalama
        } catch (IOException e) {
            // hatayı yazdır
            e.printStackTrace();
        }
    }
}