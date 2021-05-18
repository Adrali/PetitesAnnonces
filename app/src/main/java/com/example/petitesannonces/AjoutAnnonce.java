package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AjoutAnnonce extends AppCompatActivity {
    Context contexteActuel;
    int id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_annonce);
        contexteActuel = this;


        ((Button)findViewById(R.id.btn_message)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    String nomPublication = ((EditText) findViewById(R.id.et_nompublication)).getText().toString().trim();
                    String prix = ((EditText) findViewById(R.id.et_prix)).getText().toString().trim();
                    String description = ((EditText) findViewById(R.id.et_description)).getText().toString().trim();
                    boolean valide = true;
                    if (nomPublication.equals("")) {
                        ((EditText) findViewById(R.id.et_nompublication)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                        valide = false;
                    }else{
                        ((EditText) findViewById(R.id.et_nompublication)).setBackgroundColor(getResources().getColor(R.color.white));

                    }
                    if (prix.equals("")) {
                        ((EditText) findViewById(R.id.et_prix)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                        valide = false;
                    }else{
                        ((EditText) findViewById(R.id.et_prix)).setBackgroundColor(getResources().getColor(R.color.white));
                    }

                    if (description.equals("")) {
                        ((EditText) findViewById(R.id.et_description)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                        valide = false;
                    }else{
                        ((EditText) findViewById(R.id.et_description)).setBackgroundColor(getResources().getColor(R.color.white));
                    }

                    if(valide){
                        if(Database.getInstance().ajoutAnnonce(id_user,nomPublication,Double.parseDouble(prix),description,"00011111".getBytes(),"")){
                            Intent iMenu = new Intent().setClass(contexteActuel, Menu.class);
                            iMenu.putExtra("id_user",id_user);
                            (Toast.makeText(getApplicationContext(),"Votre annonce à été publiée",Toast.LENGTH_LONG)).show();
                            startActivity(iMenu);
                        }

                    }else{
                        (Toast.makeText(getApplicationContext(),"Certains champs de sont pas valides",Toast.LENGTH_SHORT)).show();
                    }
                }

        });

    }
}