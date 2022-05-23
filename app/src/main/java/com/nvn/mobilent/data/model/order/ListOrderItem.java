package com.nvn.mobilent.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListOrderItem implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("prod_id")
    @Expose
    private Integer prodId;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("name")
    @Expose
    private String name;


    public ListOrderItem(Integer id, Integer orderId, Integer quantity, Integer prodId, Integer price, String image, String name) {
        this.id = id;
        this.orderId = orderId;
        this.quantity = quantity;
        this.prodId = prodId;
        this.price = price;
        this.image = image;
        this.name = name;
    }

    public ListOrderItem(ListOrderItem loi) {
        this.id = loi.getId();
        this.orderId = loi.getOrderId();
        this.quantity = loi.getQuantity();
        this.prodId = loi.getProdId();
        this.price = loi.getPrice();
        this.image = loi.getImage();
        this.name = loi.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
