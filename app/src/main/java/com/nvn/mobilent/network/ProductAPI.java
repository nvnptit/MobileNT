package com.nvn.mobilent.network;

import com.nvn.mobilent.model.RProduct;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductAPI {
    @GET("/api/product")
    Call<RProduct> getProduct(
            @Query("page") int type,
            @Query("page_size") int page
    );

    @GET("/api/product/category")
    Call<RProduct> getProductByType(
            @Query("type") int type,
            @Query("page") int page
    );
}