package com.nvn.mobilent.data.datalocal;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPrererences {
    private static final String MYSHARED = "MYSHARED";
    private Context mContext;

    public MySharedPrererences(Context mcontext) {
        this.mContext = mcontext;
    }

    public void putStringValue(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MYSHARED,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MYSHARED,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
