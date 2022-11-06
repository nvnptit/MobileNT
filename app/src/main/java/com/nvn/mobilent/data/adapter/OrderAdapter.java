package com.nvn.mobilent.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.nvn.mobilent.R;
import com.nvn.mobilent.screens.orders.OrderActivity;
import com.nvn.mobilent.data.model.order.Order;

import java.util.ArrayList;

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
        TextView numberOrder = convertView.findViewById(R.id.tvOrder);
        TextView dateOder = convertView.findViewById(R.id.tvDate);
        TextView person = convertView.findViewById(R.id.tv_person);
        TextView statusOrder = convertView.findViewById(R.id.statusorder);
        TextView cancelOrder = convertView.findViewById(R.id.cancelorder);
        Order order = orderArrayList.get(position);
        numberOrder.setText("Đơn hàng số " + order.getId());
        dateOder.setText("Ngày lập: " + order.getBuyDate());
        person.setText("Người nhận: " + order.getRecipientName() + "\nĐịa chỉ: " + order.getDeliveryAddress());
        if (order.getStatus()==0){
            statusOrder.setText("Chưa xử lý");
            cancelOrder.setVisibility(View.VISIBLE);

        }else if (order.getStatus()==1){
            statusOrder.setText("Đã giao hàng");
            cancelOrder.setVisibility(View.INVISIBLE);
        }else {
            statusOrder.setText("Đơn đã huỷ");
            cancelOrder.setVisibility(View.INVISIBLE);
        }
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusOrder.setText("Đơn đã huỷ");
                OrderActivity.cancelOrder(order.getId());
                cancelOrder.setVisibility(View.INVISIBLE);
            }
        });
        return convertView;
    }
}
