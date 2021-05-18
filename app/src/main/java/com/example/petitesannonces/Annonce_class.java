package com.example.petitesannonces;

import android.graphics.Bitmap;

public class Annonce_class {
    int id_annonce;
    int id_proprietaire;
    String description;
    double prix;
    Bitmap image;


    Annonce_class(int id_annonce, int id_proprietaire, String description, double prix, Bitmap image){
        this.id_annonce = id_annonce;
        this.id_proprietaire = id_proprietaire;
        this.description = description;
        this.prix = prix;
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public double getPrix() {
        return prix;
    }

    public int getId_annonce() {
        return id_annonce;
    }

    public int getId_proprietaire() {
        return id_proprietaire;
    }

    public String getDescription() {
        return description;
    }
}

