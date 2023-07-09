package com.example.chatty.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.chatty.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               FirebaseAuth mAuth=FirebaseAuth.getInstance();
               FirebaseUser currentUser=mAuth.getCurrentUser();
               if(currentUser!=null)
               {
                   Intent i=new Intent(getApplicationContext(),MainActivity.class);
                   startActivity(i);
               }
               else
               {
                   Intent i=new Intent(getApplicationContext(),AuthentificationActivity.class);
                   startActivity(i);
               }

           }
       },3000);

    }
}