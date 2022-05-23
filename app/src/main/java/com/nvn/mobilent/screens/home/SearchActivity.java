package com.nvn.mobilent.screens.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nvn.mobilent.R;
import com.nvn.mobilent.data.adapter.ProductAdapter;
import com.nvn.mobilent.data.base.PathAPI;
import com.nvn.mobilent.data.base.RetrofitClient;
import com.nvn.mobilent.data.model.product.Product;
import com.nvn.mobilent.data.model.product.RProduct;
import com.nvn.mobilent.data.api.ProductAPI;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductAPI productAPI;
    EditText keySearch;
    ProductAdapter productAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setControl();
        setEvent();
        setActionBar();
    }

    private void setActionBar() {
        toolbar = findViewById(R.id.toolbarsearch1);
        setSupportActionBar(toolbar); // hỗ trợ toolbar như actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set nút backhome toolbar
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }


    private void setEvent() {
        keySearch.addTextChangedListener(new TextWatcher() {
            private final long DELAY = 500; // m
            private Timer timer = new Timer();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                getProduct();
                            }
                        },
                        DELAY
                );
            }
        });
    }

    private void setControl() {
        keySearch = findViewById(R.id.editSearch1);
        recyclerView = findViewById(R.id.searchrecyclerview);
    }

    private void getProduct() {
        productAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(ProductAPI.class);
        productAPI.searchProduct(keySearch.getText().toString().trim(), 1, 10).enqueue(new Callback<RProduct>() {
            @Override
            public void onResponse(Call<RProduct> call, Response<RProduct> response) {
                if (response.isSuccessful()) {
                    ArrayList<Product> arrs = new ArrayList<>();
                    for (Product i : response.body().getData()) {
                        if (i.getStatus()) {
                            arrs.add(i);
                        }
                    }
                    productAdapter = new ProductAdapter(getApplicationContext(), arrs);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                    recyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(Call<RProduct> call, Throwable t) {
                Log.d("NVN-API", t.toString());

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (productAdapter != null) {
            productAdapter.release();
        }
    }
}