package com.otag.colorfulwallpapers.adabter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.otag.colorfulwallpapers.Interface.ItemOnClickListener;
import com.otag.colorfulwallpapers.R;
import com.otag.colorfulwallpapers.model.Colors;
import com.otag.colorfulwallpapers.viewHolder.ColorsViewHolder;

import java.util.List;

public class ColorsAdabter extends RecyclerView.Adapter<ColorsViewHolder> {

    private Context mContext;
    private List<Colors> colorsList;
    private ColorsAdabterListern listern;


    public ColorsAdabter(Context mContext, List<Colors> colorsList, ColorsAdabterListern listern) {
        this.mContext = mContext;
        this.colorsList = colorsList;
        this.listern = listern;
    }

    @NonNull
    @Override
    public ColorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_colors,parent,false);

        return new ColorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorsViewHolder holder, int position) {

        if (colorsList.get(position).getName().equals("random")){

            int sdk = Build.VERSION.SDK_INT;

            if (sdk< Build.VERSION_CODES.JELLY_BEAN){
                holder.itemColor.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.random_color));
            }else {
                holder.itemColor.setBackground(ContextCompat.getDrawable(mContext,R.drawable.random_color));
            }
        }else{
            RoundRectShape roundRectShape = new RoundRectShape(new float[]{
                    10,10,10,10,
                    10,10,10,10

            },null,null);
            ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
            shapeDrawable.getPaint().setColor(Color.parseColor(colorsList.get(position).getHex()));


            holder.itemColor.setBackground(shapeDrawable);
        }

        holder.setItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onClick(View v, int pos) {
                listern.showPic(colorsList.get(pos).getName());
                Toast.makeText(mContext, ""+colorsList.get(pos).getName(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return colorsList.size();}
    public interface ColorsAdabterListern{
        void showPic(String colorName);
    }

}
