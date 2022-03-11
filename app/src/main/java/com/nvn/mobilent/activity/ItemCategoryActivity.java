package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nvn.mobilent.R;
import com.nvn.mobilent.adapter.ItemCategoryAdapter;
import com.nvn.mobilent.base.PathAPI;
import com.nvn.mobilent.base.RetrofitClient;
import com.nvn.mobilent.model.Product;
import com.nvn.mobilent.network.ProductAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemCategoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;

    ItemCategoryAdapter itemCategoryAdapter;
    ArrayList<Product> productArrayList;
    ProductAPI productAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setControl();
        actionToolBar();
        productAPI = (ProductAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(ProductAPI.class);
        getItemCategory();
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

    private int getIdItem() {
        return getIntent().getIntExtra("id", -1);
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar_itemcategory);
        listView = findViewById(R.id.listview_itemcategory);
        productArrayList = new ArrayList<>();
//        itemCategoryAdapter = new ItemCategoryAdapter(getApplicationContext(),productArrayList);
//        listView.setAdapter(itemCategoryAdapter);
    }

    private void getItemCategory() {
        productAPI.getProduct().enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {

                if (response.isSuccessful()) {
                    productArrayList = (ArrayList<Product>) response.body();
                    for (int i = 0; i < productArrayList.size(); i++) {
                        System.out.println("BBBB| " + i);
                        if (productArrayList.get(i).getStatus().equals("false")) {
                            productArrayList.remove(i);
                        }
                    }
                    System.out.println("AAAA: " + productArrayList.size());
                    itemCategoryAdapter = new ItemCategoryAdapter(getApplicationContext(), productArrayList);
                    listView.setAdapter(itemCategoryAdapter);
//                    recyclerView.setHasFixedSize(true);
//                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//                    recyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.d("NVN-API", t.toString());
            }
        });
    }
}