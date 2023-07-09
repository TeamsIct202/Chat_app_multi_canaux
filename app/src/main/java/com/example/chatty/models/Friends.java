package com.example.chatty.models;

public class Friends {
    private  String name;
    private  String lastmsg;
    private  String image;
    private  Long timestamp;

    public Friends(String name, String lastmsg, String image, Long timestamp) {
        this.name = name;
        this.lastmsg = lastmsg;
        this.image = image;
        this.timestamp = timestamp;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
