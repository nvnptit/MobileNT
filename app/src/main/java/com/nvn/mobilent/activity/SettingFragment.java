package com.nvn.mobilent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
    ListView listView;

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
        setEventListView();
        //super.onViewCreated(view, savedInstanceState);
    }

    private void setEventListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setEvent() {
        settingAdapter = new SettingAdapter(getContext(), R.layout.line_settingitem, arrayList);
        listView.setAdapter(settingAdapter);
    }

    private void setControl() {
        listView = getView().findViewById(R.id.lvthongtin);
        arrayList = new ArrayList<>();
        arrayList.add(new SettingItem("https://i.postimg.cc/rFMKg9Sd/ic-contracts.png", "Thông tin cá nhân"));
        arrayList.add(new SettingItem("https://i.postimg.cc/mPpMBPx5/ic-pass1.png", "Đổi mật khẩu"));
        arrayList.add(new SettingItem("https://i.postimg.cc/75Z7FgRz/ic-status.png", "Trạng thái đơn hàng"));
        arrayList.add(new SettingItem("https://i.postimg.cc/CK8V8WrW/ic-ver2.png", "Phiên bản 2022.1"));
        arrayList.add(new SettingItem("https://i.postimg.cc/3dBmfLt7/ic-lienhe.png", "Liên hệ: Nguyễn Văn Nhất"));
    }
}