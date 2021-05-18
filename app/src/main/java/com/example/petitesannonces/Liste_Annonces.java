package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class Liste_Annonces extends AppCompatActivity {
    int id_user;
    Context contexteActuel;
    List<Annonce_class> listeAnnonces = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste__annonces);

        ((ImageButton)findViewById(R.id.btnimg_rechercher)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recherche = ((EditText)findViewById(R.id.et_recherche)).getText().toString().trim();
                if(!recherche.equals("") ){
                    listeAnnonces = Database.getInstance().rechercheAnnonces(recherche,"");
                    actualiserAnnonces(listeAnnonces);
                }
            }
        });
    }

    /**actualise la liste des annonces**/
    private void actualiserAnnonces(List<Annonce_class> listeAnnonces){

    }
}