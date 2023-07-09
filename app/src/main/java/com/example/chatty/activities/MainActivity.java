package com.example.chatty.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.chatty.R;
import com.example.chatty.adapters.FriendsRecyclerAdapter;
import com.example.chatty.models.Friends;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
RecyclerView rvFriends=null;
FloatingActionButton fabchat;
FloatingActionButton acceptConn;
FriendsRecyclerAdapter friendsRecyclerAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvFriends=(RecyclerView) findViewById(R.id.rvFriends);
        fabchat=(FloatingActionButton) findViewById(R.id.fabChat);
        acceptConn=(FloatingActionButton)findViewById(R.id.accept);

        rvFriends.setAdapter(friendsRecyclerAdapter);
        fabchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),UsersSearchActivity.class);
                startActivity(i);
            }
        });
        acceptConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),AcceptConn.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onResume()
    {
        super.onResume();
        List<Friends> friends=new ArrayList<>();
        //friends.add(new Friends("RodrigueDo","hello","", 6178L));
        /////////////////////////////////////////
        rvFriends.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        friendsRecyclerAdapter= new FriendsRecyclerAdapter();
        friendsRecyclerAdapter.setItems(friends);
        rvFriends.setLayoutManager(new LinearLayoutManager(getApplicationContext()
        ));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemLogout:
                FirebaseAuth mAuth= FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent i=new Intent(getApplicationContext(),AuthentificationActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.itemSettings:
                Intent it=new Intent(getApplicationContext(),SettingsActivity.class);
                 startActivity(it);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}