package com.nvn.mobilent.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class REmail {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private String data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}