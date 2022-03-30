package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.nvn.mobilent.R;
import com.nvn.mobilent.adapter.OrderAdapter;
import com.nvn.mobilent.base.PathAPI;
import com.nvn.mobilent.base.RetrofitClient;
import com.nvn.mobilent.datalocal.DataLocalManager;
import com.nvn.mobilent.model.Order;
import com.nvn.mobilent.model.ROrder;
import com.nvn.mobilent.model.User;
import com.nvn.mobilent.network.OrderAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    ListView listOrder;
    OrderAPI orderAPI;

    ArrayList<Order> orderArrayList;
    OrderAdapter orderAdapter;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setControl();
        setEvent();
    }

    private void setEvent() {
        user = DataLocalManager.getUser();
        orderAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(OrderAPI.class);
        orderAPI.getOrderbyUserId(user.getId(), 1, 10).enqueue(new Callback<ROrder>() {
            @Override
            public void onResponse(Call<ROrder> call, Response<ROrder> response) {
                if (response.isSuccessful()) {
                    for (Order o : response.body().getData()) {
                        orderArrayList.add(new Order(o));
                    }
                    orderAdapter = new OrderAdapter(getApplicationContext(), R.layout.line_order, orderArrayList);
                    listOrder.setAdapter(orderAdapter);
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ROrder> call, Throwable t) {

            }
        });
    }

    private void setControl() {
        orderArrayList = new ArrayList<>();
        listOrder = findViewById(R.id.listOrder);
    }
}