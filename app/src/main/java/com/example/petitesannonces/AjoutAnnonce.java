package com.example.petitesannonces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AjoutAnnonce extends AppCompatActivity {
    Context contexteActuel;
    int id_user;
    private static int RESULT_LOAD_IMAGE = 1;
    Spinner spinnerLocalisation, spinnerCategorie;
    ImageView image_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_annonce);
        contexteActuel = this;
        id_user = getIntent().getIntExtra("id_user",-1);
        spinnerLocalisation = (Spinner)findViewById(R.id.spinner_liste_annonce);
        spinnerCategorie = (Spinner)findViewById(R.id.spinnerCategorie);

        /////////////////
        /// Ajout comportement des boutons
        ////////////////
        ((ImageButton)findViewById(R.id.imgBtn_pickImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);


            }

        });
        image_view = findViewById(R.id.img_annonce);
        ((Button)findViewById(R.id.btn_message)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    String localisation = spinnerLocalisation.getSelectedItem().toString();
                    String categorie = spinnerCategorie.getSelectedItem().toString();

                    String nomPublication = ((EditText) findViewById(R.id.et_nompublication)).getText().toString().trim();
                    String prix = ((EditText) findViewById(R.id.et_prix)).getText().toString().trim();
                    String description = ((EditText) findViewById(R.id.et_description)).getText().toString().trim();
                    boolean valide = true;
                    if (nomPublication.equals("")) {
                        ((EditText) findViewById(R.id.et_nompublication)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                        valide = false;
                    }else{
                        ((EditText) findViewById(R.id.et_nompublication)).setBackgroundColor(getResources().getColor(R.color.white));

                    }
                    if (prix.equals("")) {
                        ((EditText) findViewById(R.id.et_prix)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                        valide = false;
                    }else{
                        ((EditText) findViewById(R.id.et_prix)).setBackgroundColor(getResources().getColor(R.color.white));
                    }

                    if (description.equals("")) {
                        ((EditText) findViewById(R.id.et_description)).setBackgroundColor(getResources().getColor(R.color.redTransparency));
                        valide = false;
                    }else{
                        ((EditText) findViewById(R.id.et_description)).setBackgroundColor(getResources().getColor(R.color.white));
                    }

                    if(valide){
                        if(Database.getInstance().isConnected()) {
                            if (Database.getInstance().ajoutAnnonce(id_user, nomPublication, Double.parseDouble(prix), description, ImageProcessing.drawableToByteArray(image_view.getDrawable()), localisation, categorie)) {
                                Intent iMenu = new Intent().setClass(contexteActuel, Menu.class);
                                iMenu.putExtra("id_user", id_user);
                                (Toast.makeText(getApplicationContext(), "Votre annonce ?? ??t?? publi??e", Toast.LENGTH_LONG)).show();
                                startActivity(iMenu);
                            }else{
                                Toast.makeText(getApplicationContext(),"Votre annonce n'as pas pu ??tre publi??e",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Erreur de connexion avec la base de donn??e",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        (Toast.makeText(getApplicationContext(),"Certains champs de sont pas valides",Toast.LENGTH_SHORT)).show();
                    }
                }

        });
   }


    @Override
    /** Comportement apr??s la selection ou non d'une image dans la galerie**/
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                image_view.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Un erreur est survenue", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getApplicationContext(),"Aucune image n'as ??t?? selectionn??e",Toast.LENGTH_LONG).show();
        }
    }
}