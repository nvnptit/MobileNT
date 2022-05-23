package com.nvn.mobilent.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingItem {
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("data")
    @Expose
    private String data;

    public SettingItem() {
    }

    public SettingItem(String image, String data) {
        this.image = image;
        this.data = data;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
