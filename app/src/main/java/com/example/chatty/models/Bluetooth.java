package com.example.chatty.models;

public class Bluetooth {
    private String name;
    private String adresse;
    private short rssi;
    public Bluetooth(String name,String adresse,short rssi)
    {
        this.name=name;
        this.adresse=adresse;
        this.rssi=rssi;

    }
    public Bluetooth(String name,String adresse)
    {
        this.name=name;
        this.adresse=adresse;
     //   this.rssi=rssi;

    }

    public short getRssi() {
        return rssi;
    }


    public String getAdresse() {
        return adresse;
    }

    public String getName() {
        return name;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRssi(short rssi) {
        this.rssi = rssi;
    }
}
