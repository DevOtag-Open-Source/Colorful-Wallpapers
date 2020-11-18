package com.otag.colorfulwallpapers.viewHolder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.otag.colorfulwallpapers.Interface.ItemOnClickListener;
import com.otag.colorfulwallpapers.R;

import dmax.dialog.SpotsDialog;

public class PicturesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView image;
    SpotsDialog alertDialog;
    private ItemOnClickListener listener;

    public void setListener(ItemOnClickListener listener){
        this.listener = listener;

    }
    public PicturesViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition());
    }
}
