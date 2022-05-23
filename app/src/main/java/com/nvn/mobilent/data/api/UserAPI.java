package com.nvn.mobilent.data.api;

import com.nvn.mobilent.data.model.user.RLogin;
import com.nvn.mobilent.data.model.user.REmail;
import com.nvn.mobilent.data.model.user.RUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserAPI {
    @GET("/api/user")
    Call<RUser> getCartItemByID(
            @Query("page") int type,
            @Query("page_size") int page
    );


    @POST("/api/user/login")
    Call<RLogin> loginUser(
            @Query("email") String email,
            @Query("password") String password
    );


    @POST("/api/user")
    @FormUrlEncoded
    Call<RLogin> registerUser(
            @Field("email") String email,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("password") String password,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("sex") int sex,
            @Field("birthday") String birthday
    );

    @PUT("/api/user")
    @FormUrlEncoded
    Call<RLogin> changeInfo(
            @Field("id") int id,
            @Field("email") String email,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("sex") int sex,
            @Field("birthday") String birthday
    );

    @POST("/api/user/change-password")
    Call<RLogin> changePassword(
            @Query("email") String email,
            @Query("oldPassword") String oldPassword,
            @Query("newPassword") String newPassword
    );

    @POST("/api/user/change-password-by-phone")
    Call<RLogin> changePasswordbyPhone(
            @Query("phone") String phone,
            @Query("newPassword") String newPassword
    );

    @POST("/api/user/forgot-password")
    Call<REmail> forgotPassword(
            @Query("email") String email
    );
}