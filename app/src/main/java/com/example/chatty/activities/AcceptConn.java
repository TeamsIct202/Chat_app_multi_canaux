package com.example.chatty.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chatty.R;
import com.example.chatty.bluetooth.BluetoothChatService;
import com.example.chatty.bluetooth.SendReceive;
import com.example.chatty.bluetooth.ServerClass;
import com.example.chatty.bluetooth.Uuid;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AcceptConn extends AppCompatActivity {
    private static final String APP_NAME = "BluetoothChatApp";

    private TextView tvstatus;
    private Button movebtn;
    private BluetoothAdapter bluetoothAdapter;
    Handler handler;
   // SendReceive sendReceive;
    //ServerClass serverClass;
    BluetoothChatService bluetoothChatService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_conn);
        tvstatus=(TextView) findViewById(R.id.connectionstatus);
        movebtn=(Button) findViewById(R.id.move);
        handler=new Handler();
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        bluetoothChatService=BluetoothChatService.getInstance(handler,getApplicationContext());
       // serverClass=new ServerClass();
       // serverClass.start();
        bluetoothChatService.start();
        movebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  if(bluetoothChatService.isConnected()) {
                Boolean var=bluetoothChatService.isConnected();
                if(var)
                    tvstatus.setText("Connected");
                else
                    tvstatus.setText("not Connected");
                  //  Intent i = new Intent(getApplicationContext(), MainActivity.class);
                   // startActivity(i);
              //  }
            }
        });


    }
  /*  private class ServerClass extends Thread {

        // BluetoothServerSocket nesnesi
        private BluetoothServerSocket serverSocket;

        // ServerClass yapıcı fonksiyonu
        @SuppressLint("MissingPermission")
        public ServerClass() {

            try {
                // serverSocket değerine uuid değerini kayıtla
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, Uuid.MY_UUID);

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
                    sendReceive = new SendReceive(socket);

                    // sendReceive işini başlat
                    sendReceive.start();

                    // işlemi kırk bırak
                    break;
                }
            }
        }
    }
    private class SendReceive extends Thread {
        // InputStream değişkeni
        private final InputStream inputStream;

        // OutputStream değişkeni
        private final OutputStream outputStream;

        // SendReceive constructor
        public SendReceive(BluetoothSocket socket) {

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
    }*/
}


    /*public class AcceptThread extends Thread {
        private BluetoothServerSocket serverSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return ;
                }
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("NAME", Uuid.MY_UUID);
            } catch (IOException e) {
            }
            serverSocket = tmp;
        }

        @SuppressLint("SuspiciousIndentation")
        public void run() {
            BluetoothSocket socket = null;
            //assert socket !=null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    //if(socket!=null)
                        socket = serverSocket.accept();
                        tvstatus.setText("CONNECTED");
                } catch (IOException e) {
                    break;
                }

                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                  //  mHandler.obtainMessage(CONNECTED).sendToTarget();
                }
            }
        }
    }*/


