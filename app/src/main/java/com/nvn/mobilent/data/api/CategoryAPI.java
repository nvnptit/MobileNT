package com.nvn.mobilent.data.api;

import com.nvn.mobilent.data.model.category.RCategory;
import com.nvn.mobilent.data.model.chart.RChart;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryAPI {
    @GET("/api/category")
    Call<RCategory> getCategory();

    @GET("/api/category/report-category")
    Call<RChart> getChartCategory();

}
