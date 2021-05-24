package com.example.petitesannonces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemListChatAdapter extends RecyclerView.Adapter<ItemListChatAdapter.ItemListChatViewHolder>{

    List<UserModel> users;
    int layoutId;
    final int NB_ITEM_MAX = 40;
    public ItemListChatAdapter(List<UserModel> users, int layoutId){
        this.users = users;
        this.layoutId = layoutId;
    }
    public class ItemListChatViewHolder extends RecyclerView.ViewHolder {

        public TextView nom;
        public ItemListChatViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.tv_nomCorrespondant);
        }

    }

    @Override
    public ItemListChatAdapter.ItemListChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);

        return new ItemListChatAdapter.ItemListChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemListChatAdapter.ItemListChatViewHolder viewHolder, int position) {
        UserModel current = users.get(position);
        viewHolder.nom.setText(current.name);
    }

    @Override
    public int getItemCount() {
        return users.size()>NB_ITEM_MAX?NB_ITEM_MAX:users.size();
    }

    public UserModel getUser(int position){
        return this.users.get(position);
    }
}
