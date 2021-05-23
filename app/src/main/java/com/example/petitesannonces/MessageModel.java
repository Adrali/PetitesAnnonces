package com.example.petitesannonces;

public class MessageModel {
    public String message;
    public boolean fromYou;

    MessageModel(String message, boolean fromYou){
        this.message = message;
        this.fromYou = fromYou;
    }
}
