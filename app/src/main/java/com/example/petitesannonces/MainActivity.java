package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Context contexteActuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contexteActuel = this;
        ((Button)findViewById(R.id.button_inscription)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                    Intent iFormulaire = new Intent().setClass(contexteActuel,Inscription.class);
                    startActivity(iFormulaire);
                }
        });

        ((Button)findViewById(R.id.button_connexion)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                boolean valide = true;
                if (((EditText) findViewById(R.id.et_main_utilisateur)).getText().toString().trim().equals("")) {
                    ((EditText) findViewById(R.id.et_main_utilisateur)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else{
                    ((EditText) findViewById(R.id.et_main_utilisateur)).setBackgroundColor(getResources().getColor(R.color.white));

                }
                if (((EditText) findViewById(R.id.et_main_password)).getText().toString().trim().equals("")) {
                    ((EditText) findViewById(R.id.et_main_password)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else{
                    ((EditText) findViewById(R.id.et_main_password)).setBackgroundColor(getResources().getColor(R.color.white));
                }

                if(valide){
                    Intent iMenu = new Intent().setClass(contexteActuel, Menu.class);
                    startActivity(iMenu);
                }else{
                    Toast.makeText(getApplicationContext(),"Certains champs de sont pas valides",Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}