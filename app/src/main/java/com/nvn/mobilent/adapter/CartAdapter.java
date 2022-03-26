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
import com.nvn.mobilent.activity.HomeFragment;
import com.nvn.mobilent.base.PathAPI;
import com.nvn.mobilent.base.RetrofitClient;
import com.nvn.mobilent.model.Cart;
import com.nvn.mobilent.model.Product;
import com.nvn.mobilent.model.R_ProductCartItem;
import com.nvn.mobilent.network.ProductAPI;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends ArrayAdapter<Cart> {
    Context context;
    int resource;
    ArrayList<Cart> cartArrayList;

    public CartAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Cart> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.cartArrayList = objects;
    }

    @Override
    public int getCount() {
        return cartArrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView nameCart = convertView.findViewById(R.id.tv_cart);
        TextView priceCart = convertView.findViewById(R.id.tv_pricecart);
        ImageView imageCart = convertView.findViewById(R.id.iv_cart);
        Cart cart = cartArrayList.get(position);
        System.out.println(cart.toString());
        Button btnValue = convertView.findViewById(R.id.btnvalue);
        Button btnPlus = convertView.findViewById(R.id.btnplus);
        Button btnMinus = convertView.findViewById(R.id.btnminus);

        ProductAPI productAPI = null;
        productAPI = (ProductAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(ProductAPI.class);

        productAPI.getProductByID(cart.getId_prod()).enqueue(new Callback<R_ProductCartItem>() {
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
                    HomeFragment.arrCart.get(position).setQuantity(slmoi);
                    btnValue.setText(slmoi + "");
                    CartActivity.eventTotalPrice();
                }
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(btnValue.getText().toString()) - 1;
                if (slmoi <= 10 && slmoi >= 1) {
                    HomeFragment.arrCart.get(position).setQuantity(slmoi);
                    btnValue.setText(slmoi + "");
                    CartActivity.eventTotalPrice();
                }
            }
        });
        return convertView;
    }
}
