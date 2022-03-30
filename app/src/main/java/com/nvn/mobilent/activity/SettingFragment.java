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
import com.nvn.mobilent.datalocal.DataLocalManager;
import com.nvn.mobilent.model.SettingItem;
import com.nvn.mobilent.model.User;

import java.util.ArrayList;

public class SettingFragment extends Fragment {

    ArrayList<SettingItem> arrayList;
    SettingAdapter settingAdapter;
    TextView tv_profile, tv_pass, tv_cartstatus, tv_logout, welcome;
    User user;
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

    @Override
    public void onResume() {
        System.out.println("onResume");
        user = DataLocalManager.getUser();
        welcome.setText(user.getLastname() + " " + user.getFirstname());
        super.onResume();
    }

    private void setEvent() {
        welcome.setText(user.getLastname() + " " + user.getFirstname());
        tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangeInfoActivity.class); //change lại
                //   intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        tv_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                //   intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        tv_cartstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OrderActivity.class);
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
        user = DataLocalManager.getUser();
    }
}
