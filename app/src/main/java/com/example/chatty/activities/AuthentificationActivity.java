package com.example.chatty.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chatty.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthentificationActivity extends AppCompatActivity {
    TextView tvRegister=null;
    TextInputLayout textInputLayoutEmail,textInputLayoutPassword;
    MaterialButton btnConnect=null;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        // partie firebase
        mAuth = FirebaseAuth.getInstance();



        /////////////////////////////////////////////////////
        textInputLayoutEmail=(TextInputLayout) findViewById(R.id.layoutTextInputEmail);
        textInputLayoutPassword=(TextInputLayout) findViewById(R.id.layoutTextInputPassword);

        tvRegister=(TextView) findViewById(R.id.tvRegister);
        tvRegister=(TextView) findViewById(R.id.tvRegister);
        tvRegister=(TextView) findViewById(R.id.tvRegister);
        btnConnect=(MaterialButton) findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=textInputLayoutEmail.getEditText().getText().toString();
                String password=textInputLayoutPassword.getEditText().getText().toString();
                textInputLayoutPassword.setErrorEnabled(false);
                textInputLayoutEmail.setErrorEnabled(false);


                if(email.isEmpty()||password.isEmpty())
                {
                    if(password.isEmpty())
                    {
                        textInputLayoutPassword.setError("password required");
                        textInputLayoutPassword.setErrorEnabled(true);
                    }
                    if(email.isEmpty())
                    {
                        textInputLayoutEmail.setError("Email required");
                        textInputLayoutEmail.setErrorEnabled(true);

                    }
                }
                else
                {
                    signIn(email,password);

                }

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    private void signIn(String email, String password) {
        //

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    textInputLayoutPassword.setError("erreur authentification");
                    textInputLayoutEmail.setErrorEnabled(true);
                    textInputLayoutPassword.setErrorEnabled(true);

                }
            }
        });

    }
}