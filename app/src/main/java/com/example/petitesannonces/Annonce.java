package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Annonce extends AppCompatActivity {
    int id_user,id_annonce,id_annonceur;
    boolean estFavoris = false;
    UserModel annonceur;
    AnnonceModel annonce;
    Context contexteActuel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce);
        contexteActuel = this;
        id_user = getIntent().getIntExtra("id_user",-1);
        id_annonceur = getIntent().getIntExtra("id_annonceur",-2);
        id_annonceur = getIntent().getIntExtra("id_annonce",-3);
        annonceur = Database.getInstance().getUser(id_annonceur);
        annonce = Database.getInstance().rechercheAnnonce(id_annonce);


        if(id_annonce < 0 ){
            Toast.makeText(getApplicationContext(), "Erreur, l'annonce n'a pas pû être trouvé", Toast.LENGTH_SHORT);
            Intent iMenu = new Intent().setClass(contexteActuel, Menu.class);
            iMenu.putExtra("id_user", id_user);
            startActivity(iMenu);
        }

        if (id_user == id_annonceur){
            // Si on est le proprio de l'annonce
            ((ImageButton)findViewById(R.id.btnimg_signaler)).setVisibility(View.INVISIBLE);
            ((ImageButton)findViewById(R.id.btnimg_fav)).setVisibility(View.INVISIBLE);
            ((ImageButton)findViewById(R.id.btnimg_supprimer)).setVisibility(View.VISIBLE);
            ((Button)findViewById(R.id.btn_appeler)).setVisibility(View.INVISIBLE);
            ((Button)findViewById(R.id.btn_message)).setVisibility(View.INVISIBLE);




        }else{
            // Si l'annonce appartiens a quelqu'un d'autre
            ((ImageButton)findViewById(R.id.btnimg_signaler)).setVisibility(View.VISIBLE);
            ((ImageButton)findViewById(R.id.btnimg_fav)).setVisibility(View.VISIBLE);
            ((ImageButton)findViewById(R.id.btnimg_supprimer)).setVisibility(View.INVISIBLE);
            ((Button)findViewById(R.id.btn_appeler)).setVisibility(View.VISIBLE);
            ((Button)findViewById(R.id.btn_message)).setVisibility(View.VISIBLE);
            if(Database.getInstance().estFavoris(id_user,id_annonce)) {
                estFavoris = true;
            }
        }


        ((ImageButton)findViewById(R.id.btnimg_signaler)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.getInstance().reportAnnonce(id_user,id_annonce,"");
                Toast.makeText(getApplicationContext(), "L'annonce à été signalée", Toast.LENGTH_SHORT);
            }
        });

        ((ImageButton)findViewById(R.id.btnimg_fav)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(estFavoris){
                    if(Database.getInstance().ajoutFavoris(id_user,id_annonceur)){
                        //L'annonce est bien passée en favoris
                        Toast.makeText(getApplicationContext(), "L'annonce à été sauvegardée", Toast.LENGTH_SHORT);
                        estFavoris = true;
                    }else{
                        Toast.makeText(getApplicationContext(), "Erreur sauvegarde de l'annonce", Toast.LENGTH_SHORT);

                    }
                }else {
                    if (Database.getInstance().retirerFavoris(id_user, id_annonceur)) {
                        //L'annonce est bien passée en favoris
                        Toast.makeText(getApplicationContext(), "L'annonce à été rétiré de vos sauvegardes", Toast.LENGTH_SHORT);
                        estFavoris = false;
                    } else {
                        Toast.makeText(getApplicationContext(), "Erreur retrait de vos sauvegardes", Toast.LENGTH_SHORT);

                    }
                }

            }
        });


        ((ImageButton)findViewById(R.id.btnimg_supprimer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Database.getInstance().supprimerAnnonce(id_annonce)){
                    Toast.makeText(getApplicationContext(),"L'annonce à été supprimée",Toast.LENGTH_SHORT);
                    Intent iMenu = new Intent().setClass(contexteActuel, Menu.class);
                    iMenu.putExtra("id_user", id_user);
                    startActivity(iMenu);
                }else{
                    Toast.makeText(getApplicationContext(),"Erreur : l'annonce n'a pas pu être supprimer",Toast.LENGTH_SHORT);

                }
            }
        });

        ((Button)findViewById(R.id.btn_appeler)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+annonceur.getPhone()));//change the number
                startActivity(callIntent);
            }
        });

        ((Button)findViewById(R.id.btn_message)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Database.getInstance().envoyerMessage(id_user,id_annonceur,"Bonjour, je suis interessé par : " + annonce.getNom_annonce())){
                    Intent iChat = new Intent(contexteActuel,Chat.class);
                    iChat.putExtra("id_user",id_user);
                    iChat.putExtra("id_dest",id_annonceur);
                    startActivity(iChat);
                }else{
                    Toast.makeText(getApplicationContext(),"Erreur de contact avec l'annonceur",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}