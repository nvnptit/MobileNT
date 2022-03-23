package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.nvn.mobilent.R;
import com.nvn.mobilent.adapter.CartAdapter;

public class CartFragment extends Fragment {
    ListView lvCart;
    TextView tv_NoticeCart, tv_TotalCart;
    Button btnThanhToan, btnMuaHang;

    Toolbar toolbar;
    CartAdapter cartAdapter;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
    }

    private void setControl() {
        lvCart = getView().findViewById(R.id.listviewcart);
        tv_NoticeCart = getView().findViewById(R.id.tv_noticecart);
        tv_TotalCart = getView().findViewById(R.id.totalcart);
        btnThanhToan = getView().findViewById(R.id.btnThanhToan);
        btnMuaHang = getView().findViewById(R.id.btnTTMuaHang);
        toolbar = getView().findViewById(R.id.toolbarcart);
        cartAdapter = new CartAdapter(getContext(), R.layout.fragment_cart, HomeFragment.arrCart);
        lvCart.setAdapter(cartAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }
}