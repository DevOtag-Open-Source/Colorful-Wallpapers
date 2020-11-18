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
import com.otag.colorfulwallpapers.viewHolder.PicturesViewHolder;

import java.util.List;

public class PicturesAdabter extends RecyclerView.Adapter<PicturesViewHolder> {

    private Context context;
    private List<String> picLink;

    public PicturesAdabter(Context context, List<String> picLink) {
        this.context = context;
        this.picLink = picLink;
    }

    @NonNull
    @Override
    public PicturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_pictures,parent,false);
        return new PicturesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicturesViewHolder holder, int position) {
        Glide.with(context)
                .load(picLink.get(position))
                .centerCrop()
                .override(500,500)
                .placeholder(R.drawable.landscape)
                .into(holder.image);
        holder.setListener(new ItemOnClickListener() {
            @Override
            public void onClick(View v, int pos) {
                Intent intent = new Intent(context, ViewWalpaper.class);
                intent.putExtra("current_img",pos);
                Common.LİST_PİCTURES = picLink;
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return picLink.size();
    }
}
