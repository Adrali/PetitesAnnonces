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

public class Liste_Annonces extends AppCompatActivity {
    int id_user;
    Context contexteActuel;
    List<AnnonceModel> listeAnnonces = null;
    RecyclerView recyclerView;
    Spinner spinnerLocalisation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste__annonces);
        recyclerView = findViewById(R.id.RecyclerViewListe);
        spinnerLocalisation = (Spinner) findViewById(R.id.spinner_liste_annonce);
        ((ImageButton)findViewById(R.id.btnimg_rechercher)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recherche = ((EditText)findViewById(R.id.et_recherche)).getText().toString().trim();
                String localisation = spinnerLocalisation.getSelectedItem().toString();
                if(!recherche.equals("") ){
                    listeAnnonces = Database.getInstance().rechercheAnnonces(recherche,localisation);
                    recyclerView.setAdapter(new ItemAnnonceAdapter(listeAnnonces,R.layout.item_mini_annonce));
                }
            }
        });
    }

    /**actualise la liste des annonces**/

}