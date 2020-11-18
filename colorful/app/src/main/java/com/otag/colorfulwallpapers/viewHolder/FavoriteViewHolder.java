package com.otag.colorfulwallpapers.viewHolder;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.otag.colorfulwallpapers.Interface.ItemOnClickListener;
import com.otag.colorfulwallpapers.R;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {
    public ImageView img_favorite;
    private ItemOnClickListener listener;

    public void setListener(ItemOnClickListener listener) {
        this.listener = listener;
    }

    public FavoriteViewHolder(@NonNull View itemView) {
        super(itemView);

        img_favorite = itemView.findViewById(R.id.img_favorite);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v,getAdapterPosition());

            }
        });
    }
}
