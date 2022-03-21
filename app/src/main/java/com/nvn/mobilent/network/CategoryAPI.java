package com.nvn.mobilent.network;

import com.nvn.mobilent.model.RCategory;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryAPI {
    @GET("/api/category")
    Call<RCategory> getCategory();

}
