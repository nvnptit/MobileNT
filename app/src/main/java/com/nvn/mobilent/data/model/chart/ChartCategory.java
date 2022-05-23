package com.nvn.mobilent.data.model.chart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChartCategory {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("so_luong")
    @Expose
    private Integer soLuong;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

}