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
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AjoutAnnonce extends AppCompatActivity {
    Context contexteActuel;
    int id_user;
    private static int RESULT_LOAD_IMAGE = 1;
    ImageView image_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_annonce);
        contexteActuel = this;

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
                        if(Database.getInstance().ajoutAnnonce(id_user,nomPublication,Double.parseDouble(prix),description,ImageProcessing.drawableToByteArray(image_view.getDrawable()),"")){
                            Intent iMenu = new Intent().setClass(contexteActuel, Menu.class);
                            iMenu.putExtra("id_user",id_user);
                            (Toast.makeText(getApplicationContext(),"Votre annonce à été publiée",Toast.LENGTH_LONG)).show();
                            startActivity(iMenu);
                        }

                    }else{
                        (Toast.makeText(getApplicationContext(),"Certains champs de sont pas valides",Toast.LENGTH_SHORT)).show();
                    }
                }

        });
   }
    @Override
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
                Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getApplicationContext(),"You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}