package com.nvn.mobilent.network;

import com.nvn.mobilent.model.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductAPI {
    @GET("/prod")
    Call<ArrayList<Product>> getProduct();
}
/*
    @GET("/prod/{movie_id}")
    Call<ArrayList<Product>> getProduct1(
            @Path("movie_id")int id
    );

    @GET("/prod")
    Call<ArrayList<Product>> searchMovieByName(
            @Query("query") String query,
            @Query("page") int page
    );*/