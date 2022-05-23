package com.nvn.mobilent;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nvn.mobilent.data.datalocal.DataLocalManager;
import com.nvn.mobilent.screens.category.CategoryFragment;
import com.nvn.mobilent.screens.home.HomeFragment;
import com.nvn.mobilent.screens.settings.SettingFragment;
import com.nvn.mobilent.data.model.user.User;
import com.nvn.mobilent.screens.cart.CartActivity;

public class MainActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataLocalManager.init(getApplicationContext());

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        user = (User) getIntent().getSerializableExtra("user");

        DataLocalManager.setUser(user);

        CategoryFragment categoryFragment = new CategoryFragment();
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
                } else if (id == R.id.nav_setting) {
                    setFragment(settingFragment);
                    return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cartmenu: {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
    }
}
