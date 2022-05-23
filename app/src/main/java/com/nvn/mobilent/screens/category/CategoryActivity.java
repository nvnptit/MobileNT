package com.nvn.mobilent.screens.category;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nvn.mobilent.R;
import com.nvn.mobilent.screens.product.ProductDetailActivity;
import com.nvn.mobilent.data.adapter.ItemCategoryAdapter;
import com.nvn.mobilent.data.base.PathAPI;
import com.nvn.mobilent.data.base.RetrofitClient;
import com.nvn.mobilent.data.model.product.Product;
import com.nvn.mobilent.data.model.product.RProduct;
import com.nvn.mobilent.data.api.ProductAPI;
import com.nvn.mobilent.screens.cart.CartActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;

    int idCate;
    String nameCate;
    int page = 1;

    ItemCategoryAdapter itemCategoryAdapter;
    ArrayList<Product> productArrayList;
    ProductAPI productAPI;

    View footerView;
    boolean isLoading = false;
    boolean limitData = false;
    MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        setControl();
        idCate = getIntent().getIntExtra("idCate", -1);
        nameCate = getIntent().getStringExtra("nameCate");
        toolbar.setTitle(nameCate);

        actionToolBar();
        productAPI = (ProductAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(ProductAPI.class);
        getItemCategory(idCate, page);
        getMoreItemCategory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cartmenu: {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void getMoreItemCategory() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                intent.putExtra("product", productArrayList.get(i));
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void setControl() {
        toolbar = findViewById(R.id.toolbar_itemcategory);
        listView = findViewById(R.id.listview_itemcategory);
        productArrayList = new ArrayList<>();

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

    private void getItemCategory(int idCate, int page) {
        productAPI.getProductByType(idCate, page).enqueue(new Callback<RProduct>() {
            @Override
            public void onResponse(Call<RProduct> call, Response<RProduct> response) {
                if (response.isSuccessful() && response.body().getData().size() > 0) {
                    listView.removeFooterView(footerView);
                    ArrayList<Product> arrRes = new ArrayList();
                    arrRes = response.body().getData();
                    for (int i = 0; i < arrRes.size(); i++) {
                        if (arrRes.get(i).getStatus()) {
                            productArrayList.add(arrRes.get(i));
                        }
                    }
                    itemCategoryAdapter = new ItemCategoryAdapter(getApplicationContext(), productArrayList);
                    listView.setAdapter(itemCategoryAdapter);
                } else {
                    limitData = true;
                    listView.removeFooterView(footerView);
                }
            }

            @Override
            public void onFailure(Call<RProduct> call, Throwable t) {
                Log.d("NVN-API", t.toString());
            }
        });
    }

    private int getIdItem() {
        return getIntent().getIntExtra("id", -1);
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            // Quản lý thread gửi lên
            switch (msg.what) {
                case 0: {
                    listView.addFooterView(footerView); // Add thanh processbar
                    break;
                }
                case 1: {
                    getItemCategory(idCate, ++page); //cập nhật đổ dl listview
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