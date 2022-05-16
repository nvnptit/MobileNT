package com.nvn.mobilent.network;

import com.nvn.mobilent.model.RCategory;
import com.nvn.mobilent.model.RChart;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryAPI {
    @GET("/api/category")
    Call<RCategory> getCategory();

    @GET("/api/category/report-category")
    Call<RChart> getChartCategory();

}
