package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contexteActuel = this;
        isProfessional = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        ((EditText)findViewById(R.id.et_companyname)).setVisibility(View.GONE);


        ((CheckBox)(findViewById(R.id.cb_isPro))).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        ((EditText)findViewById(R.id.et_firstname)).setVisibility(View.GONE);
                        ((EditText)findViewById(R.id.et_name)).setVisibility(View.GONE);
                        ((EditText)findViewById(R.id.et_companyname)).setVisibility(View.VISIBLE);
                        isProfessional = true;
                    }else{
                        ((EditText)findViewById(R.id.et_firstname)).setVisibility(View.VISIBLE);
                        ((EditText)findViewById(R.id.et_name)).setVisibility(View.VISIBLE);
                        ((EditText)findViewById(R.id.et_companyname)).setVisibility(View.GONE);
                        isProfessional = false;
                    }
               }
        });
        ((Button)findViewById(R.id.bt_valider_inscription)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valide = true;
                Pattern pattern = Pattern.compile("^((\\w[^\\W]+)[\\.\\-]?){1,}\\@(([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
                Matcher matcher = pattern.matcher(((EditText)findViewById(R.id.et_mail)).getText().toString());
                if(((EditText)findViewById(R.id.et_username)).getText().toString().trim().equals("") ){
                    ((EditText)findViewById(R.id.et_username)).setBackgroundColor(getResources().getColor(R.color.redTransparency));

                    valide = false;
                }else{
                    ((EditText)findViewById(R.id.et_username)).setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(((EditText)findViewById(R.id.et_firstname)).getText().toString().trim().equals("")  && !isProfessional){
                    ((EditText)findViewById(R.id.et_firstname)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else if(!isProfessional){
                    ((EditText)findViewById(R.id.et_firstname)).setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(((EditText)findViewById(R.id.et_name)).getText().toString().trim().equals("")  && !isProfessional){
                    ((EditText)findViewById(R.id.et_name)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else if(!isProfessional){
                    ((EditText)findViewById(R.id.et_name)).setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(((EditText)findViewById(R.id.et_companyname)).getText().toString().trim().equals("")  && isProfessional){
                    ((EditText)findViewById(R.id.et_companyname)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else if (isProfessional){
                    ((EditText)findViewById(R.id.et_companyname)).setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(!matcher.find()){
                    ((EditText)findViewById(R.id.et_mail)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else{
                    ((EditText)findViewById(R.id.et_mail)).setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(((EditText)findViewById(R.id.et_phone)).getText().toString().trim().equals("") ){
                    ((EditText)findViewById(R.id.et_phone)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else{
                    ((EditText)findViewById(R.id.et_phone)).setBackgroundColor(getResources().getColor(R.color.white));

                }
                if(((EditText)findViewById(R.id.et_password)).getText().toString().trim().equals("") ){
                    ((EditText)findViewById(R.id.et_password)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                    valide = false;
                }else{
                    ((EditText)findViewById(R.id.et_password)).setBackgroundColor(getResources().getColor(R.color.white));
                }


                if(valide) {
                    Intent iMenu = new Intent().setClass(contexteActuel, Menu.class);
                    startActivity(iMenu);
                }else{
                    (Toast.makeText(getApplicationContext(),"Certains champs de sont pas valides",Toast.LENGTH_SHORT)).show();
                }
            }
        });
    }
}