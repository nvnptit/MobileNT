package com.nvn.mobilent.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nvn.mobilent.R;
import com.nvn.mobilent.activity.CartActivity;
import com.nvn.mobilent.base.PathAPI;
import com.nvn.mobilent.base.RetrofitClient;
import com.nvn.mobilent.model.Order;
import com.nvn.mobilent.model.Product;
import com.nvn.mobilent.model.R_ProductCartItem;
import com.nvn.mobilent.network.ProductAPI;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends ArrayAdapter<Order> {
    Context context;
    int resource;
    ArrayList<Order> orderArrayList;

    public OrderAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Order> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.orderArrayList = objects;
    }
    @Override
    public int getCount() {
        return orderArrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView nameCart = convertView.findViewById(R.id.tv_cart);
        TextView priceCart = convertView.findViewById(R.id.tv_pricecart);
        ImageView imageCart = convertView.findViewById(R.id.iv_cart);
        Order order = orderArrayList.get(position);
        Button btnValue = convertView.findViewById(R.id.btnvalue);
        Button btnPlus = convertView.findViewById(R.id.btnplus);
        Button btnMinus = convertView.findViewById(R.id.btnminus);
        ImageView imgDelete = convertView.findViewById(R.id.imagedeletecart);

        ProductAPI productAPI = null;
        productAPI = (ProductAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(ProductAPI.class);

        productAPI.getProductByID(cart.getProdId()).enqueue(new Callback<R_ProductCartItem>() {
            @Override
            public void onResponse(Call<R_ProductCartItem> call, Response<R_ProductCartItem> response) {
                Product product = response.body().getData();
                DecimalFormat df = new DecimalFormat("###,###,###");
                priceCart.setText(df.format(product.getPrice()) + " VNĐ");
            }

            @Override
            public void onFailure(Call<R_ProductCartItem> call, Throwable t) {
                Log.d("ERROR: ", t.toString());
            }
        });
        nameCart.setText(cart.getName());
        Picasso.get().load(cart.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(imageCart);
        btnValue.setText(cart.getQuantity() + "");

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(btnValue.getText().toString()) + 1;
                if (slmoi <= 10 && slmoi >= 1) {
                    btnValue.setText(slmoi + "");
                    if (cart.getId() == null) {
                        CartActivity.putCartItem(CartActivity.newIDCart, slmoi);
                    } else {
                        CartActivity.putCartItem(cart.getId(), slmoi);
                    }
                }
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(btnValue.getText().toString()) - 1;
                if (slmoi <= 10 && slmoi >= 1) {
                    btnValue.setText(slmoi + "");
                    if (cart.getId() == null) {
                        CartActivity.putCartItem(CartActivity.newIDCart, slmoi);
                    } else {
                        CartActivity.putCartItem(cart.getId(), slmoi);
                    }
                }
            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartActivity.deleteItem(position);
            }
        });
        return convertView;
    }
}
