package com.nvn.mobilent.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nvn.mobilent.R;
import com.nvn.mobilent.data.model.category.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<Category> arrCate;

    public CategoryAdapter(Context context, ArrayList<Category> arr) {
        this.context = context;
        this.arrCate = arr;
    }

    @Override
    public int getCount() {
        return arrCate.size();
    }

    @Override
    public Category getItem(int i) {
        return arrCate.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.line_category, null);

            viewHolder.tvCategory = view.findViewById(R.id.tvCategory);
            viewHolder.imgCategory = view.findViewById(R.id.imgCategory);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();

        }
        Category category = (Category) getItem(i);
        viewHolder.tvCategory.setText(category.getName());
        Picasso.get().load(category.getImage().trim())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(viewHolder.imgCategory); // tra ve imageview
        return view;
    }

    public class ViewHolder {
        TextView tvCategory;
        ImageView imgCategory;
    }

}