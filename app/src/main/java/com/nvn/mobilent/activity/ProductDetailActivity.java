package com.nvn.mobilent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nvn.mobilent.R;
import com.nvn.mobilent.model.Cart;
import com.nvn.mobilent.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ProductDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    Product product;

    ImageView image, heart;
    boolean tmp = false;
    TextView name, quantity, price, detail;
    Spinner spinner;
    Button btn_addcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setControl();
        actionToolBar();
        setEventSpinner();
        loadInfo();
        changeHeart();
        setEventButton();
    }

    private void setEventButton() {
        btn_addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HomeFragment.arrCart.size() > 0) {
                    boolean exist = false;
                    int amount = Integer.parseInt(spinner.getSelectedItem().toString());
                    for (Cart item : HomeFragment.arrCart) {
                        if (item.getId_prod().equals(product.getId())) {
                            System.out.println("Co thang nay " + product.getName());
                            item.setQuantity(item.getQuantity() + amount);
                            if (item.getQuantity() >= 10) {
                                item.setQuantity(10);
                            }
                            exist = true;
                        }
                    }

                    if (exist == false) {
                        HomeFragment.arrCart.add(new Cart(product.getId(), product.getName(), product.getImage(), amount));
                    }
                } else {
                    int amount = Integer.parseInt(spinner.getSelectedItem().toString());
                    System.out.println("AMOUNT NVN: " + amount);
                    HomeFragment.arrCart.add(new Cart(product.getId(), product.getName(), product.getImage(), amount));
                }
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
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