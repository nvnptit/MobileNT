package com.nvn.mobilent.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

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
    private Object deliveryCancelDay;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("recipientName")
    @Expose
    private String recipientName;
    @SerializedName("listOrderItem")
    @Expose
    private ArrayList<ListOrderItem> listOrderItem = null;

    public Order(Integer id, Integer userId, String deliveryAddress, String buyDate, Object deliveryCancelDay, String phone, Integer status, String recipientName, ArrayList<ListOrderItem> listOrderItem) {
        this.id = id;
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.buyDate = buyDate;
        this.deliveryCancelDay = deliveryCancelDay;
        this.phone = phone;
        this.status = status;
        this.recipientName = recipientName;
        this.listOrderItem = listOrderItem;
    }

    public Order(Order order) {
        this.id = order.getId();
        this.userId = order.getUserId();
        this.deliveryAddress = order.getDeliveryAddress();
        this.buyDate = order.getBuyDate();
        this.deliveryCancelDay = order.getDeliveryCancelDay();
        this.phone = order.getPhone();
        this.status = order.getStatus();
        this.recipientName = order.getRecipientName();
        this.listOrderItem = order.getListOrderItem();
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

    public Object getDeliveryCancelDay() {
        return deliveryCancelDay;
    }

    public void setDeliveryCancelDay(Object deliveryCancelDay) {
        this.deliveryCancelDay = deliveryCancelDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public ArrayList<ListOrderItem> getListOrderItem() {
        return listOrderItem;
    }

    public void setListOrderItem(ArrayList<ListOrderItem> listOrderItem) {
        this.listOrderItem = listOrderItem;
    }

    public long getTotal() {
        long total = 0;
        for (ListOrderItem o : listOrderItem) {
            total += o.getPrice() * o.getQuantity();
        }
        return total;
    }
}
