package com.nvn.mobilent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Order {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("deliveryAddress")
    @Expose
    private String deliveryAddress;
    @SerializedName("buyDate")
    @Expose
    private String buyDate;
    @SerializedName("deliveryCancelDay")
    @Expose
    private String deliveryCancelDay;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("listOrderItem")
    @Expose
    private ArrayList<ListOrderItem> listOrderItem = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public Object getDeliveryCancelDay() {
        return deliveryCancelDay;
    }

    public void setDeliveryCancelDay(String deliveryCancelDay) {
        this.deliveryCancelDay = deliveryCancelDay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ArrayList<ListOrderItem> getListOrderItem() {
        return listOrderItem;
    }

    public void setListOrderItem(ArrayList<ListOrderItem> listOrderItem) {
        this.listOrderItem = listOrderItem;
    }

}
