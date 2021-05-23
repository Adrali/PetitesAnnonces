package com.example.petitesannonces;

public class MessageModel {
    String message;
    boolean fromYou;

    MessageModel(String message, boolean fromYou){
        this.message = message;
        this.fromYou = fromYou;
    }

    public String getMessage() {
        return message;
    }

    public boolean isFromYou() {
        return fromYou;
    }
}
