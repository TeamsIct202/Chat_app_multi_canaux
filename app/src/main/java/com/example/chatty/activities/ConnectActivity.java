package com.example.chatty.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatty.R;
import com.example.chatty.bluetooth.BluetoothChatService;
import com.example.chatty.bluetooth.BluetoothMessage;
import com.example.chatty.bluetooth.ClientClass;
import com.example.chatty.bluetooth.SendReceive;
import com.example.chatty.bluetooth.Uuid;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectActivity extends AppCompatActivity {
    String dvName;
    String dvAdresse;
    TextView tvName;
    TextView tvAdresse;
    TextView status;
    Button connect;
    BluetoothAdapter bluetoothAdapter;
    BluetoothMessage bluetoothMessage;
    BluetoothDevice device;
    boolean isConnected=false;
    SendReceive sendReceive;
    //ClientClass clientClass;
    Handler handler;
    BluetoothChatService bluetoothChatService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        ////////////////////////////////////////////////////////////////////////////////////////////
        tvName=(TextView)findViewById(R.id.dvname);
        tvAdresse=(TextView)findViewById(R.id.dvadresse);
        status=(TextView)findViewById(R.id.status);
        connect=(Button)findViewById(R.id.connect);
        dvAdresse=getIntent().getStringExtra("adresse");
        dvName=getIntent().getStringExtra("name");
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        device=bluetoothAdapter.getRemoteDevice(dvAdresse);
        tvName.setText(dvName);
        tvAdresse.setText(dvAdresse);
        //clientClass=new ClientClass(device,getApplicationContext());
        handler=new Handler();
        bluetoothChatService=BluetoothChatService.getInstance(handler,getApplicationContext());
       /* if(device==null)
        {
            Toast.makeText(this, "A problem", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Not a prob", Toast.LENGTH_SHORT).show();*/
        ////////////////////////////////////////////////////////////////////////////////////////////
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              bluetoothChatService.connectDevice(device);

            }
        });


    }
    /*private class ClientClass extends Thread {

        // BluetoothDevice nesnesi
        private BluetoothDevice device;

        // BluetoothSocket nesnesi
        private BluetoothSocket socket;

        // ClientClass yapıcı sınıfı
        public ClientClass(BluetoothDevice device1) {

            // nesneye device değerine device1'i ata
            device = device1;

            try {
                // uuid değerine socket işine ata
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    socket = device.createRfcommSocketToServiceRecord(Uuid.MY_UUID);
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
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    socket.connect();

                    // bir mesaj örneği alabilmek için bu metot kullanılır. Alınan mesaja istediğimiz değerler verilir.
                   // Message message = Message.obtain();

                    // kullanıcı tanımlı mesaj kodudur. Int tipinde tanımlanır.
                    //  message.what = STATE_CONNECTED;

                    // mesaj kuyruğunun sonuna bir mesaj eklemeyi sağlar.
                  //  handler.sendMessage(message);

                    // sendReceive nesnesi işlemi
                    sendReceive = new SendReceive(socket);

                    // sendReceive başlat
                    sendReceive.start();
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
    }*/ //
  /*  private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket=null;
        private final BluetoothDevice mmDevice;
        public ConnectThread connectThread;

        private ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;
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
            // Get a BluetoothSocket to connect with the given BluetoothDevicegt
            try {
                // MY_UUID is the app's UUID string, also used by the server code

                tmp = device.createRfcommSocketToServiceRecord(Uuid.MY_UUID);
                isConnected=true;
            } catch (IOException e) {
                isConnected=false;
            }
            mmSocket = tmp;
            bluetoothMessage.getInstance();
            bluetoothMessage.setMmSocket(mmSocket);
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            bluetoothAdapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
               // mHandler.obtainMessage(CONNECTING).sendToTarget();


                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }

            // Do work to manage the connection (in a separate thread)
//            bluetooth_message = "Initial message"
//            mHandler.obtainMessage(MESSAGE_WRITE,mmSocket).sendToTarget();
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }

    }*/
}