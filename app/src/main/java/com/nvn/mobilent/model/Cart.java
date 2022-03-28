package com.nvn.mobilent.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {
    @SerializedName("prod_id")
    @Expose
    private Integer prodId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;


    public Cart(Integer prodId, String name, String image, Integer quantity) {
        this.prodId = prodId;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
    }


    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @NonNull
    @Override
    public String toString() {
        return "CART:  " + getProdId() + "|" + getName() + "|" + getQuantity() + "|" + getImage();
    }
}
