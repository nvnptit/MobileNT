package com.nvn.mobilent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListOrderItem implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("prod_id")
    @Expose
    private Integer prodId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("image")
    @Expose
    private String image;

    public ListOrderItem(Integer id, Integer quantity, Integer prodId, String name, Integer orderId, Integer price, Boolean status, String image) {
        this.id = id;
        this.quantity = quantity;
        this.prodId = prodId;
        this.name = name;
        this.orderId = orderId;
        this.price = price;
        this.status = status;
        this.image = image;
    }

    public ListOrderItem(ListOrderItem loi) {
        this.id = loi.getId();
        this.quantity = loi.getQuantity();
        this.prodId = loi.getProdId();
        this.name = loi.getName();
        this.orderId = loi.getOrderId();
        this.price = loi.getPrice();
        this.status = loi.getStatus();
        this.image = loi.getImage();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}