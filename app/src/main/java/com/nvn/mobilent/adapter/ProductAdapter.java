package com.nvn.mobilent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nvn.mobilent.R;
import com.nvn.mobilent.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {

    Context context;
    ArrayList<Product> arrProd;

    public ProductAdapter(Context context, ArrayList<Product> arrProd) {
        this.context = context;
        this.arrProd = arrProd;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Khởi tạo lại cái view chúng ta đã thiết kế
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_producthome, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        // Set/Get thông tin
        Product prod = arrProd.get(position);
        holder.tvNameProduct.setText(prod.getName());
        DecimalFormat df = new DecimalFormat("###,###,###");
        holder.tvPriceProduct.setText(df.format(prod.getPrice()) + "VNĐ");
        Picasso.get().load(prod.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(holder.imgProduct); // tra ve imageview
    }

    @Override
    public int getItemCount() {
        return arrProd.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imgProduct;
        public TextView tvNameProduct, tvPriceProduct;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvNameProduct = itemView.findViewById(R.id.tvnameproduct);
            tvPriceProduct = itemView.findViewById(R.id.tvpriceproduct);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }

}
