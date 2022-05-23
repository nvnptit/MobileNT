package com.nvn.mobilent.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nvn.mobilent.R;
import com.nvn.mobilent.data.model.order.ListOrderItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class OrderItemAdapter extends ArrayAdapter<ListOrderItem> {
    Context context;
    int resource;
    ArrayList<ListOrderItem> orderItemArrayList;

    public OrderItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ListOrderItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.orderItemArrayList = objects;
    }

    @Override
    public int getCount() {
        return orderItemArrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView name = convertView.findViewById(R.id.nameorderdetail);
        TextView price = convertView.findViewById(R.id.priceorderdetail);
        TextView amount = convertView.findViewById(R.id.amountorderdetail);
        ImageView image = convertView.findViewById(R.id.imageorderdetail);
        TextView money = convertView.findViewById(R.id.moneyorderdetail);
        ListOrderItem itemOrder = orderItemArrayList.get(position);
        name.setText(itemOrder.getName());
        price.setText("Đơn giá: " + itemOrder.getPrice() + "");
        amount.setText("Số lượng: " + itemOrder.getQuantity() + "");
        money.setText("Tổng tiền: " + itemOrder.getQuantity() * itemOrder.getPrice() + " VNĐ");
        Picasso.get().load(itemOrder.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image1)
                .into(image);
        return convertView;
    }
}
