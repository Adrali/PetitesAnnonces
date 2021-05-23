package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Spinner;

import java.util.List;

public class Liste_Mes_Annonces extends AppCompatActivity {

    int id_user;
    Context contexteActuel;
    List<AnnonceModel> listeAnnonces = null;
    RecyclerView recyclerView;
    Spinner spinnerLocalisation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste__annonces);
        recyclerView = findViewById(R.id.RecyclerViewListeMesAnnonces);
        listeAnnonces = Database.getInstance().rechercheMesAnnonces(id_user);
        recyclerView.setAdapter(new ItemAnnonceAdapter(listeAnnonces,R.layout.item_mini_annonce));
    }
}