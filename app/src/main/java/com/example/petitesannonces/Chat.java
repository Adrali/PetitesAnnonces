package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {
    int id_user;
    int id_dest;
    RecyclerView recyclerView;
    List<MessageModel> lMessages;
    UserModel dest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        id_user = getIntent().getIntExtra("id_user",-1);
        id_dest = getIntent().getIntExtra("id_dest",-2);
        dest = Database.getInstance().getUser(id_dest);
        lMessages = Database.getInstance().obtenirMessage(id_user,id_dest);

        recyclerView = findViewById(R.id.recyclerViewChat);
        recyclerView.setAdapter(new ItemMessageAdapter(lMessages,dest.getName(),R.layout.item_mini_chat));


        /////////////////
        /// Ajout comportement des boutons
        ////////////////
        ((ImageButton)findViewById(R.id.imgBtn_actualiser)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                lMessages = Database.getInstance().obtenirMessage(id_user,id_dest);
                recyclerView.setAdapter(new ItemMessageAdapter(lMessages,dest.getName(),R.layout.item_mini_chat));
            }
        });

        ((ImageButton)findViewById(R.id.btn_envoi_message)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String message = ((EditText)findViewById(R.id.et_envoi_message)).getText().toString().trim();

                if(!message.equals("") ){
                    if(Database.getInstance().isConnected()){
                        if(Database.getInstance().envoyerMessage(id_user,id_dest,message)) {
                            ((EditText) findViewById(R.id.et_envoi_message)).setText("");
                        }else{
                            Toast.makeText(getApplicationContext(),"Erreur d'envoi de message",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Erreur connexion à la BDD",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ((ImageButton)findViewById(R.id.imgBtn_appeler)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+dest.getPhone()));//change the number
                startActivity(callIntent);
            }
        });
    }


}