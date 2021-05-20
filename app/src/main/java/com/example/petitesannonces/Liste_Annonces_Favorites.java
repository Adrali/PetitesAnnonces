package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.List;

public class Liste_Annonces_Favorites extends AppCompatActivity {

    int id_user;
    Context contexteActuel;
    List<AnnonceModel> listeAnnonces = null;
    RecyclerView recyclerView;
    Spinner spinnerLocalisation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste__annonces);
        recyclerView = findViewById(R.id.RecyclerViewListeFavoris);
        listeAnnonces = Database.getInstance().rechercheAnnoncesFavori(id_user);
        recyclerView.setAdapter(new ItemAnnonceAdapter(listeAnnonces,R.layout.item_mini_annonce));
    }

}