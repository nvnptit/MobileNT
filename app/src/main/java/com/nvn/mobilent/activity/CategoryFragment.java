package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nvn.mobilent.R;
import com.nvn.mobilent.adapter.CategoryAdapter;
import com.nvn.mobilent.base.PathAPI;
import com.nvn.mobilent.base.RetrofitClient;
import com.nvn.mobilent.model.Category;
import com.nvn.mobilent.network.CategoryAPI;
import com.nvn.mobilent.util.CheckConnection;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    ListView listViewCategory;
    ArrayList<Category> categoryArrayList;
    CategoryAdapter categoryAdapter;
    CategoryAPI categoryAPI;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            System.out.println("OK");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        return view;
    }

    private void setControl() {
        listViewCategory = getView().findViewById(R.id.listviewcategory);
        categoryArrayList = new ArrayList<>();
        this.categoryAdapter = new CategoryAdapter(getContext(), categoryArrayList);
        this.listViewCategory.setAdapter(categoryAdapter);
    }

    private void getCategory() {
        categoryAPI.getCategory().enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.isSuccessful()) {
                    categoryArrayList = (ArrayList<Category>) response.body();
                    categoryAdapter.updateData(categoryArrayList);//update bản vẽ
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Log.d("NVN-API", t.toString());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        categoryAPI = (CategoryAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(CategoryAPI.class);
        if (!CheckConnection.haveNetworkConnection(getContext())) {
            CheckConnection.showToast_Short(getContext(), "Kiểm tra lại kết nối Internet");
        } else {
            getCategory();
        }
    }


}