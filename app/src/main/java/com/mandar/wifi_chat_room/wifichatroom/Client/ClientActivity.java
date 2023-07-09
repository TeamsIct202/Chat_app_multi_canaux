package com.mandar.wifi_chat_room.wifichatroom.Client;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.*;
import java.net.*;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;

import com.mandar.wifi_chat_room.wifichatroom.R;



public class ClientActivity extends AppCompatActivity {
    Button send_but;
    EditText messagespace;
    Handler handler = new Handler();
    String ip,name;
    final Context context = this;
    Socket clientSocket;
    PrintStream ps = null;
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);

        messagespace = (EditText) findViewById(R.id.messagespace);
        send_but = (Button) findViewById(R.id.send_but);


        listView = (ListView) findViewById(R.id.msgview);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right);
        listView.setAdapter(chatArrayAdapter);

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });


        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompt_client, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptsView);

        final EditText uname = (EditText) promptsView.findViewById(R.id.uname);
        final EditText sip = (EditText) promptsView.findViewById(R.id.sip);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        name = uname.getText().toString();
                        ip = sip.getText().toString();
                        Thread sc = new Thread(new StartCommunication());
                        sc.start();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private boolean sendChatMessage(String str) {
        String arr[] = str.split(":");
        if(arr.length == 1) {
            if (str.contains("Joined") || str.contains("Connected to"))
                chatArrayAdapter.add(new ChatMessage(false, "<font color='#00AA00'>*** " + str + " ***</font>"));
            else chatArrayAdapter.add(new ChatMessage(false, "<font color='#AA0000'>*** " + str + " ***</font>"));
        }else if (!arr[0].equals(name))
            chatArrayAdapter.add(new ChatMessage(false, "<font color='#0077CC'>" + arr[0] + "</font><br/>" + arr[1]));
        else
            chatArrayAdapter.add(new ChatMessage(true, arr[1]));
        return true;
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (ps != null) {
            ps.println("Ex1+:" + name);
            ps.close();
        }
    }

    class StartCommunication implements Runnable {

        @Override
        public void run()
        {
            try {
                InetSocketAddress inetAddress = new InetSocketAddress(ip, 55555);
                clientSocket = new Socket();
                clientSocket.connect(inetAddress,7000);
                ps = new PrintStream(clientSocket.getOutputStream());
                sendChatMessage("Connected to "+ ip + " !!\n");
                ps.println("j01ne6:" + name);
                send_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Thread st = new Thread(new SendThread());
                        st.start();
                    }
                });

                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while(true)
                {
                    final String str = br.readLine();
                    if(str.equalsIgnoreCase("exit"))
                    {
                        sendChatMessage("Server Closed the Connection!");
                        Thread.sleep(2000);
                        finish();
                        break;
                    }
                    sendChatMessage(str);
                }
            }catch(final Exception e){
                sendChatMessage("Not able to connect!");
                try {Thread.sleep(2000);} catch (Exception exx){ }
                finish();
            }
        }
    }

    class SendThread implements Runnable {
        @Override
        public void run() {
            try {
                String message = messagespace.getText().toString();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        messagespace.setText("");
                    }
                });
                message = name + ": " + message;
                ps.println(message);
                ps.flush();
            }
            catch (Exception e) {

            }
        }
    }
}
