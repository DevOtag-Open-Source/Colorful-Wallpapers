package com.otag.colorfulwallpapers.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.otag.colorfulwallpapers.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    ImageView btn_gmail,btn_play_store,btn_devotag,btn_share;


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_about, container, false);


        btn_gmail = view.findViewById(R.id.btn_gmail);
        btn_play_store = view.findViewById(R.id.btn_play_store);
        btn_devotag = view.findViewById(R.id.btn_devotag);
        btn_share = view.findViewById(R.id.btn_share);


        btn_devotag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageDevotag();
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApps();
            }
        });
        btn_play_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate();
            }
        });

        btn_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
        return view;
    }

    private void sendEmail() {

        Intent emailIntenet = new Intent(Intent.ACTION_SEND);
        String aEmailBCCList[]={"hgunduz295@gmail.com"};
        emailIntenet.putExtra(Intent.EXTRA_EMAIL,aEmailBCCList);
        emailIntenet.putExtra(Intent.EXTRA_BCC,aEmailBCCList);
        emailIntenet.putExtra(Intent.EXTRA_BCC,getString(R.string.app_name));
        emailIntenet.setType("text/plain");
        emailIntenet.putExtra(Intent.EXTRA_TEXT,"");
        startActivity(emailIntenet);

    }

    private void rate() {



        try {


            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.otag.colorful" + getContext().getPackageName())));
        }catch (ActivityNotFoundException ex){
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.otag.colorful"+getContext().getPackageName())));
        }

    }

    private void shareApps() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "https://play.google.com/store/apps/details?id=com.otag.colorful"+getContext().getPackageName();
        intent.putExtra(intent.EXTRA_SUBJECT,getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT,shareBodyText);
        startActivity(Intent.createChooser(intent,"Share this app"));


    }

    private void pageDevotag() {


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.devotag.com"));
        startActivity(intent);
    }
}
