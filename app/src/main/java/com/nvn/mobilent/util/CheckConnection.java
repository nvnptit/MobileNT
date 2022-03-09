package com.nvn.mobilent.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckConnection {
    public static boolean haveNetworkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    return true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    return true;
        }
        return false;
    }

    public static void showToast_Short(Context context, String notice) {
        Toast.makeText(context, notice, Toast.LENGTH_SHORT).show();
    }

    public static void showToast_Long(Context context, String notice) {
        Toast.makeText(context, notice, Toast.LENGTH_LONG).show();
    }
}
