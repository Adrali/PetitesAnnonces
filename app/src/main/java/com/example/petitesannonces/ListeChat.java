package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ListeChat extends AppCompatActivity {
    int id_user;
    RecyclerView recyclerView;
    Context contexteActuel;
    ItemListChatAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_chat);
        id_user = getIntent().getIntExtra("id_user", -1);
        recyclerView = findViewById(R.id.recyclerViewListeChat);
        contexteActuel = this;
        this.configureOnClickRecyclerView();
        List<UserModel> listeUserChat = Database.getInstance().obtenirInterlocuteurs(id_user);
        recyclerView.setAdapter(adapter = new ItemListChatAdapter(listeUserChat,R.layout.item_list_chat));
    }

    /**Ajoute le comportement cliquable au recycler view**/
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.activity_liste_chat)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Click sur : "+ adapter.getUser(position).getName());
                        Intent iAnnonce = new Intent(contexteActuel,Chat.class);
                        iAnnonce.putExtra("id_user",id_user);
                        iAnnonce.putExtra("id_dest",adapter.getUser(position).getId_user());
                        startActivity(iAnnonce);
                    }
                });
    }
}