package com.example.sms_group3;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Message extends RealmObject {

    @PrimaryKey
    private long id;
    private String phoneNumber;
    private String text;
    private long date;
    private String type;

    // Constructeurs, getters et setters

    public Message() {
        // Constructeur vide requis par Realm
    }

    public Message(long id) {
        this.id = id;
    }

    public Message(long currentTimeMillis, String address, String conversation, long date) {
        this.id = currentTimeMillis; // Utiliser currentTimeMillis comme identifiant unique
        this.phoneNumber = address;
        this.text = conversation;
        this.date = date;
        this.type = ""; // Définir le type du message selon vos besoins
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}