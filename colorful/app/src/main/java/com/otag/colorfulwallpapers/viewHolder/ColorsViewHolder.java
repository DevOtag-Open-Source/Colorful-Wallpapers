package com.otag.colorfulwallpapers.viewHolder;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.otag.colorfulwallpapers.Interface.ItemOnClickListener;
import com.otag.colorfulwallpapers.R;

public class ColorsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public Button itemColor;
    private ItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public ColorsViewHolder(@NonNull View itemView) {
        super(itemView);

        itemColor = itemView.findViewById(R.id.item_color);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemOnClickListener.onClick(v,getAdapterPosition());
    }
}