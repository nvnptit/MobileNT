package com.nvn.mobilent.network;

import com.nvn.mobilent.model.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductAPI {
    @GET("/api/product")
    Call<ArrayList<Product>> getProduct(
            @Query("page") int type,
            @Query("page_size") int page
    );

    @GET("/api/product/category")
    Call<ArrayList<Product>> getProductByType(
            @Query("type") int type,
            @Query("page") int page
    );
}