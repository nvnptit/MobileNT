package com.nvn.mobilent.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RLogin {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private User data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

}
