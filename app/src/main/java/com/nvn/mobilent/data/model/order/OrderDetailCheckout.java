package com.nvn.mobilent.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailCheckout {
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("prod_id")
    @Expose
    private Integer prodId;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;

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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

}