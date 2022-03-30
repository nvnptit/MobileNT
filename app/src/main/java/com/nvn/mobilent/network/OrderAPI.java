package com.nvn.mobilent.network;

import com.nvn.mobilent.model.OrderDetail;
import com.nvn.mobilent.model.RLogin;
import com.nvn.mobilent.model.ROrder;
import com.nvn.mobilent.model.RProduct;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderAPI {
    @GET("/api/order")
    Call<ROrder> getOrderbyUserId(
            @Query("user_id") int user_id,
            @Query("page") int page,
            @Query("page_size") int pagesize
    );

    @GET("/api/order")
    Call<RProduct> getDetailOrderbyIdOrder(
            @Query("id") int orderid
    );

    @POST("/api/order")
    @FormUrlEncoded
    Call<RLogin> postOrder(
            @Field("user_id") int user_id,
            @Field("deliveryAddress") String deliveryAddress,
            @Field("phone") String phone,
            @Field("orderDetail") ArrayList<OrderDetail> arr
    );
}