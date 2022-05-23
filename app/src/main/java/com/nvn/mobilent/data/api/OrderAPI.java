package com.nvn.mobilent.data.api;

import com.nvn.mobilent.data.model.order.OrderCheckout;
import com.nvn.mobilent.data.model.order.OrderDetailCheckout;
import com.nvn.mobilent.data.model.order.ROrderObject;
import com.nvn.mobilent.data.model.order.ROrder;
import com.nvn.mobilent.data.model.order.ROrderItemDetail;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<ROrderObject> postOrder(
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
    @PUT("/api/order/huy-hang")
    Call<ROrderObject> cancelOrder(
            @Query("id") int id
    );
}