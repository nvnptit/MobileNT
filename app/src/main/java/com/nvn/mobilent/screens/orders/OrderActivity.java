package com.nvn.mobilent.screens.orders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nvn.mobilent.R;
import com.nvn.mobilent.data.adapter.OrderAdapter;
import com.nvn.mobilent.data.base.PathAPI;
import com.nvn.mobilent.data.base.RetrofitClient;
import com.nvn.mobilent.data.datalocal.DataLocalManager;
import com.nvn.mobilent.data.model.order.Order;
import com.nvn.mobilent.data.model.order.ROrderObject;
import com.nvn.mobilent.data.model.order.ROrder;
import com.nvn.mobilent.data.model.user.User;
import com.nvn.mobilent.data.api.OrderAPI;
import com.nvn.mobilent.utils.AppUtils;

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
        orderAPI.cancelOrder(id).enqueue(new Callback<ROrderObject>() {
            @Override
            public void onResponse(Call<ROrderObject> call, Response<ROrderObject> response) {
                if (response.isSuccessful()){
                    if (response.body().getResult()){
                        AppUtils.showToast_Short(orderAdapter.getContext(), "Đã huỷ đơn hàng thành công!");
                    }
                }
            }

            @Override
            public void onFailure(Call<ROrderObject> call, Throwable t) {
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