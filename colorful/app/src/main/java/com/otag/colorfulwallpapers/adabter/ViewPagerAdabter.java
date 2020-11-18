package com.otag.colorfulwallpapers.adabter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.otag.colorfulwallpapers.R;
import com.otag.colorfulwallpapers.common.Common;

import java.util.List;

public class ViewPagerAdabter extends PagerAdapter {
    private Context context;
    private List<String> listPictures;

    public ViewPagerAdabter(Context context, List<String> listPictures) {
        this.context = context;
        this.listPictures = listPictures;
    }

    @Override
    public int getCount() {

        return listPictures.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(context)
                .load(listPictures.get(position))
                .override(Common.WIDTH,Common.HEIGHT)
                .placeholder(R.drawable.landscape)
                .into(imageView);
        container.addView(imageView,0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }
}
