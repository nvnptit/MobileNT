package com.nvn.mobilent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.nvn.mobilent.R;
import com.nvn.mobilent.adapter.ProductAdapter;
import com.nvn.mobilent.base.PathAPI;
import com.nvn.mobilent.base.RetrofitClient;
import com.nvn.mobilent.model.Cart;
import com.nvn.mobilent.model.Product;
import com.nvn.mobilent.model.RProduct;
import com.nvn.mobilent.network.ProductAPI;
import com.nvn.mobilent.util.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    ProductAPI productAPI;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;

    Button timkiem, btnAddCartHome;
    boolean limitData = false;
    //    ItemClickListener itemClickListener;
    public static ArrayList<Cart> arrCart;

    public HomeFragment() {
    }

    private void setActionViewLipper(View view) {
        ArrayList<String> slidePictures = new ArrayList<>();
        slidePictures.add("https://cdn.tgdd.vn/2022/02/banner/830-300-830x300-20.png");
        slidePictures.add("https://cdn.tgdd.vn/2022/02/banner/reno6z-830-300-830x300.png");
        slidePictures.add("https://cdn.tgdd.vn/2022/03/banner/830-300-830x300-1.png");
        slidePictures.add("https://cdn.tgdd.vn/2022/02/banner/830-300-830x300-19.png");
        for (int i = 0; i < slidePictures.size(); i++) {
            ImageView imageView = new ImageView(view.getContext().getApplicationContext());
            Picasso.get().load(slidePictures.get(i)).into(imageView); // Ep anh vao imageview
            imageView.setScaleType(ImageView.ScaleType.FIT_XY); //Fix vua khung anh
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(4000); // Set time delay slide
        viewFlipper.setAutoStart(true); // Auto

        Animation animation_SlideIn = AnimationUtils.loadAnimation(view.getContext().getApplicationContext(), R.anim.slide_in_right);
        Animation animation_SlideOut = AnimationUtils.loadAnimation(view.getContext().getApplicationContext(), R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_SlideIn);
        viewFlipper.setOutAnimation(animation_SlideOut);
    }

    private void setActionBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar); // hỗ trợ toolbar như actionbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set nút home toolbar
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START); //nhảy ra giữa
        });
    }

    private void setControl(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        viewFlipper = view.findViewById(R.id.viewlipper);
        recyclerView = view.findViewById(R.id.recyclerview);
        navigationView = view.findViewById(R.id.navigationview);
        drawerLayout = view.findViewById(R.id.drawerlayout);
        btnAddCartHome = view.findViewById(R.id.btn_addcarthome);

        // Listview
        productArrayList = new ArrayList<>();
        timkiem = view.findViewById(R.id.timkiem);

        if (arrCart != null) {

        } else {
            arrCart = new ArrayList<>();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            System.out.println("OK");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (!AppUtils.haveNetworkConnection(getContext())) {
            AppUtils.showToast_Short(getContext(), "Kiểm tra lại kết nối Internet");
        } else {
            setControl(view);
            setActionBar();
            setActionViewLipper(view);
        }
        return view;
    }

    private void getProduct() {
        productAPI.getProduct(1, 10).enqueue(new Callback<RProduct>() {
            @Override
            public void onResponse(Call<RProduct> call, Response<RProduct> response) {
                if (response.isSuccessful() && !limitData) {
                    productArrayList = (ArrayList<Product>) response.body().getData();
                    for (int i = 0; i < productArrayList.size(); i++) {
                        if (productArrayList.get(i).getStatus().equals("false")) {
                            productArrayList.remove(i);
                        }
                    }
                    productAdapter = new ProductAdapter(getContext(), productArrayList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    recyclerView.setAdapter(productAdapter);
                    limitData = true;
                }
            }

            @Override
            public void onFailure(Call<RProduct> call, Throwable t) {
                Log.d("NVN-API", t.toString());

            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class); //CategoryFragment.this.getActivity()
                startActivity(intent);
            }
        });

        productAPI = (ProductAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(ProductAPI.class);
        if (!AppUtils.haveNetworkConnection(getContext())) {
            AppUtils.showToast_Short(getContext(), "Kiểm tra lại kết nối Internet");
        } else {
            limitData = false;
            getProduct();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (productAdapter != null) {
            productAdapter.release();
        }
    }
}