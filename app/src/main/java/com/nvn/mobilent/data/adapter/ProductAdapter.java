package com.nvn.mobilent.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nvn.mobilent.R;
import com.nvn.mobilent.screens.product.ProductDetailActivity;
import com.nvn.mobilent.data.model.product.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {

    Context context;
    ArrayList<Product> arrProd;
    ArrayList<Product> arrTmp = new ArrayList<>();


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Khởi tạo lại cái view chúng ta đã thiết kế
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_producthome, null);
        return new ItemHolder(view);
    }

    public ProductAdapter(Context context, ArrayList<Product> arrProd) {
        this.context = context;
        this.arrProd = arrProd;
        this.arrTmp.addAll(arrProd);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Product prod = arrProd.get(position);
        if (prod == null) {
            return;
        }
        holder.tvNameProduct.setText(prod.getName());
        DecimalFormat df = new DecimalFormat("###,###,###");
        holder.tvPriceProduct.setText(df.format(prod.getPrice()) + "VNĐ");
        Picasso.get().load(prod.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(holder.imgProduct); // tra ve imageview

    }

    public void release() {
        context = null;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return arrProd.size();
    }

    public void filter(String text) {
        arrProd.clear();
        text = text.toLowerCase();
        if (text.length() == 0) {
            arrProd.addAll(arrTmp);
        } else {
            for (Product sp : arrTmp) {
                if (sp.getName().toLowerCase().contains(text)) {
                    arrProd.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imgProduct;
        public TextView tvNameProduct, tvPriceProduct;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvNameProduct = itemView.findViewById(R.id.tvnameproduct);
            tvPriceProduct = itemView.findViewById(R.id.tvpriceproduct);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("product", arrProd.get(getAbsoluteAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }

    }

}
