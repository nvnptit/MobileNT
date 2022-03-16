package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nvn.mobilent.R;
import com.nvn.mobilent.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ProductDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    Product product;

    ImageView image, heart;
    boolean tmp = false;
    TextView name, quantity, price, detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setControl();
        actionToolBar();
        loadInfo();
        changeHeart();

    }

    private void loadInfo() {
        product = (Product) getIntent().getSerializableExtra("product");
        name.setText(product.getName());
        quantity.setText("Số lượng tồn: " + product.getQuantity());
        DecimalFormat df = new DecimalFormat("###,###,###");
        price.setText(df.format(product.getPrice()) + " VNĐ");
//        detail.setMaxLines(5);
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


    private void setControl() {
        toolbar = findViewById(R.id.toolbar_productdetail);
        name = findViewById(R.id.tv_nameproductdetail);
        quantity = findViewById(R.id.tv_quantity);
        price = findViewById(R.id.tv_priceproduct);
        detail = findViewById(R.id.tv_productdetail);
        image = findViewById(R.id.image_productdetail);
        heart = findViewById(R.id.ic_heart);
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