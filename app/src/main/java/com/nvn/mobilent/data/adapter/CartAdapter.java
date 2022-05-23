package com.nvn.mobilent.data.adapter;

import android.content.Context;
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
import com.nvn.mobilent.screens.cart.CartActivity;
import com.nvn.mobilent.data.model.cart.ACart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<ACart> {
    Context context;
    int resource;
    ArrayList<ACart> cartArrayList;

    public CartAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ACart> objects) {
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
        ACart cart = cartArrayList.get(position);
        Button btnValue = convertView.findViewById(R.id.btnvalue);
        Button btnPlus = convertView.findViewById(R.id.btnplus);
        Button btnMinus = convertView.findViewById(R.id.btnminus);
        ImageView imgDelete = convertView.findViewById(R.id.imagedeletecart);

        DecimalFormat df = new DecimalFormat("###,###,###");
        priceCart.setText(df.format(cart.getPrice()) + " VNĐ");
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
                    cart.setQuantity(slmoi);
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
                    cart.setQuantity(slmoi);
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
