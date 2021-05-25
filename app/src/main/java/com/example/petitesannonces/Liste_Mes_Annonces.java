package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import java.util.List;

public class Liste_Mes_Annonces extends AppCompatActivity {

    int id_user;
    Context contexteActuel;
    List<AnnonceModel> listeAnnonces = null;
    RecyclerView recyclerView;
    Spinner spinnerLocalisation;
    ItemAnnonceAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_mes_annonces);

        id_user = getIntent().getIntExtra("id_user",-1);
        contexteActuel = this;
        this.configureOnClickRecyclerView();
        recyclerView = findViewById(R.id.RecyclerViewListeMesAnnonces);

        //On cherche dans la base de donnée puis on affiche
        listeAnnonces = Database.getInstance().rechercheMesAnnonces(id_user);
        recyclerView.setAdapter(adapter = new ItemAnnonceAdapter(listeAnnonces,R.layout.item_mini_annonce));
    }



    /**Ajoute le comportement cliquable au recycler view**/
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.activity_liste_mes_annonces)
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