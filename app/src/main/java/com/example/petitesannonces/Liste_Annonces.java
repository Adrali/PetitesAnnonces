package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Liste_Annonces extends AppCompatActivity {
    int id_user;
    Context contexteActuel;
    List<AnnonceModel> listeAnnonces = null;
    RecyclerView recyclerView;
    Spinner spinnerLocalisation;
    ItemAnnonceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste__annonces);
        id_user = getIntent().getIntExtra("id_user",-1);
        contexteActuel = this;

        recyclerView = findViewById(R.id.RecyclerViewListe);
        this.configureOnClickRecyclerView();
        spinnerLocalisation = (Spinner)findViewById(R.id.spinner_liste_annonce);
        ((ImageButton)findViewById(R.id.btnimg_rechercher)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recherche = ((EditText)findViewById(R.id.et_recherche)).getText().toString().trim();
                String localisation = spinnerLocalisation.getSelectedItem().toString();
                if(!recherche.equals("") ){
                    //listeAnnonces = Database.getInstance().rechercheAnnonces(recherche,localisation);
                    listeAnnonces = new ArrayList<AnnonceModel>();
                    listeAnnonces.add(new AnnonceModel(1,1,"Je sens bon",10.15,null,"Produit 1"));
                    listeAnnonces.add(new AnnonceModel(2,1,"Je suis grand",27.15,null,"Produit 2"));
                    listeAnnonces.add(new AnnonceModel(3,2,"Je vais vite",0.15,null,"Produit 3"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 4"));
                    listeAnnonces.add(new AnnonceModel(1,1,"Je sens bon",10.15,null,"Produit 5"));
                    listeAnnonces.add(new AnnonceModel(2,1,"Je suis grand",27.15,null,"Produit 6"));
                    listeAnnonces.add(new AnnonceModel(3,2,"Je vais vite",0.15,null,"Produit 7"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 8"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 9"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 10"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 11"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 12"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 13"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 14"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 15"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 16"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 17"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 18"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 19"));
                    listeAnnonces.add(new AnnonceModel(4,2,"Je suis capable de dancer",1000.00,null,"Produit 20"));
                    recyclerView.setAdapter(adapter = new ItemAnnonceAdapter(listeAnnonces,R.layout.item_mini_annonce));
                }
            }
        });
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.activity_liste__annonces)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Click sur : "+ adapter.getAnnonce(position).nom_annonce);
                        Intent iAnnonce = new Intent(contexteActuel,Annonce.class);
                        iAnnonce.putExtra("id_user",id_user);
                        iAnnonce.putExtra("id_annonceur",adapter.getAnnonce(position).id_proprietaire);
                        iAnnonce.putExtra("id_annonce",adapter.getAnnonce(position).id_annonce);
                        startActivity(iAnnonce);
                    }
                });
    }
}