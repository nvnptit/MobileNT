package com.nvn.mobilent.data.model.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nvn.mobilent.data.model.product.Product;

public class R_ProductCartItem {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private Product data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Product getData() {
        return data;
    }

    public void setData(Product data) {
        this.data = data;
    }

}
