package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Context contexteActuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        contexteActuel = this;
        ((Button)findViewById(R.id.btn_recherche_annonce)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMenu = new Intent().setClass(contexteActuel, Liste_Annonces.class);
                startActivity(iMenu);
            }
        });
        ((Button)findViewById(R.id.btn_annonces_fav)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMenu = new Intent().setClass(contexteActuel, Liste_Annonces.class);
                startActivity(iMenu);
            }
        });
        ((Button)findViewById(R.id.btn_mes_annonces)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMenu = new Intent().setClass(contexteActuel, Liste_Annonces.class);
                startActivity(iMenu);
            }
        });
        ((Button)findViewById(R.id.btn_publier_annonces)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMenu = new Intent().setClass(contexteActuel, AjoutAnnonce.class);
                startActivity(iMenu);
            }
        });

        ((Button)findViewById(R.id.btn_deconnexion)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMenu = new Intent().setClass(contexteActuel, MainActivity.class);
                startActivity(iMenu);
            }
        });
    }
}