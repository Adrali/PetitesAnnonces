package com.example.petitesannonces;

public class UserModel {
    int id_user;
    String name;
    String phone;
    String mail;

    public UserModel(int id_user, String name, String phone, String mail) {
        this.id_user = id_user;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
    }

    public int getId_user() {
        return id_user;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }
}
