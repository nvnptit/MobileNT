package com.nvn.mobilent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {
    @SerializedName("id_prod")
    @Expose
    private Integer id_prod;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    public Cart(Integer id_prod, String name, String image, Integer quantity) {
        this.id_prod = id_prod;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
    }

    public Integer getId_prod() {
        return id_prod;
    }

    public void setId_prod(Integer id_prod) {
        this.id_prod = id_prod;
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
}
