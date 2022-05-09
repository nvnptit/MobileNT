package com.nvn.mobilent.activity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.nvn.mobilent.datalocal.DataLocalManager;
import com.nvn.mobilent.model.Cart;
import com.nvn.mobilent.model.Product;
import com.nvn.mobilent.model.RListCartItem;
import com.nvn.mobilent.model.R_Cart;
import com.nvn.mobilent.model.R_ProductCartItem;
import com.nvn.mobilent.model.User;
import com.nvn.mobilent.network.CartItemAPI;
import com.nvn.mobilent.network.ProductAPI;
import com.nvn.mobilent.util.AppUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    public static int newIDCart;
    static TextView tv_TotalCart;
    Button btnThanhToan, btnMuaHang;

    static long total = 0;
    static long price = 0;
    public static ArrayList<Cart> cartArrayList;
    private static User user;
    static ListView lvCart;
    static CartAdapter cartAdapter;
    static TextView tv_NoticeCart;
    static ProductAPI productAPI;
    static CartItemAPI cartItemAPI;
    static Cart newCart;
    Toolbar toolbar;


    public static void putCartItem(int cartItem_id, int quantity) {
        System.out.println("putCartItem:" + cartItem_id + "|" + quantity + "|");
        cartItemAPI.editCartItem(cartItem_id, quantity).enqueue(new Callback<R_Cart>() {
            @Override
            public void onResponse(Call<R_Cart> call, Response<R_Cart> response) {
                if (response.isSuccessful()) {
                    AppUtils.showToast_Short(tv_TotalCart.getContext(), "Đã cập nhật sản phẩm vào giỏ hàng!");
                    solveTotal();
                }
            }

            @Override
            public void onFailure(Call<R_Cart> call, Throwable t) {
            }
        });
    }

    public static void postCartItem(int prod_id, int quantity, int userid) {
        cartItemAPI.setNewCartItem(prod_id, quantity, userid).enqueue(new Callback<R_Cart>() {
            @Override
            public void onResponse(Call<R_Cart> call, Response<R_Cart> response) {
                if (response.isSuccessful()) {
                    AppUtils.showToast_Short(tv_TotalCart.getContext(), "Đã thêm sản phẩm vào giỏ hàng!");
                    newIDCart = response.body().getData().getId();
                    solveTotal();
                }
            }

            @Override
            public void onFailure(Call<R_Cart> call, Throwable t) {
            }
        });
    }

    public static void deleteCartItem(int id) {
        CartItemAPI cartItemAPI = (CartItemAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(CartItemAPI.class);
        cartItemAPI.deleteCartItem(id).enqueue(new Callback<Error>() {
            @Override
            public void onResponse(Call<Error> call, Response<Error> response) {
                if (response.isSuccessful()) {
                    AppUtils.showToast_Short(tv_TotalCart.getContext(), "Đã xoá sản phẩm khỏi giỏ hàng!");
                    solveTotal();
                }
            }

            @Override
            public void onFailure(Call<Error> call, Throwable t) {
                AppUtils.showToast_Short(tv_TotalCart.getContext(), "Lỗi xoá!");
            }
        });
    }

    public static void loadListCart() {
        if (newCart != null) {
            System.out.println(newCart.getName());
        }
        cartItemAPI.getCartItemByUserId(user.getId()).enqueue(new Callback<RListCartItem>() {
            @Override
            public void onResponse(Call<RListCartItem> call, Response<RListCartItem> response) {
                ArrayList<Cart> arrs = response.body().getData();
                if (newCart != null) {
                    System.out.println("NEWCART");
                    boolean exist = false;
                    for (Cart item : arrs) {
                        if (item.getProdId().equals(newCart.getProdId())) {
                            item.setQuantity(item.getQuantity() + newCart.getQuantity());
                            if (item.getQuantity() >= 10) {
                                item.setQuantity(10);
                            }
                            putCartItem(item.getId(), item.getQuantity());
                            exist = true;
                        }
                    }
                    if (!exist) {
                        postCartItem(newCart.getProdId(), newCart.getQuantity(), user.getId());
                        arrs.add(newCart);
                    }
                }
                cartAdapter.notifyDataSetChanged();
                getListCart(arrs);
            }

            @Override
            public void onFailure(Call<RListCartItem> call, Throwable t) {
            }
        });
    }

    public static void solveTotal() {
        // Xử lý tiền
        productAPI = (ProductAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(ProductAPI.class);
        cartItemAPI.getCartItemByUserId(user.getId()).enqueue(new Callback<RListCartItem>() {
            @Override
            public void onResponse(Call<RListCartItem> call, Response<RListCartItem> response) {
                ArrayList<Cart> arrs = response.body().getData();
                if (arrs.size() > 0) {
                    total = 0;
                    for (Cart item : arrs) {
                        productAPI.getProductByID(item.getProdId()).enqueue(new Callback<R_ProductCartItem>() {
                            @Override
                            public void onResponse(Call<R_ProductCartItem> call, Response<R_ProductCartItem> response) {
                                Product product = response.body().getData();
                                price = product.getPrice();
                                total = total + price * item.getQuantity();
                                DecimalFormat df = new DecimalFormat("###,###,###");
                                tv_TotalCart.setText(df.format(total) + " VNĐ");
                                System.out.println(total);
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

            @Override
            public void onFailure(Call<RListCartItem> call, Throwable t) {
            }
        });
    }

    private static void getListCart(ArrayList<Cart> data) {
        cartArrayList.clear();
        for (Cart i : data) {
            cartArrayList.add(new Cart(i));
        }
        if (cartArrayList.size() > 0) {
            lvCart.setVisibility(View.VISIBLE);
            tv_NoticeCart.setVisibility(View.INVISIBLE);
            cartAdapter.notifyDataSetChanged();
        } else {
            lvCart.setVisibility(View.INVISIBLE);
            tv_NoticeCart.setVisibility(View.VISIBLE);
            cartAdapter.notifyDataSetChanged();
        }
        catchOnItemListView();
    }

    public static void deleteItem(int position) {
        if (cartArrayList.get(position).getId() == null) {
            deleteCartItem(newIDCart);
            solveTotal();
        } else {
            deleteCartItem(cartArrayList.get(position).getId());
            solveTotal();
        }
        cartArrayList.remove(position);
        AppUtils.showToast_Short(tv_TotalCart.getContext(), "Đã xoá sản phẩm ra khỏi giỏ hàng");
        cartAdapter.notifyDataSetChanged();
    }

    public static void catchOnItemListView() {
        lvCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(lvCart.getContext());
                builder.setTitle("Xác nhận xoá sản phẩm");
                builder.setMessage("Bạn có chắc xoá sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (cartArrayList.size() <= 0) {
                            tv_NoticeCart.setVisibility(View.VISIBLE);
                        } else {
                            // Xử lý ArrayCart
                            if (cartArrayList.get(position).getId() == null) {
                                deleteCartItem(newIDCart);
                            } else {
                                deleteCartItem(cartArrayList.get(position).getId());
                            }
                            cartArrayList.remove(position);
                            AppUtils.showToast_Short(lvCart.getContext(), "Đã xoá sản phẩm khỏi giỏ hàng");
                            cartAdapter.notifyDataSetChanged();
                            if (cartArrayList.size() <= 0) {
                                tv_NoticeCart.setVisibility(View.VISIBLE);
                            } else {
                                tv_NoticeCart.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                            }
                            solveTotal();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cartAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    protected void onResume() {
        solveTotal();
        cartArrayList.clear();
        cartAdapter.notifyDataSetChanged();
        super.onResume();
    }

    private void setControl() {
        lvCart = findViewById(R.id.listviewcart);
        tv_NoticeCart = findViewById(R.id.tv_noticecart);
        tv_TotalCart = findViewById(R.id.totalcart);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnMuaHang = findViewById(R.id.btnTTMuaHang);
        toolbar = findViewById(R.id.toolbarcart);

        cartArrayList = new ArrayList<>();
        cartAdapter = new CartAdapter(getApplicationContext(), R.layout.linecartitem, cartArrayList);
        lvCart.setAdapter(cartAdapter);
        cartItemAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(CartItemAPI.class);
    }

    private void setActionBar() {
        setSupportActionBar(toolbar); // hỗ trợ toolbar như actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set nút backhome toolbar
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        user = DataLocalManager.getUser();
        setControl();
        newCart = (Cart) getIntent().getSerializableExtra("newcartitem");
        setActionBar();

        loadListCart();
        setEventButton();
        System.out.println("SIZECARTCLICK: " + cartArrayList.size());
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
                if (cartArrayList.size() <= 0) {
                    AppUtils.showToast_Short(getApplicationContext(), "Giỏ hàng trống!");
                } else {
                    finish(); // destroy activity
                    System.out.println("CARTSIZE odio:" + cartArrayList.size());
                    Intent intent = new Intent(getApplicationContext(), InfoCartActivity.class);
                    intent.putExtra("sizecart", cartArrayList.size());
                    startActivity(intent);
                }
            }
        });
    }
}