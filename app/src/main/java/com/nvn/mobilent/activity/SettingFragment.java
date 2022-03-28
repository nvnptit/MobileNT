package com.nvn.mobilent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nvn.mobilent.R;
import com.nvn.mobilent.adapter.SettingAdapter;
import com.nvn.mobilent.model.SettingItem;

import java.util.ArrayList;

public class SettingFragment extends Fragment {

    ArrayList<SettingItem> arrayList;
    SettingAdapter settingAdapter;
    TextView tv_profile, tv_pass, tv_cartstatus, tv_logout, welcome;

    public SettingFragment() {
    }

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setControl();
        setEvent();
    }


    private void setEvent() {

        welcome.setText(HomeFragment.objectUser.getLastname() + " " + HomeFragment.objectUser.getFirstname());
        tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangeInfoActivity.class); //change lại
                intent.putExtra("user", HomeFragment.objectUser);
                startActivity(intent);
            }
        });
        tv_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        tv_cartstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OrderStatusActivity.class);
                startActivity(intent);
            }
        });
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        tv_profile = getView().findViewById(R.id.profile);
        tv_pass = getView().findViewById(R.id.changepass);
        tv_cartstatus = getView().findViewById(R.id.cartstatus);
        tv_logout = getView().findViewById(R.id.logout);
        welcome = getView().findViewById(R.id.welcome);
    }
}


//        listView = getView().findViewById(R.id.lvthongtin);
//        arrayList = new ArrayList<>();
//        arrayList.add(new SettingItem("https://i.postimg.cc/rFMKg9Sd/ic-contracts.png", "Thông tin cá nhân"));
//        arrayList.add(new SettingItem("https://i.postimg.cc/mPpMBPx5/ic-pass1.png", "Đổi mật khẩu"));
//        arrayList.add(new SettingItem("https://i.postimg.cc/75Z7FgRz/ic-status.png", "Trạng thái đơn hàng"));
//        arrayList.add(new SettingItem("https://i.postimg.cc/CK8V8WrW/ic-ver2.png", "Phiên bản 2022.3"));
//        arrayList.add(new SettingItem("https://i.postimg.cc/Gm6BwCvS/a-signout.png", "Đăng xuất tài khoản"));