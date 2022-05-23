package com.nvn.mobilent.data.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nvn.mobilent.data.model.category.Category;

import java.util.ArrayList;

public class RCategory {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private ArrayList<Category> data = null;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public ArrayList<Category> getData() {
        return data;
    }

    public void setData(ArrayList<Category> data) {
        this.data = data;
    }

}