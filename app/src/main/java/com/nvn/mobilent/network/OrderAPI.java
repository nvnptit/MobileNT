package com.nvn.mobilent.network;

import com.nvn.mobilent.model.OrderCheckout;
import com.nvn.mobilent.model.OrderDetailCheckout;
import com.nvn.mobilent.model.RObject;
import com.nvn.mobilent.model.ROrder;
import com.nvn.mobilent.model.ROrderItemDetail;

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

    @GET("/api/order/detail")
    Call<ROrderItemDetail> getDetailOrderbyIdOrder(
            @Query("id") int orderid
    );

    @POST("/api/order")
    @FormUrlEncoded
    Call<OrderCheckout> postOrder1(
            @Field("user_id") int user_id,
            @Field("deliveryAddress") String deliveryAddress,
            @Field("phone") String phone,
            @Field("recipientName") String name,
            @Field("orderDetail[]") ArrayList<OrderDetailCheckout> arr
    );// check lại

    @POST("/api/order")
    @FormUrlEncoded
    Call<RObject> postOrder(
            @Field("user_id") int user_id,
            @Field("deliveryAddress") String deliveryAddress,
            @Field("phone") String phone,
            @Field("recipientName") String name
    );

    @POST("/api/order/order-item")
    @FormUrlEncoded
    Call<OrderDetailCheckout> postOrderDetail(
            @Field("quantity") int quantity,
            @Field("prod_id") int prod_id,
            @Field("price") int price,
            @Field("order_id") int order_id
    );
}