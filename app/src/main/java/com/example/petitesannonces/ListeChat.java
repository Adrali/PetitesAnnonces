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
        //List<UserModel> listeUserChat = Database.getInstance().obtenirInterlocuteurs(id_user);
        List<UserModel> listeUserChat = new ArrayList<UserModel>();
        listeUserChat.add(new UserModel(1,"Toto","000000012","toto@gmail"));
        listeUserChat.add(new UserModel(2,"Tata","000000012","toto@gmail"));
        listeUserChat.add(new UserModel(3,"Titi","000000012","toto@gmail"));
        listeUserChat.add(new UserModel(4,"Tutu","000000012","toto@gmail"));
        listeUserChat.add(new UserModel(5,"Lolo","000000012","toto@gmail"));
        listeUserChat.add(new UserModel(6,"Lala","000000012","toto@gmail"));
        listeUserChat.add(new UserModel(7,"Lili","000000012","toto@gmail"));
        recyclerView.setAdapter(adapter = new ItemListChatAdapter(listeUserChat,R.layout.item_mini_annonce));
        contexteActuel = this;

    }
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.activity_liste__annonces)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Click sur : "+ adapter.getAnnonce(position).getName());
                        Intent iAnnonce = new Intent(contexteActuel,Annonce.class);
                        iAnnonce.putExtra("id_user",id_user);
                        iAnnonce.putExtra("id_dest",adapter.getAnnonce(position).getId_user());
                        startActivity(iAnnonce);
                    }
                });
    }
}