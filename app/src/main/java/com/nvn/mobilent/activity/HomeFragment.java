package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.nvn.mobilent.R;
import com.nvn.mobilent.util.CheckConnection;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listViewHome;
    DrawerLayout drawerLayout;


    public HomeFragment() {
        // Required empty public constructor
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
        listViewHome = view.findViewById(R.id.listviewhome);
        drawerLayout = view.findViewById(R.id.drawerlayout);
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
        if (!CheckConnection.haveNetworkConnection(getContext())) {
            CheckConnection.showToast_Short(getContext(), "Kiểm tra lại kết nối Internet");
        } else {
            setControl(view);
            setActionBar();
            setActionViewLipper(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}