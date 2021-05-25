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
    Spinner spinnerLocalisation, spinnerCategorie;
    ItemAnnonceAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste__annonces);

        /////////////////
        /// Association des vars aux view
        ////////////////
        id_user = getIntent().getIntExtra("id_user",-1);

        contexteActuel = this;
        this.configureOnClickRecyclerView();

        recyclerView = findViewById(R.id.RecyclerViewListe);
        spinnerLocalisation = (Spinner)findViewById(R.id.spinner_liste_annonce);
        spinnerCategorie = (Spinner)findViewById(R.id.spinnerCategorieRecherche);


        /////////////////
        /// Ajout comportement du boutons
        ////////////////
        ((ImageButton)findViewById(R.id.btnimg_rechercher)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On récup les infos des inputs
                String recherche = ((EditText)findViewById(R.id.et_recherche)).getText().toString().trim();
                String localisation = spinnerLocalisation.getSelectedItem().toString();
                String categorie = spinnerCategorie.getSelectedItem().toString();
                //On cherche dans la base de donnée puis on affiche
                if(!recherche.equals("") ){
                    listeAnnonces = Database.getInstance().rechercheAnnonces(recherche,localisation,categorie);
                    recyclerView.setAdapter(adapter = new ItemAnnonceAdapter(listeAnnonces,R.layout.item_mini_annonce));
                }
            }
        });
    }
    /**Ajoute le comportement cliquable au recycler view**/
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