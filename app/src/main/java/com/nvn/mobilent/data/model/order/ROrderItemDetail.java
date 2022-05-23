package com.nvn.mobilent.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nvn.mobilent.data.model.order.ListOrderItem;

import java.util.ArrayList;

public class ROrderItemDetail {
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private ArrayList<ListOrderItem> data = null;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public ArrayList<ListOrderItem> getData() {
        return data;
    }

    public void setData(ArrayList<ListOrderItem> data) {
        this.data = data;
    }

}