package com.nvn.mobilent.data.model.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nvn.mobilent.data.model.cart.Cart;

import java.util.ArrayList;

public class RListCartItem {
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private ArrayList<Cart> data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public ArrayList<Cart> getData() {
        return data;
    }

    public void setData(ArrayList<Cart> data) {
        this.data = data;
    }

}