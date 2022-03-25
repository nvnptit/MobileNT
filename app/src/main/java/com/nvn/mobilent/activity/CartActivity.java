package com.nvn.mobilent.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
    static TextView tv_TotalCart;
    static ProductAPI productAPI;
    Button btnThanhToan, btnMuaHang;

    Toolbar toolbar;
    CartAdapter cartAdapter;
    static long total = 0;
    static long price = 0;
    TextView tv_NoticeCart;

    public static void eventTotalPrice() {
        total = 0;
        price = 0;
        productAPI = (ProductAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(ProductAPI.class);
        if (HomeFragment.arrCart.size() > 0) {

            for (Cart item : HomeFragment.arrCart) {
                productAPI.getProductByID(item.getId_prod()).enqueue(new Callback<RCartItem>() {
                    @Override
                    public void onResponse(Call<RCartItem> call, Response<RCartItem> response) {
                        Product product = response.body().getData();
                        price = product.getPrice();
                        total = total + price * item.getQuantity();
                        DecimalFormat df = new DecimalFormat("###,###,###");
                        tv_TotalCart.setText(df.format(total) + " VNĐ");
                    }

                    @Override
                    public void onFailure(Call<RCartItem> call, Throwable t) {
                    }

                });
            }
        } else {
            tv_TotalCart.setText("0 VNĐ");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setControl();
        setActionBar();
        checkData();
        eventTotalPrice();
        catchOnItemListView();
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

    private void catchOnItemListView() {
        lvCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Xác nhận xoá sản phẩm");
                builder.setMessage("Bạn có chắc xoá sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (HomeFragment.arrCart.size() <= 0) {
                            tv_NoticeCart.setVisibility(View.VISIBLE);
                            eventTotalPrice();
                        } else {
                            // Xử lý ArrayCart
                            HomeFragment.arrCart.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            if (HomeFragment.arrCart.size() <= 0) {
                                tv_NoticeCart.setVisibility(View.VISIBLE);
                            } else {
                                tv_NoticeCart.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                            }
                        }
                        eventTotalPrice();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cartAdapter.notifyDataSetChanged();
                        CartActivity.eventTotalPrice();
                    }
                });
                builder.show();
                return true;

            }
        });
    }

}