package com.example.petitesannonces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class ItemAnnonceAdapter extends RecyclerView.Adapter<ItemAnnonceAdapter.ItemAnnonceViewHolder> {
    List<AnnonceModel> annonces;
    int layoutId;
    public ItemAnnonceAdapter(List<AnnonceModel> annonces, int layoutId){
        this.annonces = annonces;
        this.layoutId = layoutId;
    }
    public class ItemAnnonceViewHolder extends RecyclerView.ViewHolder {

        TextView nom;
        TextView prix;
        public ItemAnnonceViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.nom_item_annonce);
            prix = itemView.findViewById(R.id.prix_item_annonce);
        }
    }

    @Override
    public ItemAnnonceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);

        return new ItemAnnonceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAnnonceViewHolder viewHolder, int position) {
        AnnonceModel current = annonces.get(position);
        viewHolder.nom.setText(current.nom_annonce);
        viewHolder.prix.setText(String.valueOf(current.prix));
    }

    @Override
    public int getItemCount() {
        return annonces.size()>20?20:annonces.size();
    }

}
