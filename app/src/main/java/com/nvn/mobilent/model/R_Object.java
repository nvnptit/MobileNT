package com.nvn.mobilent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class R_Object {

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
