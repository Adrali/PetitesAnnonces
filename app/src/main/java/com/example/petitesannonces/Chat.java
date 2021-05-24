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
        //dest = Database.getInstance().getUser(id_dest);
        //lMessages = Database.getInstance().obtenirMessage(id_user,id_dest);
        dest = new UserModel(2,"Bob","561468464","dfgqgqfggqg");
        lMessages = new ArrayList<MessageModel>();
        lMessages.add(new MessageModel("Bonjour",true));
        lMessages.add(new MessageModel("Bonjour",false));
        lMessages.add(new MessageModel("Votre bien m'interesse",true));
        lMessages.add(new MessageModel("Puis-je négocier le prix ?",true));
        lMessages.add(new MessageModel("Combiens me proposez vous ?",false));
        lMessages.add(new MessageModel("20€",true));
        lMessages.add(new MessageModel("ça marche, on se donne rendez vous ou ?",false));
        recyclerView = findViewById(R.id.recyclerViewChat);
        recyclerView.setAdapter(new ItemMessageAdapter(lMessages,dest.getName(),R.layout.item_mini_chat));

        ((ImageButton)findViewById(R.id.imgBtn_actualiser)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*dest = Database.getInstance().getUser(id_dest);
                lMessages = Database.getInstance().obtenirMessage(id_user,id_dest);*/

            }

        });
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