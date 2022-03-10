package com.nvn.mobilent.network;

import com.nvn.mobilent.model.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductAPI {
    @GET("/prod")
    Call<ArrayList<Product>> getProduct();

}
