package com.nvn.mobilent.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nvn.mobilent.R;
import com.nvn.mobilent.adapter.OrderAdapter;
import com.nvn.mobilent.base.PathAPI;
import com.nvn.mobilent.base.RetrofitClient;
import com.nvn.mobilent.datalocal.DataLocalManager;
import com.nvn.mobilent.model.Order;
import com.nvn.mobilent.model.RObject;
import com.nvn.mobilent.model.ROrder;
import com.nvn.mobilent.model.R_Cart;
import com.nvn.mobilent.model.User;
import com.nvn.mobilent.network.OrderAPI;
import com.nvn.mobilent.util.AppUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    ListView listOrder;
    static OrderAPI orderAPI;

   public static ArrayList<Order> orderArrayList;
    static OrderAdapter orderAdapter;
    User user;

    int page = 1;
    Toolbar toolbar;
    View footerView;
    MyHandler myHandler;
    boolean isLoading = false;
    boolean limitData = false;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setControl();
        actionToolBar();
        setEvent();
    }

    private void setEvent() {
        getOrderbyUserId(1);
        getMoreItemCategory();
    }

    private void setControl() {
        orderArrayList = new ArrayList<>();
        listOrder = findViewById(R.id.listOrder);
        toolbar = findViewById(R.id.toolbar_order);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.processbar, null);
        myHandler = new MyHandler();
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

    public static void cancelOrder(int id) {
        orderAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(OrderAPI.class);
        orderAPI.cancelOrder(id).enqueue(new Callback<RObject>() {
            @Override
            public void onResponse(Call<RObject> call, Response<RObject> response) {
                if (response.isSuccessful()){
                    if (response.body().getResult()){
                        AppUtils.showToast_Short(orderAdapter.getContext(), "Đã huỷ đơn hàng thành công!");
                    }
                }
            }

            @Override
            public void onFailure(Call<RObject> call, Throwable t) {
                AppUtils.showToast_Short(orderAdapter.getContext(), "Lỗi huỷ đơn hàng!");
            }
        });
    }

    private void getOrderbyUserId(int page) {
        user = DataLocalManager.getUser();
        orderAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(OrderAPI.class);
        orderAPI.getOrderbyUserId(user.getId(), page, 10).enqueue(new Callback<ROrder>() {
            @Override
            public void onResponse(Call<ROrder> call, Response<ROrder> response) {
                if (response.isSuccessful() && response.body().getData().size() > 0) {
                    listOrder.removeFooterView(footerView);
                    for (Order o : response.body().getData()) {
                        orderArrayList.add(new Order(o));
                    }
                    orderAdapter = new OrderAdapter(getApplicationContext(), R.layout.line_order, orderArrayList);
                    listOrder.setAdapter(orderAdapter);
                    orderAdapter.notifyDataSetChanged();
                } else {
                    limitData = true;
                    listOrder.removeFooterView(footerView);
                }
            }

            @Override
            public void onFailure(Call<ROrder> call, Throwable t) {

            }
        });
    }

    private void getMoreItemCategory() {
        listOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), OrderDetailActivity.class);
                intent.putExtra("order", orderArrayList.get(i));
                startActivity(intent);
            }
        });

        listOrder.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0 && !isLoading && !limitData) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            // Quản lý thread gửi lên
            switch (msg.what) {
                case 0: {
                    listOrder.addFooterView(footerView); // Add thanh processbar
                    break;
                }
                case 1: {
                    getOrderbyUserId(++page); //cập nhật đổ dl listview
                    isLoading = false;
                    break;
                }
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1); // method liên kết thread vs handler
            myHandler.sendMessage(message);
        }
    }

}