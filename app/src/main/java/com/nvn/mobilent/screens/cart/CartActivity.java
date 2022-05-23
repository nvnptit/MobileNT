package com.nvn.mobilent.screens.cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nvn.mobilent.R;
import com.nvn.mobilent.data.adapter.CartAdapter;
import com.nvn.mobilent.data.base.PathAPI;
import com.nvn.mobilent.data.base.RetrofitClient;
import com.nvn.mobilent.data.datalocal.DataLocalManager;
import com.nvn.mobilent.data.model.cart.ACart;
import com.nvn.mobilent.data.model.cart.Cart;
import com.nvn.mobilent.data.model.product.Product;
import com.nvn.mobilent.data.model.cart.RListCartItem;
import com.nvn.mobilent.data.model.cart.R_Cart;
import com.nvn.mobilent.data.model.cart.R_ProductCartItem;
import com.nvn.mobilent.data.model.user.User;
import com.nvn.mobilent.data.api.CartItemAPI;
import com.nvn.mobilent.data.api.ProductAPI;
import com.nvn.mobilent.utils.AppUtils;

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
    public static ArrayList<Cart> cartArrayList1;
    private static User user;
    static ListView lvCart;
    static CartAdapter cartAdapter;
    static TextView tv_NoticeCart;
    static ProductAPI productAPI;
    static CartItemAPI cartItemAPI;
    static Cart newCart;
    Toolbar toolbar;

    public static ArrayList<ACart> cartArrayList;

    public static void putCartItem(int cartItem_id, int quantity) {
        System.out.println("putCartItem:" + cartItem_id + "|" + quantity + "|");
        cartItemAPI.editCartItem(cartItem_id, quantity).enqueue(new Callback<R_Cart>() {
            @Override
            public void onResponse(Call<R_Cart> call, Response<R_Cart> response) {
                if (response.isSuccessful()) {
                    AppUtils.showToast_Short(tv_TotalCart.getContext(), "Đã cập nhật sản phẩm vào giỏ hàng!");
                }
                addCart(null);
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
                    addCart(null);
                }
            }

            @Override
            public void onFailure(Call<Error> call, Throwable t) {
                AppUtils.showToast_Short(tv_TotalCart.getContext(), "Lỗi xoá!");
            }
        });
    }

    public static void update() {
        if (cartArrayList.size() > 0) {
            lvCart.setVisibility(View.VISIBLE);
            tv_NoticeCart.setVisibility(View.INVISIBLE);
        } else {
            lvCart.setVisibility(View.INVISIBLE);
            tv_NoticeCart.setVisibility(View.VISIBLE);
        }
        cartAdapter.notifyDataSetChanged();
        //catchOnItemListView();
    }

    public static void loadCartArrayList() {
        cartItemAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(CartItemAPI.class);
        productAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(ProductAPI.class);
        cartItemAPI.getCartItemByUserId(user.getId()).enqueue(new Callback<RListCartItem>() {
            @Override
            public void onResponse(Call<RListCartItem> call, Response<RListCartItem> response) {
                ArrayList<Cart> arrs = response.body().getData();

                for (Cart item : arrs) {
                    productAPI.getProductByID(item.getProdId()).enqueue(new Callback<R_ProductCartItem>() {
                        @Override
                        public void onResponse(Call<R_ProductCartItem> call, Response<R_ProductCartItem> response) {
                            Product product = response.body().getData();
                            ACart aCart = new ACart(item, product.getPrice());
                            addCart(aCart);
                        }

                        @Override
                        public void onFailure(Call<R_ProductCartItem> call, Throwable t) {
                            Log.d("ERROR: ", t.toString());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<RListCartItem> call, Throwable t) {
                Log.d("ERROR: ", t.toString());
            }
        });
        cartAdapter.notifyDataSetChanged();
        update();
    }

    public static void addCart(ACart aCart) {
        int money = 0;
        if (aCart != null) {
            cartArrayList.add(aCart);
            cartAdapter.notifyDataSetChanged();
        }

        for (ACart item :cartArrayList){
            money+= item.getQuantity()*item.getPrice();
        }
        DecimalFormat df = new DecimalFormat("###,###,###");
        tv_TotalCart.setText(df.format(money) + " VNĐ");
        update();
    }

    public static void deleteItem(int position) {
        if (cartArrayList.get(position).getId() == null) {
            deleteCartItem(newIDCart);
            addCart(null);
          //  loadListCart();
        } else {
            deleteCartItem(cartArrayList.get(position).getId());
        //    loadListCart();
            addCart(null);
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
            //                loadListCart();
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        addCart(null);
////        cartArrayList.clear();
////        loadListCart();
////        cartAdapter.notifyDataSetChanged();
//    }

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

        loadCartArrayList();
        setEventButton();
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