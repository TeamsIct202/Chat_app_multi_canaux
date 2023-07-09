package com.example.sms_group3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.Realm;
import io.realm.RealmChangeListener;
public class ConversationActivity extends AppCompatActivity {

    private static final int SMS_SEND_PERMISSION_REQUEST_CODE = 2;

    private String phoneNumber;
    private EditText editTextMessage;
    private TextView status;
    private ListView listViewMessages;
    private Button buttonSend;
    private static final String SENT = "SMS_SENT";
    private static final String DELIVERED = "SMS_DELIVERED";
    private RealmChangeListener<Realm> realmChangeListener;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();

        realmChangeListener = new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                // Mettre à jour l'affichage
                showMessages();
            }
        };

        realm.addChangeListener(realmChangeListener);


        editTextMessage = findViewById(R.id.editTextMessage);
        listViewMessages = findViewById(R.id.listViewMessages);
        buttonSend = findViewById(R.id.buttonSend);
        status = findViewById(R.id.status);
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        String contactname = getContactName(phoneNumber);
        setTitle(contactname);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, SMS_SEND_PERMISSION_REQUEST_CODE);
        }

        showMessages();

        buttonSend.setOnClickListener(view -> {
            if (phoneNumber==null){
                Toast.makeText(getApplicationContext(), "L'expediteur ne prend pas en charge les reponses", Toast.LENGTH_SHORT).show();
                return;
            }else {
                String message = editTextMessage.getText().toString().trim();
                if (!message.isEmpty()) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        sendSms(phoneNumber, message);
                    } else {
                        ActivityCompat.requestPermissions(ConversationActivity.this,
                                new String[]{Manifest.permission.SEND_SMS},
                                SMS_SEND_PERMISSION_REQUEST_CODE);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Le champ du message est vide", Toast.LENGTH_SHORT).show();
                }
            }});
    }

    @SuppressLint("Range")
    private String getContactName(String phoneNumber) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            cursor.close();
        }

        return phoneNumber;
    }

    private void showMessages() {
        ArrayList<String> messages = new ArrayList<>();
        String[] projection = new String[]{"_id", "address", "body", "date", "type"};

        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/"), projection,
                "address = ? OR address = ?", new String[]{phoneNumber, "+237" + phoneNumber.substring(1)},
                "date ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String body = cursor.getString(cursor.getColumnIndex("body"));
                @SuppressLint("Range") long dateInMillis = cursor.getLong(cursor.getColumnIndex("date"));
                @SuppressLint("Range") int type = cursor.getInt(cursor.getColumnIndex("type"));

                String messageType = (type == Telephony.Sms.MESSAGE_TYPE_SENT) ? "sent" : "received";
                String messageDisplay = "";

                if (messageType.equals("sent")) {
                    messageDisplay = "> " + body + " " + "\n"+DateFormat.format("dd/MM/yyyy HH:mm", new Date(dateInMillis));
                } else {
                    messageDisplay = body + " " + "\n"+DateFormat.format("dd/MM/yyyy HH:mm", new Date(dateInMillis)) + " <";
                }

                messages.add(messageDisplay);
            } while (cursor.moveToNext());
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.message_item, messages) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    // Utilise le fichier message_item2.xml pour les messages reçus
                    String message = getItem(position);
                    boolean isSentMessage = message.startsWith(">");

                    if (isSentMessage) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_item, parent, false);
                    } else {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_item2, parent, false);
                    }
                }

                TextView textViewMessage = convertView.findViewById(R.id.textViewMessage);
                String message = getItem(position);

                boolean isSentMessage = message.startsWith(">");

                if (isSentMessage) {
                    textViewMessage.setGravity(Gravity.END);
                    textViewMessage.setBackgroundResource(R.drawable.sent_message_background);
                } else {
                    textViewMessage.setGravity(Gravity.START);
                    textViewMessage.setBackgroundResource(R.drawable.received_message_background);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textViewMessage.getLayoutParams();
                    layoutParams.gravity = Gravity.END;
                    textViewMessage.setLayoutParams(layoutParams);
                }
                message = message.replace("<", "").replace(">", "");
                textViewMessage.setText(message);

                return convertView;
            }
        };

        listViewMessages.setAdapter(adapter);
    }
    private void sendSms(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(getApplicationContext(), 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent arg1) {
                String result="";
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent", Toast.LENGTH_SHORT).show();
                        result="succes";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "echec", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", Toast.LENGTH_SHORT).show();
                        break;
                }
                if(result=="succes"){
                    showMessages();
                }
                else{
                    Toast.makeText(getBaseContext(), "echec d'envoi", Toast.LENGTH_SHORT).show();

                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);


        // editTextMessage.setText(" ");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_SEND_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted to send SMS", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
