package com.example.sms_group3;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import android.view.View;
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_SMS_PERMISSION = 100;

    private EditText editTextPhoneNumber;
    private EditText editTextMessage;
    private Button buttonSend;
    private ListView listViewSms;
    private long installationTime;
    private SmsContentObserver smsContentObserver;
    private static final int REQUEST_CODE_PICK_CONTACT = 1;
    private String selectedContactNumber;
    private ActivityResultLauncher<Intent> pickContactLauncher;
    private ActivityResultLauncher<Intent> contactPickerLauncher;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonSelectContact = findViewById(R.id.buttonSelectContact);
        buttonSelectContact.setOnClickListener(view -> openContacts());

        contactPickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri contactUri = data.getData();

                    String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
                    Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);

                    if (cursor != null && cursor.moveToFirst()) {
                        int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        String selectedContactName = cursor.getString(nameIndex);
                        cursor.close();
                    selectedContactName=getContactNumber(selectedContactName);
                        EditText editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
                        editTextPhoneNumber.setText(selectedContactName);
                    }
                }
            }
        });
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        installationTime = preferences.getLong("installationTime", 0);

        if (installationTime == 0) {
            // Première exécution de l'application, enregistrer le temps d'installation
            installationTime = System.currentTimeMillis();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong("installationTime", installationTime);
            editor.apply();
        }
        // On initialise Realm
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("sms_group.realm")
                .build();
        Realm.setDefaultConfiguration(configuration);

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonSend = findViewById(R.id.buttonSend);
        listViewSms = findViewById(R.id.listViewSms);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = String.valueOf(editTextPhoneNumber.getText());

                // Vérifier si le champ est vide
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez saisir un numéro de téléphone", Toast.LENGTH_SHORT).show();
                    return; // Arrête l'exécution de la méthode onClick
                }

                String contactName = getContactName(getApplicationContext(), phoneNumber);
                String phone = getContactNumber(phoneNumber);
                Intent intent = new Intent(MainActivity.this, ConversationActivity.class);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS}, REQUEST_SMS_PERMISSION);
        } else {
            // On affiche les conversations
            showSmsList();
        }

        smsContentObserver = new SmsContentObserver(new Handler());
        getContentResolver().registerContentObserver(Telephony.Sms.CONTENT_URI, true, smsContentObserver);


    }
    private void openContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        contactPickerLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    // On affiche les conversations
                    showSmsList();
                }
            } else {
                Toast.makeText(MainActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // On ferme l'instance Realm
        Realm.getDefaultInstance().close();
    }



    private void showSmsList() {
        HashMap<String, ArrayList<String>> conversations = new HashMap<>();

        // On crée une instance Realm
        Realm realm = Realm.getDefaultInstance();

        Cursor cursor = getContentResolver().query(
                Uri.parse("content://sms/"),
                new String[]{
                        "DISTINCT " + Telephony.Sms.ADDRESS,
                        Telephony.Sms.THREAD_ID,
                        Telephony.Sms.BODY,
                        Telephony.Sms.DATE
                },
                null, null,
                Telephony.Sms.DATE + " DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String address = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                String body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY));
                long date = cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE));
                String contactName = getContactName(getApplicationContext(), address);
                String contact="";
                if (contactName != null) {
                    contact=address;
                    address = contactName + " (" + address + ")";
                }
                String conversation = body + "\n" + getDate(date);
                if (!conversations.containsKey(address)) {
                    conversations.put(address, new ArrayList<>());
                }
                ArrayList<String> messages = conversations.get(address);
                messages.add(conversation);
                conversations.put(address, messages);


                // Save the message to the Realm database
                realm.beginTransaction();
                realm.copyToRealm(new Message(
                        System.currentTimeMillis(),  // génère un identifiant unique à chaque fois
                        address, conversation, date));
                realm.commitTransaction();
            }
            cursor.close();
        }
        ArrayList<String> conversationsList = new ArrayList<>();
        for (String address : conversations.keySet()) {
            ArrayList<String> messages = conversations.get(address);
            String conversation = address + " (" + messages.size() + " messages)\n";
            for (String message : messages) {
                conversation += message + "\n";
                break;
            }
            conversationsList.add(0, conversation);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, conversationsList);
        listViewSms.setAdapter(adapter);

        listViewSms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String conversation = (String) adapterView.getItemAtPosition(i);
                String[] lines = conversation.split("\n");
                String address = lines[0].substring(0, lines[0].indexOf("(")-1);
                String phone = getContactNumber(address);
                Intent intent = new Intent(MainActivity.this, ConversationActivity.class);
                intent.putExtra("phoneNumber", phone);
                startActivity(intent);
            }
        });
    }
    @SuppressLint("Range")
    private String getContactNumber(String contactName) {
        // String contactNumber = null;
        ContentResolver contentResolver = getContentResolver();
        String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?";
        String[] selectionArgs = { contactName };
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, selection, selectionArgs, null);

        if (cursor != null && cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
           contactName = contactName.replaceAll("\\s", "");
            contactName = contactName.replaceAll("-", "");


            cursor.close();
        }

        return contactName;
    }

    @SuppressLint("Range")
    private String getDate(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date(date));
    }

    private String getContactName(android.content.Context context, String phoneNumber) {
        android.database.Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                            Uri.encode(phoneNumber)),
                    new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                cursor.close();
                return name;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private class SmsContentObserver extends ContentObserver {

        public SmsContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            // On affiche les conversations
            showSmsList();
        }

    }}




