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
import com.nvn.mobilent.data.model.SettingItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SettingAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<SettingItem> arrayList;

    public SettingAdapter(@NonNull Context context, int resource, ArrayList<SettingItem> arrayList) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        SettingItem setting = arrayList.get(position);
        ImageView imageView = convertView.findViewById(R.id.image_setting);
        TextView textView = convertView.findViewById(R.id.tv_datasetting);
        Picasso.get().load(setting.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image1)
                .into(imageView);
        textView.setText(setting.getData());
        return convertView;
    }
}
