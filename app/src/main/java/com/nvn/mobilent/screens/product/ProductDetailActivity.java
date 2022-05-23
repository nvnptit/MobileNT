package com.nvn.mobilent.screens.product;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nvn.mobilent.R;
import com.nvn.mobilent.data.base.PathAPI;
import com.nvn.mobilent.data.base.RetrofitClient;
import com.nvn.mobilent.data.datalocal.DataLocalManager;
import com.nvn.mobilent.data.model.cart.Cart;
import com.nvn.mobilent.data.model.product.Product;
import com.nvn.mobilent.data.model.cart.RListCartItem;
import com.nvn.mobilent.data.model.cart.R_Cart;
import com.nvn.mobilent.data.model.user.User;
import com.nvn.mobilent.data.api.CartItemAPI;
import com.nvn.mobilent.utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    static Toolbar toolbar;
    Product product;

    private static User user;
    ImageView image, heart;
    boolean tmp = false;
    TextView name, quantity, price, detail;
    Spinner spinner;
    Button btn_addcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        user = DataLocalManager.getUser();
        setControl();
        actionToolBar();
        setEventSpinner();
        loadInfo();
        changeHeart();
        setEventButton();
    }

    public static void postCartItem(int prod_id, int quantity, int userid) {
        CartItemAPI cartItemAPI = (CartItemAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(CartItemAPI.class);
        cartItemAPI.setNewCartItem(prod_id, quantity, userid).enqueue(new Callback<R_Cart>() {
            @Override
            public void onResponse(Call<R_Cart> call, Response<R_Cart> response) {
                if (response.isSuccessful()) {
                    AppUtils.showToast_Short(toolbar.getContext(), "Đã thêm sản phẩm vào giỏ hàng!");
                }
            }

            @Override
            public void onFailure(Call<R_Cart> call, Throwable t) {
            }
        });
    }

    public static void putCartItem(int cartItem_id, int quantity) {
        CartItemAPI cartItemAPI = (CartItemAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(CartItemAPI.class);
        cartItemAPI.editCartItem(cartItem_id, quantity).enqueue(new Callback<R_Cart>() {
            @Override
            public void onResponse(Call<R_Cart> call, Response<R_Cart> response) {
                if (response.isSuccessful()) {
                    AppUtils.showToast_Short(toolbar.getContext(), "Đã cập nhật sản phẩm vào giỏ hàng!");
                }
            }

            @Override
            public void onFailure(Call<R_Cart> call, Throwable t) {
            }
        });
    }

    private void setEventButton() {
        btn_addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                int amount = Integer.parseInt(spinner.getSelectedItem().toString());
                CartItemAPI cartItemAPI = (CartItemAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(CartItemAPI.class);
                cartItemAPI.getCartItemByUserId(user.getId()).enqueue(new Callback<RListCartItem>() {
                    @Override
                    public void onResponse(Call<RListCartItem> call, Response<RListCartItem> response) {
                        ArrayList<Cart> arrs = response.body().getData();
                        boolean exist = false;
                        for (Cart item : arrs) {
                            if (item.getProdId().equals(product.getId())) {
                                item.setQuantity(item.getQuantity() + amount);
                                if (item.getQuantity() >= 10) {
                                    item.setQuantity(10);
                                }
                                putCartItem(item.getId(), item.getQuantity());
                                exist = true;
                            }
                        }

                        if (!exist) {
                            postCartItem(product.getId(), amount, user.getId());
                        }
                    }


                    @Override
                    public void onFailure(Call<RListCartItem> call, Throwable t) {
                        Log.d("ERROR: ", t.toString());
                    }
                });
            }
        });
    }

    private void loadInfo() {
        product = (Product) getIntent().getSerializableExtra("product");
        name.setText(product.getName());
        quantity.setText("Số lượng tồn: " + product.getQuantity());
        DecimalFormat df = new DecimalFormat("###,###,###");
        price.setText(df.format(product.getPrice()) + " VNĐ");
        detail.setText(product.getDetail());
        Picasso.get().load(product.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(image);
    }

    private void changeHeart() {
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tmp) {
                    heart.setImageResource(R.drawable.ic_heart1);
                    tmp = true;
                } else {
                    heart.setImageResource(R.drawable.ic_heart2);
                    tmp = false;
                }
            }
        });
    }

    private void setEventSpinner() {
        Integer[] amount = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, amount);
        spinner.setAdapter(arrayAdapter);
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar_productdetail);
        name = findViewById(R.id.tv_nameproductdetail);
        quantity = findViewById(R.id.tv_quantity);
        spinner = findViewById(R.id.spinner);
        price = findViewById(R.id.tv_priceproduct);
        detail = findViewById(R.id.tv_productdetail);
        image = findViewById(R.id.image_productdetail);
        heart = findViewById(R.id.ic_heart);
        btn_addcart = findViewById(R.id.btn_addcart);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //tạo nút home
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}