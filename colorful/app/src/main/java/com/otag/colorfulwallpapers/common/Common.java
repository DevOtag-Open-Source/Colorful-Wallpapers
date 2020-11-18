package com.otag.colorfulwallpapers.common;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

public class Common {
    public static  List<String> LİST_PİCTURES = null;
    public static  int HEIGHT = 0 ;
    public static  int WIDTH = 0 ;
    public static  String  DEFAULT_COLOR = "Random";

    public static boolean isConnectToInternet(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null)
            return true;

        return  false;
    }
}
