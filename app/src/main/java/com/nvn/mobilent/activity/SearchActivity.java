package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nvn.mobilent.R;
import com.nvn.mobilent.adapter.ProductAdapter;
import com.nvn.mobilent.base.PathAPI;
import com.nvn.mobilent.base.RetrofitClient;
import com.nvn.mobilent.model.Product;
import com.nvn.mobilent.model.RProduct;
import com.nvn.mobilent.network.ProductAPI;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setControl();
        setEvent();
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
        keySearch = findViewById(R.id.editSearch);
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
}