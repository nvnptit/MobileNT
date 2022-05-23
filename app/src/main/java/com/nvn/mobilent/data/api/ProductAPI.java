package com.nvn.mobilent.data.api;

import com.nvn.mobilent.data.model.product.RProduct;
import com.nvn.mobilent.data.model.cart.R_ProductCartItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductAPI {
    @GET("/api/product/newest")
    Call<RProduct> getProduct(
            @Query("page") int type,
            @Query("page_size") int page
    );

    @GET("/api/product/name")
    Call<RProduct> searchProduct(
            @Query("name") String name,
            @Query("page") int type,
            @Query("page_size") int page
    );

    @GET("/api/product/category")
    Call<RProduct> getProductByType(
            @Query("type") int type,
            @Query("page") int page
    );

    @GET("/api/product/id")
    Call<R_ProductCartItem> getProductByID(
            @Query("id") int id
    );

}