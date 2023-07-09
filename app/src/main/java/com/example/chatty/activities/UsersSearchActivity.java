package com.example.chatty.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatty.R;
import com.example.chatty.adapters.UsersRecyclerAdapter;
import com.example.chatty.models.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsersSearchActivity extends AppCompatActivity {
    RecyclerView rvUsers;
    EditText editSearch;
    UsersRecyclerAdapter usersRecyclerAdapter;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser currentUser=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_users_search);
        rvUsers=(RecyclerView) findViewById(R.id.rvUsers);
        editSearch=(EditText) findViewById(R.id.editSearch);
        List<Users> users=new ArrayList<>();

        //firebase
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        currentUser=mAuth.getCurrentUser();

        /////////////////////////////////////////////////////
        usersRecyclerAdapter=new UsersRecyclerAdapter();
        rvUsers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        db.collection("users").whereNotEqualTo("email",currentUser.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (Iterator<QueryDocumentSnapshot> it = queryDocumentSnapshots.iterator(); it.hasNext(); ) {
                    QueryDocumentSnapshot document = it.next();
                    String uuid=document.getId();
                    String email=document.getString("email");
                    String fullName=document.getString("fullname");
                    users.add(new Users(email,fullName,null,uuid));


                }
                usersRecyclerAdapter.setItems(users);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



      //inc rodrigueDO
        rvUsers.setAdapter(usersRecyclerAdapter);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usersRecyclerAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}