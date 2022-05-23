package com.nvn.mobilent.data.model.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nvn.mobilent.data.model.cart.Cart;

public class R_Cart {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private Cart data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Cart getData() {
        return data;
    }

    public void setData(Cart data) {
        this.data = data;
    }

}
