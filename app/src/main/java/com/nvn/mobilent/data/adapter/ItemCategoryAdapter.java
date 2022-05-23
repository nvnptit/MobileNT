package com.nvn.mobilent.data.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nvn.mobilent.R;
import com.nvn.mobilent.data.model.product.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ItemCategoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrProd;

    public ItemCategoryAdapter(Context context, ArrayList<Product> arrProd) {
        this.context = context;
        this.arrProd = arrProd;
    }

    @Override
    public int getCount() {
        return arrProd.size();
    }

    @Override
    public Object getItem(int i) {
        return arrProd.get(i);
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.line_product, null);  // gán layout vào view
            viewHolder.tvNameItem = view.findViewById(R.id.tv_itemname);
            viewHolder.tvPriceItem = view.findViewById(R.id.tv_itemprice);
            viewHolder.tvDetailItem = view.findViewById(R.id.tv_itemdetail);
            viewHolder.imageViewItem = view.findViewById(R.id.img_itemview);
            view.setTag(viewHolder); // set tag view
        } else {
            viewHolder = (ViewHolder) view.getTag(); // set lại ánh xạ
        }
        Product product = (Product) getItem(i);
        viewHolder.tvNameItem.setText(product.getName());
        DecimalFormat df = new DecimalFormat("###,###,###");
        viewHolder.tvPriceItem.setText(df.format(product.getPrice()) + "VNĐ");
        viewHolder.tvDetailItem.setMaxLines(2);
        viewHolder.tvDetailItem.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvDetailItem.setText(product.getDetail());
        Picasso.get().load(product.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(viewHolder.imageViewItem); // tra ve imageview
        return view;
    }

    public class ViewHolder {
        public ImageView imageViewItem;
        public TextView tvNameItem, tvPriceItem, tvDetailItem;
    }
}
