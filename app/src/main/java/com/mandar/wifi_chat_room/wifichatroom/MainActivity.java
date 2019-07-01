package com.mandar.wifi_chat_room.wifichatroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mandar.wifi_chat_room.wifichatroom.Client.ClientActivity;
import com.mandar.wifi_chat_room.wifichatroom.R;
import com.mandar.wifi_chat_room.wifichatroom.Server.ServerActivity;

public class MainActivity extends AppCompatActivity {

    private Button beserver;
    private Button beclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beserver = (Button) findViewById(R.id.be_server);
        beclient = (Button) findViewById(R.id.be_client);
        beserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchServerActivity();
            }
        });

        beclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchClientActivity();
            }
        });
    }

    private void launchServerActivity() {

        Intent intent = new Intent(this, ServerActivity.class);
        startActivity(intent);
    }

    private void launchClientActivity() {

        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }
}
