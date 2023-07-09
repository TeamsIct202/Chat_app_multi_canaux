package com.example.chatty.models;

public class Users {
    private String email;
    private String fullname;
    private String image;
    private String uuid;
    public Users()
    {

    }

    public Users(String email, String fullName, String image) {
        this.email = email;
        this.fullname = fullName;
        this.image = image;
    }
    public Users(String email, String fullName, String image,String uuid) {
        this.email = email;
        this.fullname = fullName;
        this.image = image;
        this.uuid=uuid;
    }

    public String getFullName() {
        return fullname;
    }

    public void setFullName(String fullName) {
        this.fullname = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
