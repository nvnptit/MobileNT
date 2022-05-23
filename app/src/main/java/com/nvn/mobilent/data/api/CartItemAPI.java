package com.nvn.mobilent.data.api;

import com.nvn.mobilent.data.model.cart.RListCartItem;
import com.nvn.mobilent.data.model.cart.R_Cart;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CartItemAPI {
    @GET("/api/cart-item")
    Call<RListCartItem> getCartItemByUserId(
            @Query("user_id") int userid
    );


    @POST("/api/cart-item")
    @FormUrlEncoded
    Call<R_Cart> setNewCartItem(
            @Field("prod_id") int prod_id,
            @Field("quantity") int quantiy,
            @Field("user_id") int userid
    );

    @DELETE("/api/cart-item/all-cart-item")
    Call<R_Cart> deleteAllCartByUserId(
            @Query("user_id") int userid
    );

    @DELETE("/api/cart-item")
    Call<Error> deleteCartItem(
            @Query("id") int id
    );

    @PUT("/api/cart-item")
    Call<R_Cart> editCartItem(
            @Query("cartItem_id") int cartItem_id,
            @Query("quantity") int quantity
    );
}
