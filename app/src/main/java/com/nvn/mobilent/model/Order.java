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

    public Order(Integer id, Integer userId, String deliveryAddress, String buyDate, String deliveryCancelDay, Integer status, ArrayList<ListOrderItem> listOrderItem) {
        this.id = id;
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.buyDate = buyDate;
        this.deliveryCancelDay = deliveryCancelDay;
        this.status = status;
        this.listOrderItem = listOrderItem;
    }

    public Order(Order ord) {
        this.id = ord.getId();
        this.userId = ord.getUserId();
        this.deliveryAddress = ord.getDeliveryAddress();
        this.buyDate = ord.getBuyDate();
        this.deliveryCancelDay = ord.getDeliveryCancelDay();
        this.status = ord.getStatus();
        this.listOrderItem = ord.getListOrderItem();
    }

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

    public String getDeliveryCancelDay() {
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
