package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.nvn.mobilent.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        CategoryFragment categoryFragment = new CategoryFragment();
        CartFragment cartFragment = new CartFragment();
        SettingFragment settingFragment = new SettingFragment();
        HomeFragment homeFragment = new HomeFragment();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    setFragment(homeFragment);
                    return true;
                } else if (id == R.id.nav_category) {
                    setFragment(categoryFragment);
                    return true;
                } else if (id == R.id.nav_cart) {
                    setFragment(cartFragment);
                    return true;
                } else if (id == R.id.nav_setting) {
                    setFragment(settingFragment);
                    return true;
                }
                return false;
            }
        });
//        setControl();
//        setActionBar();
//        setActionViewLipper();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }

    private void setActionViewLipper() {
        ArrayList<String> slidePictures = new ArrayList<>();
        slidePictures.add("https://cdn.tgdd.vn/2022/02/banner/830-300-830x300-20.png");
        slidePictures.add("https://cdn.tgdd.vn/2022/02/banner/reno6z-830-300-830x300.png");
        slidePictures.add("https://cdn.tgdd.vn/2022/03/banner/830-300-830x300-1.png");
        slidePictures.add("https://cdn.tgdd.vn/2022/02/banner/830-300-830x300-19.png");
        for (int i = 0; i < slidePictures.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(slidePictures.get(i)).into(imageView); // Ep anh vao imageview
            imageView.setScaleType(ImageView.ScaleType.FIT_XY); //Fix vua khung anh
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000); // Set time delay slide
        viewFlipper.setAutoStart(true); // Auto

        Animation animation_SlideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_SlideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_SlideIn);
        viewFlipper.setOutAnimation(animation_SlideOut);
    }

    private void setActionBar() {
        setSupportActionBar(toolbar); // hỗ trợ toolbar như actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set nút home toolbar
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START); //nhảy ra giữa
            }
        });

    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        viewFlipper = findViewById(R.id.viewlipper);
        recyclerView = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.navigationview);
        listView = findViewById(R.id.listViewHome);
        drawerLayout = findViewById(R.id.drawerlayout);
    }

}
