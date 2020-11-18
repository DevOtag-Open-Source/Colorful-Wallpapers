package com.otag.colorfulwallpapers;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.otag.colorfulwallpapers.R;
import com.otag.colorfulwallpapers.adabter.ViewPagerAdabter;
import com.otag.colorfulwallpapers.common.Common;
import com.otag.colorfulwallpapers.database.Favorite;
import com.otag.colorfulwallpapers.database.FavoriteRoomDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dmax.dialog.SpotsDialog;

public class ViewWalpaper extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdabter adabter;
    RelativeLayout rootLayout;
    FloatingActionButton fabDownload,fabFavorite,fabSet;
    SpotsDialog alertDialog;

    static final int PERMISIION_REQUEST_CODE = 1000;
    static final int NUM_OF_THREADS = 4;
    ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREADS);
    FavoriteRoomDatabase database;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wallpaper);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Common.WIDTH = size.x;
        Common.HEIGHT = size.y;
        database = FavoriteRoomDatabase.getDatabse(this);
        addFavorite();
        addViewPager();


        rootLayout = findViewById(R.id.root_layout);
        fabDownload = findViewById(R.id.fabDownload);
        fabDownload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ViewWalpaper.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISIION_REQUEST_CODE);
                }else{
                    downloadBitMap();
                }


            }
        });
        fabSet = findViewById(R.id.fabSet);
        fabSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWalpaperToScreen();
            }
        });

        fabFavorite = findViewById(R.id.fabFavorite);
        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFavorite();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==PERMISIION_REQUEST_CODE && grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED){

            downloadBitMap();
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void addFavorite() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String currentLinkImage=Common.LİST_PİCTURES.get(viewPager.getCurrentItem());
                Favorite favorite = database.dao().isExist(currentLinkImage);
                if (favorite !=null){
                    fabFavorite.setImageResource(R.drawable.ic_favorite_yes);
                }else{
                    fabFavorite.setImageResource(R.drawable.ic_favorite_no);
                }


            }
        });

    }

    private void updateFavorite() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String currentLinkImage = Common.LİST_PİCTURES.get(viewPager.getCurrentItem());
                Favorite favorite = database.dao().isExist(currentLinkImage);
                if (favorite == null){


                    fabFavorite.setImageResource(R.drawable.ic_favorite_yes);
                    database.dao().insertFavorite(new Favorite(currentLinkImage,""+System.currentTimeMillis()));

                }else{


                    fabFavorite.setImageResource(R.drawable.ic_favorite_no);
                    database.dao().deleteFavorite(currentLinkImage);
                }
            }
        });
    }

    private void setWalpaperToScreen() {
        MobileAds.initialize(this,"ca-app-pub-1999652019836649~6379286222");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1999652019836649/5446683782");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mInterstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                super.onAdLoaded();
            }

            @Override
            public void onAdClosed() {
                wpfonksiyon();
                super.onAdClosed();
            }
        });


    }

    private void wpfonksiyon() {
        alertDialog = new SpotsDialog(ViewWalpaper.this);
        alertDialog.show();
        alertDialog.setMessage("Please waiting...");
        Glide.with(this)
                .asBitmap()
                .load(Common.LİST_PİCTURES.get(viewPager.getCurrentItem()))
                .override(Common.WIDTH,Common.HEIGHT)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(ViewWalpaper.this);
                        try {
                            wallpaperManager.setBitmap(resource);
                            Snackbar.make(rootLayout,"wallpaper was set",Snackbar.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        alertDialog.dismiss();
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    private void downloadBitMap() {
        MobileAds.initialize(this,"ca-app-pub-1999652019836649~6379286222");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1999652019836649/9218106516");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mInterstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                super.onAdLoaded();
            }

            @Override
            public void onAdClosed() {
                otag();
                super.onAdClosed();
            }
        });


    }

    private void otag() {
        alertDialog = new SpotsDialog(ViewWalpaper.this);
        alertDialog.show();
        alertDialog.setMessage("Please waiting...");

        Glide.with(this)
                .asBitmap()
                .load(Common.LİST_PİCTURES.get(viewPager.getCurrentItem()))
                .override(Common.WIDTH,Common.HEIGHT)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        try {
                            saveBitmap(resource);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }

    private void saveBitmap(Bitmap bitmap) throws  IOException{

        String fileName = UUID.randomUUID()+".jpg";
        OutputStream outputStream;
        boolean isSaved;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,fileName);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES);
            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
            outputStream = resolver.openOutputStream(uri);
        }else{

            String path = Environment.getExternalStorageDirectory().toString();

            File folder = new File(path+"/"+getString(R.string.app_name));
            if (!folder.exists()){


                folder.mkdirs();
            }
            File file = new File(folder,fileName);
            if (file.exists())
                file.delete();


            outputStream = new FileOutputStream(file);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));

        }
        isSaved = bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        if(isSaved){
        Snackbar.make(rootLayout,"Download Succesful", Snackbar.LENGTH_LONG ).show();}else{
            Snackbar.make(rootLayout,"failed !!", Snackbar.LENGTH_LONG ).show();
        }
        outputStream.flush();
        outputStream.close();
        alertDialog.dismiss();
    }

    private void addViewPager() {

        if (getIntent() !=null){
            viewPager = findViewById(R.id.viewPager);

            int pos = getIntent().getIntExtra("current_img",0);
            adabter = new ViewPagerAdabter(this,Common.LİST_PİCTURES);
            viewPager.setAdapter(adabter);
            viewPager.setCurrentItem(pos);
            adabter.notifyDataSetChanged();
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    addFavorite();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }


    }
}
