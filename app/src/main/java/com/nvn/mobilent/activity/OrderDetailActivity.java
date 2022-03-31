package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nvn.mobilent.R;
import com.nvn.mobilent.adapter.OrderItemAdapter;
import com.nvn.mobilent.base.PathAPI;
import com.nvn.mobilent.base.RetrofitClient;
import com.nvn.mobilent.model.ListOrderItem;
import com.nvn.mobilent.model.ROrderItemDetail;
import com.nvn.mobilent.network.OrderAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    int idOrder;
    ImageView imageView;
    TextView name, price, amount;
    Toolbar toolbar;

    OrderAPI orderAPI;
    ListView listOrderDetail;
    ArrayList<ListOrderItem> orderItemArrayList;
    OrderItemAdapter orderItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        idOrder = getIntent().getIntExtra("idorder", -1);
        System.out.println("IDORDER:" + idOrder);
        setControl();
        actionToolBar();
        setEvent();
        getOrderbyOrderId(idOrder);
    }

    private void setEvent() {
    }

    private void setControl() {
        orderItemArrayList = new ArrayList<>();
        listOrderDetail = findViewById(R.id.listorderdetail);
        toolbar = findViewById(R.id.toolbar_orderdetail);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //tạo nút home
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getOrderbyOrderId(int idOrder) {
        orderAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(OrderAPI.class);
        orderAPI.getDetailOrderbyIdOrder(idOrder).enqueue(new Callback<ROrderItemDetail>() {
            @Override
            public void onResponse(Call<ROrderItemDetail> call, Response<ROrderItemDetail> response) {
                if (response.isSuccessful()) {
                    for (ListOrderItem loi : response.body().getData()) {
                        orderItemArrayList.add(new ListOrderItem(loi));
                    }
                    System.out.println(orderItemArrayList.size());
                    System.out.println("SIZELiSTORDERITEM: " + orderItemArrayList.size());
                    orderItemAdapter = new OrderItemAdapter(getApplicationContext(), R.layout.line_orderdetail, orderItemArrayList);
                    listOrderDetail.setAdapter(orderItemAdapter);
                    orderItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ROrderItemDetail> call, Throwable t) {

            }
        });
    }


}