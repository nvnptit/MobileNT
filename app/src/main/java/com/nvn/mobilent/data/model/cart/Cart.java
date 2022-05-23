package com.nvn.mobilent.data.model.cart;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cart implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
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


    public Cart(Integer id, Integer prodId, String name, String image, Integer quantity) {
        this.id = id;
        this.prodId = prodId;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
    }

    public Cart(Integer prodId, String name, String image, Integer quantity) {
        this.prodId = prodId;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
    }

    public Cart(Cart c) {
        this.id = c.getId();
        this.prodId = c.getProdId();
        this.name = c.getName();
        this.image = c.getImage();
        this.quantity = c.getQuantity();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
