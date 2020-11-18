package com.otag.colorfulwallpapers.adabter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.otag.colorfulwallpapers.Interface.ItemOnClickListener;
import com.otag.colorfulwallpapers.R;
import com.otag.colorfulwallpapers.ViewWalpaper;
import com.otag.colorfulwallpapers.common.Common;
import com.otag.colorfulwallpapers.viewHolder.FavoriteViewHolder;

import java.util.List;

public class  FavoriteAdabter extends RecyclerView.Adapter<FavoriteViewHolder> {
    private Context context;
    private List<String> listFavorite;

    public FavoriteAdabter(Context context, List<String> listFavorite) {
        this.context = context;
        this.listFavorite = listFavorite;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite,parent,false);

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Glide.with(context)
                .load(listFavorite.get(position))
                .placeholder(R.drawable.landscape)
                .into(holder.img_favorite);
        holder.setListener(new ItemOnClickListener() {
            @Override
            public void onClick(View v, int pos) {

                Common.LİST_PİCTURES = listFavorite  ;
                Intent intent = new Intent(context, ViewWalpaper.class);
                intent.putExtra("current_img",pos);
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return listFavorite.size();
    }
}
