package com.example.chatty.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import  android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatty.R;
import com.example.chatty.adapters.ChatRecyclerAdapter;
import com.example.chatty.bluetooth.BluetoothMessage;
import com.example.chatty.models.Friends;
import com.example.chatty.models.Message;
import com.example.chatty.models.MessageSorter;
import com.example.chatty.models.MessageSorting;
import com.example.chatty.models.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    FloatingActionButton fabSendMessage=null;
    EditText editMessage;
    RecyclerView rvChatlist;
    ChatRecyclerAdapter chatRecyclerAdapter;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser currentUser=null;
    boolean isConnected=true;
    BluetoothMessage bluetoothMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inc
        // firebase
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        currentUser=mAuth.getCurrentUser();
        bluetoothMessage=BluetoothMessage.getInstance();

        /////////////////////////////////////////////////////







        /////////////////////////////////////////////////////////////
        String userUuid=getIntent().getStringExtra("friend");
        db.collection("users").document(userUuid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getData().isEmpty()) {
                    return;
                } else {

                    Users user = documentSnapshot.toObject(Users.class);
                    user.setUuid(userUuid);
                    setUserData(user);



                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    private void setUserData(Users user) {
        getSupportActionBar().setTitle(user.getFullName());
        setContentView(R.layout.activity_chat);
        fabSendMessage=(FloatingActionButton) findViewById(R.id.fabSendMessage);
        editMessage=(EditText) findViewById(R.id.editMessage);
        rvChatlist=(RecyclerView) findViewById(R.id.rvChatList);
        List<Message> messages=new ArrayList<>();
        chatRecyclerAdapter=new ChatRecyclerAdapter();
        List<Message> sortedMessage=MessageSorter.sortMessagesByTimestamp(messages);

        chatRecyclerAdapter.setItems(sortedMessage);
        rvChatlist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvChatlist.setAdapter(chatRecyclerAdapter);




        /////////////////////////////////////////////////////////




        //////////////////////////////////////////////////////
        fabSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=editMessage.getText().toString();
                if(!message.isEmpty())
                {
                    Message message1=new Message(
                            currentUser.getUid(),
                            user.getUuid(),
                            message,
                            System.currentTimeMillis(),
                            false
                    );
                    /////////////////////////////////////////////////////////////////////////////////////
                    db.collection("users").document().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                        }
                    });
                    /////////////////////////////////////////////////////////////////////////////////////
                    db.collection("messages").add(message1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            rvChatlist.scrollToPosition(messages.size()-1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChatActivity.this, "Couldn't send meessage", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //Friends friend=new Friends(user.getFullName(),messag)
                  //  db.collection("users").document(currentUser.getUid()).collection("friends").document(user.getUuid()).add(friend)
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                editMessage.setText("");
            }
        });
        Query sentQuery=db.collection("messages").whereEqualTo("sender",currentUser.getUid()).whereEqualTo("receiver",user.getUuid()).orderBy("timestamp", Query.Direction.ASCENDING);
        Query receivedQuery=db.collection("messages").whereEqualTo("sender",user.getUuid()).whereEqualTo("receiver",currentUser.getUid()).orderBy("timestamp", Query.Direction.ASCENDING);
        sentQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                {

                }
                else

                 {
                     assert value != null;
                      for (QueryDocumentSnapshot document : value) {
                        Message  message=document.toObject(Message.class);
                        message.setReceived(false);
                        if(!messages.contains(message))
                            messages.add(message);

                    }
                      if(!messages.isEmpty())
                      {
                          List<Message> sortedMessage= MessageSorting.getSortedMessagesByTimestamp(messages);

                          chatRecyclerAdapter.setItems(sortedMessage);
                          rvChatlist.scrollToPosition(messages.size()-1);


                      }
                }
            }
        });
        //receiver
        receivedQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                {

                }
                else

                {
                    assert value != null;
                    for (QueryDocumentSnapshot document : value) {
                        Message  message=document.toObject(Message.class);
                        message.setReceived(true);
                        if(!messages.contains(message))
                            messages.add(message);

                    }
                    if(!messages.isEmpty())
                    {
                        List<Message> sortedMessage= MessageSorting.getSortedMessagesByTimestamp(messages);
                        chatRecyclerAdapter.setItems(sortedMessage);
                        rvChatlist.scrollToPosition(messages.size()-1);


                    }
                }
            }
        });
    }
    private boolean isMobileDataEnabled() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE
                && networkInfo.isConnected();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private List<Message> bubbleSort(List<Message> arr) {
        List<Message> arr2;
        int n = arr.size();
        Message temp = new Message();
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(arr.get(j-1).getTimestamp() > arr.get(j).getTimestamp()){
                    //swap elements
                   /* temp = arr.get(j-1);
                    arr[j-1] = arr[j];
                    arr[j] = temp;*/
                    Collections.swap(arr, j-1, j);
                }

            }
        }
        arr2=arr;
  return arr2;
    }
    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            isConnected=true;
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            isConnected=false;
        }

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            final boolean unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);
        }
    };
}