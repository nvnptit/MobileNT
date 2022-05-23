package com.nvn.mobilent.data.datalocal;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.nvn.mobilent.data.model.user.User;

public class DataLocalManager {
    private static final String PREF_OBJECT = "PREF_OBJECT";
    private static DataLocalManager instance;
    private MySharedPrererences mySharedPrererences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharedPrererences = new MySharedPrererences(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null)
            instance = new DataLocalManager();
        return instance;
    }

    public static User getUser() {
        String jsonUser = DataLocalManager.getInstance().mySharedPrererences.getStringValue(PREF_OBJECT);
        Gson gson = new Gson();
        User user = gson.fromJson(jsonUser, User.class);
        return user;
    }

    public static void setUser(User user) {
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        DataLocalManager.getInstance().mySharedPrererences.putStringValue(PREF_OBJECT, jsonUser);
    }

    public static Uri getUriImage() {
        String uri = DataLocalManager.getInstance().mySharedPrererences.getStringValue("URI");
        if (uri.isEmpty()) {
            return null;
        }
        return Uri.parse(uri);
    }

    public static void setUriImage(Uri uriImage) {
        DataLocalManager.getInstance().mySharedPrererences.putStringValue("URI", uriImage.toString());
    }

}
