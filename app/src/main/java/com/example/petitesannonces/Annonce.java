package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Annonce extends AppCompatActivity {
    int id_user,id_annonce;
    Context contexteActuel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce);
        contexteActuel = this;
        ((ImageButton)findViewById(R.id.btnimg_signaler)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.getInstance().reportAnnonce(id_user,id_annonce,"");
                Toast.makeText(getApplicationContext(), "L'annonce à été signalée", Toast.LENGTH_SHORT);
            }
        });

        ((ImageButton)findViewById(R.id.btnimg_supprimer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Database.getInstance().supprimerAnnonce(id_annonce)){
                    Toast.makeText(getApplicationContext(),"L'annonce à été supprimée",Toast.LENGTH_SHORT);
                    Intent iMenu = new Intent().setClass(contexteActuel, Menu.class);
                    iMenu.putExtra("id_user", id_user);
                    startActivity(iMenu);
                }else{
                    Toast.makeText(getApplicationContext(),"Erreur : l'annonce n'as pas pu être supprimer",Toast.LENGTH_SHORT);
                    Intent iMenu = new Intent().setClass(contexteActuel, Menu.class);
                    iMenu.putExtra("id_user", id_user);
                    startActivity(iMenu);
                }
            }
        });
    }
}