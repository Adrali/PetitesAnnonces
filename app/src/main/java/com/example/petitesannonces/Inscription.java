package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.*;

public class Inscription extends AppCompatActivity {
    Context contexteActuel;
    boolean isProfessional;
    Database db = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contexteActuel = this;
        isProfessional = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        ((EditText)findViewById(R.id.et_companyname)).setVisibility(View.GONE);
        ((EditText)findViewById(R.id.et_website)).setVisibility(View.GONE);


        ((CheckBox)(findViewById(R.id.cb_isPro))).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        ((EditText)findViewById(R.id.et_firstname)).setVisibility(View.GONE);
                        ((EditText)findViewById(R.id.et_name)).setVisibility(View.GONE);
                        ((EditText)findViewById(R.id.et_companyname)).setVisibility(View.VISIBLE);
                        ((EditText)findViewById(R.id.et_website)).setVisibility(View.VISIBLE);

                        isProfessional = true;
                    }else{
                        ((EditText)findViewById(R.id.et_firstname)).setVisibility(View.VISIBLE);
                        ((EditText)findViewById(R.id.et_name)).setVisibility(View.VISIBLE);
                        ((EditText)findViewById(R.id.et_companyname)).setVisibility(View.GONE);
                        ((EditText)findViewById(R.id.et_website)).setVisibility(View.GONE);
                        isProfessional = false;
                    }
               }
        });
        ((Button)findViewById(R.id.bt_valider_inscription)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valide = true;
                String username=((EditText)findViewById(R.id.et_username)).getText().toString().trim();
                String password=((EditText)findViewById(R.id.et_password)).getText().toString().trim();
                String firstname=((EditText)findViewById(R.id.et_firstname)).getText().toString().trim();
                String lastname=((EditText)findViewById(R.id.et_name)).getText().toString().trim();
                String companyname=((EditText)findViewById(R.id.et_companyname)).getText().toString().trim();
                String phone=((EditText)findViewById(R.id.et_phone)).getText().toString().trim();
                String email=((EditText)findViewById(R.id.et_mail)).getText().toString().trim();
                String website = ((EditText)findViewById(R.id.et_website)).getText().toString().trim();
                Pattern pattern = Pattern.compile("^((\\w[^\\W]+)[\\.\\-]?){1,}\\@(([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
                Matcher matcher = pattern.matcher(((EditText)findViewById(R.id.et_mail)).getText().toString());

                ///////////////
                ///// Verification de champs de texte
                //////////////

                if(username.equals("") ){
                    ((EditText)findViewById(R.id.et_username)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else{
                    ((EditText)findViewById(R.id.et_username)).setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(firstname.equals("")  && !isProfessional){
                    ((EditText)findViewById(R.id.et_firstname)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else if(!isProfessional){
                    ((EditText)findViewById(R.id.et_firstname)).setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(lastname.equals("")  && !isProfessional){
                    ((EditText)findViewById(R.id.et_name)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else if(!isProfessional){
                    ((EditText)findViewById(R.id.et_name)).setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(companyname.equals("")  && isProfessional){
                    ((EditText)findViewById(R.id.et_companyname)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else if (isProfessional){
                    ((EditText)findViewById(R.id.et_companyname)).setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(website.equals("")  && isProfessional){
                    ((EditText)findViewById(R.id.et_website)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else if (isProfessional){
                    ((EditText)findViewById(R.id.et_website)).setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(!matcher.find()){
                    ((EditText)findViewById(R.id.et_mail)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else{
                    ((EditText)findViewById(R.id.et_mail)).setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(phone.equals("") ){
                    ((EditText)findViewById(R.id.et_phone)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else{
                    ((EditText)findViewById(R.id.et_phone)).setBackgroundColor(getResources().getColor(R.color.white));

                }
                if(password.equals("") ){
                    ((EditText)findViewById(R.id.et_password)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else{
                    ((EditText)findViewById(R.id.et_password)).setBackgroundColor(getResources().getColor(R.color.white));
                }

                /////////
                //// On essaye d'ajouter a la bdd puis de connecter
                /////////

                if(valide) {
                    if(db.isConnected()) {
                        if (Database.getInstance().userExist(username)) {
                            if (isProfessional)
                                Database.getInstance().signInUser(username, password, firstname, lastname, phone, email);
                            else
                                Database.getInstance().signInPro(username, password, website, companyname, phone, email);
                            int id = Database.getInstance().connectUser(username, password);
                            Toast.makeText(getApplicationContext(),"Vous avez correctement été inscrit",Toast.LENGTH_LONG).show();

                            if (id >= 0) {
                                Intent iMenu = new Intent().setClass(contexteActuel, Menu.class);
                                iMenu.putExtra("id_user", id);
                                startActivity(iMenu);
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Le nom de compte existe déjà",Toast.LENGTH_SHORT).show();

                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Erreur de connexion avec la base de donnée",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Certains champs de sont pas valides",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}