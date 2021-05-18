package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Chat extends AppCompatActivity {
    int id_user;
    int id_dest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ((ImageButton)findViewById(R.id.btn_envoi_message)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String message = ((EditText)findViewById(R.id.et_envoi_message)).getText().toString().trim();
                if(!message.equals("") ){
                    if(Database.getInstance().envoyerMessage(id_user,id_dest,message))
                        ((EditText)findViewById(R.id.et_envoi_message)).setText("");
                }

            }

        });
    }


}