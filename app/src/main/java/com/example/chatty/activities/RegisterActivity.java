package com.example.chatty.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatty.R;
import com.example.chatty.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    MaterialButton btnRegister;
    TextInputLayout layoutTextInputEmail;

    TextInputLayout layoutTextInputName;
    TextInputLayout layoutTextInputPassword;
    TextInputLayout layoutTextInputConfirmPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        layoutTextInputEmail=(TextInputLayout) findViewById(R.id.LayoutTextInputEmail);
        layoutTextInputName=(TextInputLayout) findViewById(R.id.LayoutTextInputName);
        layoutTextInputPassword=(TextInputLayout) findViewById(R.id.layoutTextInputPassword);
        layoutTextInputConfirmPassword=(TextInputLayout) findViewById(R.id.layoutTextConfirmInputPassword);
        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initErrors();
                String email=layoutTextInputEmail.getEditText().getText().toString();
                String name=layoutTextInputName.getEditText().getText().toString();
                String password=layoutTextInputPassword.getEditText().getText().toString();
                String confirmpassword=layoutTextInputConfirmPassword.getEditText().getText().toString();
                if(email.isEmpty()||password.isEmpty()||confirmpassword.isEmpty()||name.isEmpty())
                {
                    if(password.isEmpty())
                    {
                        layoutTextInputPassword.setError("password required");
                        layoutTextInputPassword.setErrorEnabled(true);
                    }
                    if(email.isEmpty())
                    {
                        layoutTextInputEmail.setError("Email required");
                        layoutTextInputEmail.setErrorEnabled(true);

                    }
                    if(password.isEmpty())
                    {
                        layoutTextInputPassword.setError("password required");
                        layoutTextInputPassword.setErrorEnabled(true);

                    }
                    if(confirmpassword.isEmpty())
                    {
                        layoutTextInputConfirmPassword.setError("confirm password required");
                        layoutTextInputConfirmPassword.setErrorEnabled(true);

                    }
                }
                else
                {
                    if(!password.equals(confirmpassword))

                    {
                        Toast.makeText(RegisterActivity.this, password, Toast.LENGTH_SHORT).show();
                        Toast.makeText(RegisterActivity.this, confirmpassword, Toast.LENGTH_SHORT).show();

                        layoutTextInputConfirmPassword.setError("password not matching");
                        layoutTextInputConfirmPassword.setErrorEnabled(true);
                    }
                    else
                    {
                        // utilisateur auth firebase RodrigueDo
                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    // creation dans firestore rodriguedo
                                    FirebaseUser curentUser=mAuth.getCurrentUser();
                                    Map<String, String> user = new HashMap<>();
                                    user.put("fullname",name);
                                    user.put("email",email);
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("users").document(curentUser.getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(i);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });


                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }


            }
        });

    }

    private void initErrors() {
        layoutTextInputEmail.setErrorEnabled(false);
        layoutTextInputPassword.setErrorEnabled(false);
        layoutTextInputConfirmPassword.setErrorEnabled(false);
        layoutTextInputName.setErrorEnabled(false);
    }
}