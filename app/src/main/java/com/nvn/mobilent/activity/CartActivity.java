package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nvn.mobilent.R;
import com.nvn.mobilent.adapter.CartAdapter;
import com.nvn.mobilent.base.PathAPI;
import com.nvn.mobilent.base.RetrofitClient;
import com.nvn.mobilent.model.Cart;
import com.nvn.mobilent.model.Product;
import com.nvn.mobilent.model.RCartItem;
import com.nvn.mobilent.network.ProductAPI;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    ListView lvCart;
    TextView tv_NoticeCart, tv_TotalCart;
    Button btnThanhToan, btnMuaHang;

    Toolbar toolbar;
    CartAdapter cartAdapter;
    ProductAPI productAPI;
    long total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setControl();
        setActionBar();
        checkData();
        loadListCart();
    }

    private void setValue(long number, long sl) {
        this.total = this.total + number * sl;
    }

    private void loadListCart() {
        productAPI = (ProductAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(ProductAPI.class);
        for (Cart item : HomeFragment.arrCart) {
            productAPI.getProductByID(item.getId_prod()).enqueue(new Callback<RCartItem>() {
                @Override
                public void onResponse(Call<RCartItem> call, Response<RCartItem> response) {
                    Product product = response.body().getData();
                    int price = product.getPrice();
                    setValue(price, item.getQuantity());
                }

                @Override
                public void onFailure(Call<RCartItem> call, Throwable t) {
                }

            });
            DecimalFormat df = new DecimalFormat("###,###,###");
            tv_TotalCart.setText(df.format(total) + " VNĐ");
        }
    }

    private void checkData() {
        if (HomeFragment.arrCart.size() > 0) {
            lvCart.setVisibility(View.VISIBLE);
            tv_NoticeCart.setVisibility(View.INVISIBLE);
            cartAdapter.notifyDataSetChanged();
        } else {
            lvCart.setVisibility(View.INVISIBLE);
            tv_NoticeCart.setVisibility(View.VISIBLE);
            cartAdapter.notifyDataSetChanged();
        }
    }

    private void setActionBar() {
        setSupportActionBar(toolbar); // hỗ trợ toolbar như actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set nút backhome toolbar
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    private void setControl() {
        lvCart = findViewById(R.id.listviewcart);
        tv_NoticeCart = findViewById(R.id.tv_noticecart);
        tv_TotalCart = findViewById(R.id.totalcart);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnMuaHang = findViewById(R.id.btnTTMuaHang);
        toolbar = findViewById(R.id.toolbarcart);
        cartAdapter = new CartAdapter(getApplicationContext(), R.layout.linecartitem, HomeFragment.arrCart);
        lvCart.setAdapter(cartAdapter);
    }

}