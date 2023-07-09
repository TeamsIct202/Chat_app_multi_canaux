package com.example.chatty.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chatty.R;
import com.example.chatty.models.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.Any;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SettingsActivity extends AppCompatActivity {
 ShapeableImageView ivUser=null;
 TextInputLayout layoutTextInputEmail=null;
 TextInputLayout layoutTextInputName=null;
 MaterialButton btnSave;
 FirebaseAuth mAuth;
 FirebaseFirestore db;
FirebaseUser currentUser=null;
Boolean isImageChanged=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        currentUser=mAuth.getCurrentUser();

        ivUser=(ShapeableImageView)findViewById(R.id.ivUser);
        layoutTextInputEmail=(TextInputLayout) findViewById(R.id.layoutTextInputEmail);
        layoutTextInputName=(TextInputLayout) findViewById(R.id.layoutTextInputName);
        btnSave=(MaterialButton) findViewById(R.id.btnSave);
        ActivityResultLauncher<String> pickImage =
                registerForActivityResult(new ActivityResultContracts.GetContent(), uris -> {
                    ivUser.setImageURI(uris);
                    isImageChanged=true;

                });

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage.launch("image/*");
            }
        });
        ////////////////////////////////////////////////////////////
        if(currentUser!=null)
        {
            db.collection("users").document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot !=null)
                    {
                        Users user=documentSnapshot.toObject(Users.class);
                        if(user!=null)
                        {
                            user.setUuid(currentUser.getUid());
                            // jajoute les infos automatiquement dans les formulaires
                            layoutTextInputEmail.getEditText().setText(user.getEmail());
                            layoutTextInputName.getEditText().setText(user.getFullName());
                            setUserData(user);
                        }
                    }
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Users", Toast.LENGTH_SHORT).show();
        }




        ///////////////////////////////////////////////////////////

    }
    private void setUserData(Users user)
    {
        layoutTextInputEmail.getEditText().setText(user.getEmail());
        layoutTextInputName.getEditText().setText(user.getFullName());
        //user.setImage(Glide.with(getApplicationContext()).load("").placeholder(R.drawable.ic_person).into(ivUser));
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                layoutTextInputEmail.setErrorEnabled(false);
               // String fullname=layoutTextInputName.getEditText().getText().toString();
                 // Create a reference to "mountains.jpg
                //
                if(isImageChanged) {
                    upLoadImageToFirebaseStorage(user);
                }
                else if(layoutTextInputName.getEditText().getText().toString()!=user.getFullName())

                {

                    updateUserInfo(user);
                }
                else {
                    Toast.makeText(getApplicationContext()," A JOUR",Toast.LENGTH_SHORT).show();
                    layoutTextInputName.clearFocus();
                }
// Create a reference to 'images/mountains.jpg'
               // StorageReference mountainImagesRef = storageRef.child("images/${UUI}");

// While the file names are the same, the references point to different files
                //mountainsRef.getName().equals(mountainImagesRef.getName());    // true
               // mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
                updateUserInfo(user);
            }
        });

    }

    private void upLoadImageToFirebaseStorage(Users user) {
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference imageRef = storageRef.child(String.format("images/%s", user.getUuid()));
        BitmapDrawable drawable = (BitmapDrawable) ivUser.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream baos= new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data=baos.toByteArray();
// UPLOAD FIREBASE STORAGE
        UploadTask uploadTask= imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        user.setImage(uri.toString());
                        updateUserInfo(user);

                    }
                });
            }
        });
    }

    private void updateUserInfo(Users user) {
        Map<String, Object> updatedUser = new HashMap<>();
        updatedUser.put("fullname",layoutTextInputEmail.getEditText().getText().toString());
        updatedUser.put("image",user.getImage());
        db.collection("users").document(user.getUuid()).update(updatedUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "correctement modifier", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                layoutTextInputName.setError("an error ");
                layoutTextInputName.setErrorEnabled(true);
            }
        });
    }
}