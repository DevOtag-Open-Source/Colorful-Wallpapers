package com.otag.colorfulwallpapers;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.otag.colorfulwallpapers.R;
import com.otag.colorfulwallpapers.fragment.AboutFragment;
import com.otag.colorfulwallpapers.fragment.ColorsFragment;
import com.otag.colorfulwallpapers.fragment.FavoriteFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

//set colors fragment by default
        loadfragment(new ColorsFragment());

//init views
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadfragment(Fragment fragment) {

        if (fragment !=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container,fragment)
                    .commit();
            return true;

        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id =menuItem.getItemId();
        Fragment fragment = null;
        if (id == R.id.action_colors ){
            fragment=new ColorsFragment();
        }else
        if (id == R.id.action_about ){
            fragment=new AboutFragment();
        }else
        if (id == R.id.action_favorite ){
            fragment=new FavoriteFragment();
        }
        return loadfragment(fragment);
    }
}

