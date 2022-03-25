package com.nvn.mobilent.network;

import com.nvn.mobilent.model.RListCartItem;
import com.nvn.mobilent.model.R_ResCart;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CartItemAPI {
    @GET("/api/cart-item")
    Call<RListCartItem> getCartItemByID(
            @Query("user_id") int id
    );


    @POST("/api/cart-item")
    @FormUrlEncoded
    Call<R_ResCart> setNewCartItem(
            @Field("quantity") int q,
            @Field("prod_id") int p,
            @Field("user_id") int u
    );

}
