package com.example.petitesannonces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemMessageAdapter extends RecyclerView.Adapter<ItemMessageAdapter.ItemMessageViewHolder>{
    List<MessageModel> messages;
    int layoutId;
    String nomInterlocuteur;
    final int NB_ITEM_MAX = 40;
    public ItemMessageAdapter(List<MessageModel> messages, String nomInterlocuteur,int layoutId){
        this.messages = messages;
        this.layoutId = layoutId;
        this.nomInterlocuteur = nomInterlocuteur;
    }
    public class ItemMessageViewHolder extends RecyclerView.ViewHolder {

        public TextView nom;
        public TextView message;
        public ItemMessageViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.tv_emetteur);
            message = itemView.findViewById(R.id.tv_message);
        }

    }

    @Override
    public ItemMessageAdapter.ItemMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);

        return new ItemMessageAdapter.ItemMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemMessageAdapter.ItemMessageViewHolder viewHolder, int position) {
        MessageModel current = messages.get(position);
        viewHolder.nom.setText(current.isFromYou()?"Vous":nomInterlocuteur);
        viewHolder.message.setText(current.getMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size()>NB_ITEM_MAX?NB_ITEM_MAX:messages.size();
    }

    public MessageModel getAnnonce(int position){
        return this.messages.get(position);
    }
}
