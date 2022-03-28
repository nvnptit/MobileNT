package com.nvn.mobilent.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.nvn.mobilent.model.RListCartItem;
import com.nvn.mobilent.model.R_Object;
import com.nvn.mobilent.model.R_ProductCartItem;
import com.nvn.mobilent.network.CartItemAPI;
import com.nvn.mobilent.network.ProductAPI;
import com.nvn.mobilent.util.AppUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    ListView lvCart;
    static TextView tv_TotalCart;
    static ProductAPI productAPI;
    Button btnThanhToan, btnMuaHang;
    ImageView imgDelete;

    Toolbar toolbar;
    CartAdapter cartAdapter;
    static long total = 0;
    static long price = 0;
    TextView tv_NoticeCart;

    //    admin@gmail.com

    public static void eventTotalPrice() {
        deleteAllCart(HomeFragment.objectUser.getId());
        total = 0;
        price = 0;
        productAPI = (ProductAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(ProductAPI.class);
        if (HomeFragment.arrCart.size() > 0) {
            for (Cart item : HomeFragment.arrCart) {
                productAPI.getProductByID(item.getProdId()).enqueue(new Callback<R_ProductCartItem>() {
                    @Override
                    public void onResponse(Call<R_ProductCartItem> call, Response<R_ProductCartItem> response) {
                        Product product = response.body().getData();
                        price = product.getPrice();
                        total = total + price * item.getQuantity();
                        DecimalFormat df = new DecimalFormat("###,###,###");
                        tv_TotalCart.setText(df.format(total) + " VNĐ");
                        updateListCartItem(product.getId(), item.getQuantity(), HomeFragment.objectUser.getId());
                    }

                    @Override
                    public void onFailure(Call<R_ProductCartItem> call, Throwable t) {
                    }

                });
            }
        } else {
            tv_TotalCart.setText("0 VNĐ");
        }
    }

    static void getCart(ArrayList<Cart> carts) {
        HomeFragment.arrCart = carts;
//notify change list
    }

    public static void updateListCartItem(int prod_id, int quantity, int userid) {
        CartItemAPI cartItemAPI = (CartItemAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(CartItemAPI.class);
        cartItemAPI.setNewCartItem(prod_id, quantity, userid).enqueue(new Callback<R_Object>() {
            @Override
            public void onResponse(Call<R_Object> call, Response<R_Object> response) {
                if (response.isSuccessful()) {
                    System.out.println("KETQUA POST: " + response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<R_Object> call, Throwable t) {

            }
        });
    }

    public static void deleteAllCart(int userid) {
        CartItemAPI cartItemAPI = (CartItemAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(CartItemAPI.class);
        cartItemAPI.deleteAllCartByUserId(userid).enqueue(new Callback<R_Object>() {
            @Override
            public void onResponse(Call<R_Object> call, Response<R_Object> response) {
                if (response.isSuccessful()) {
                    System.out.println("KETQUA Delete: " + response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<R_Object> call, Throwable t) {

            }
        });
    }

    public static void loadListCart() {
        CartItemAPI cartItemAPI;
        cartItemAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(CartItemAPI.class);
        cartItemAPI.getCartItemByUserId(4).enqueue(new Callback<RListCartItem>() {
            @Override
            public void onResponse(Call<RListCartItem> call, Response<RListCartItem> response) {
                ArrayList<Cart> arr = response.body().getData();
                getCart(arr);
            }

            @Override
            public void onFailure(Call<RListCartItem> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setControl();
        setActionBar();
        loadListCart();
        checkData();
        eventTotalPrice();
        catchOnItemListView();
        setEventButton();
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
        imgDelete = findViewById(R.id.imagedeletecart);
        toolbar = findViewById(R.id.toolbarcart);
        cartAdapter = new CartAdapter(getApplicationContext(), R.layout.linecartitem, HomeFragment.arrCart);
        lvCart.setAdapter(cartAdapter);
    }

    private void setEventButton() {
        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HomeFragment.arrCart.size() <= 0) {
                    AppUtils.showToast_Short(getApplicationContext(), "Giỏ hàng trống!");
                } else {
                    Intent intent = new Intent(getApplicationContext(), InfoCartActivity.class);
                    startActivity(intent);
                }
            }
        });
        lvCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
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
            }
        });
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