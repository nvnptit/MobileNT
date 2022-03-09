package com.nvn.mobilent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nvn.mobilent.R;
import com.nvn.mobilent.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    ArrayList<Category> arr;
    Context context;

    public CategoryAdapter(ArrayList<Category> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null; // tuy chinh theo view minh muon
        if (view == null) { // View rong thi nhay vao day
            viewHolder = new ViewHolder();   // Get service la cai layout ra
            // LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.line_category, null); //vẽ

            viewHolder.tvCategory = view.findViewById(R.id.tvCategory);
            viewHolder.imgCategory = view.findViewById(R.id.imgCategory);

            view.setTag(viewHolder); // gan man hinh vao viewholder tuy chinh

        } else {
            viewHolder = (ViewHolder) view.getTag(); // khi da goi truoc do roi thi get cai tag ra thoi

        }
        Category category = (Category) getItem(i);
        viewHolder.tvCategory.setText(category.getName());
        Picasso.get().load(category.getImg())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(viewHolder.imgCategory); // tra ve imageview
        return view;
    }

    public class ViewHolder {
        TextView tvCategory;
        ImageView imgCategory;
    }

    public void notifyDataSetChanged(ArrayList<Category> newList) {
        arr.clear();
        arr.addAll(newList);
        super.notifyDataSetChanged();
    }
}