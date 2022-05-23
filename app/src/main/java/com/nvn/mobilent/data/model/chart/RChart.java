package com.nvn.mobilent.data.model.chart;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RChart {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private ArrayList<ChartCategory> data = null;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public ArrayList<ChartCategory> getData() {
        return data;
    }

    public void setData(ArrayList<ChartCategory> data) {
        this.data = data;
    }

}