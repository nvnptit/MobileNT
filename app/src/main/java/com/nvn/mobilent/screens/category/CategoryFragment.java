package com.nvn.mobilent.screens.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nvn.mobilent.R;
import com.nvn.mobilent.screens.cart.CartActivity;
import com.nvn.mobilent.data.adapter.CategoryAdapter;
import com.nvn.mobilent.data.base.PathAPI;
import com.nvn.mobilent.data.base.RetrofitClient;
import com.nvn.mobilent.data.model.category.Category;
import com.nvn.mobilent.data.model.category.RCategory;
import com.nvn.mobilent.data.api.CategoryAPI;
import com.nvn.mobilent.utils.AppUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    ListView listViewCategory;
    public ArrayList<Category> categoryArrayList;
    CategoryAdapter categoryAdapter;
    CategoryAPI categoryAPI;
    ImageView reportCategory;

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


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cart_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cartmenu: {
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void setControl() {
        listViewCategory = getView().findViewById(R.id.listviewcategory);
        reportCategory = getView().findViewById(R.id.reportcategory);
        categoryArrayList = new ArrayList<>();
    }

    private void getCategory() {
        categoryAPI.getCategory().enqueue(new Callback<RCategory>() {
            @Override
            public void onResponse(Call<RCategory> call, Response<RCategory> response) {
                if (response.isSuccessful()) {
                    categoryArrayList = (ArrayList<Category>) response.body().getData();
                    for (int i = 0; i < categoryArrayList.size(); i++) {
                        if (categoryArrayList.get(i).getStatus().equals("false")) {
                            categoryArrayList.remove(i);
                        }
                    }

                    listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            if (!AppUtils.haveNetworkConnection(getContext())) {
                                AppUtils.showToast_Short(getContext(), "Kiểm tra lại kết nối Internet");
                            } else {
                                Intent intent = new Intent(getActivity(), CategoryActivity.class); //CategoryFragment.this.getActivity()
                                intent.putExtra("idCate", categoryArrayList.get(i).getId());
                                intent.putExtra("nameCate", categoryArrayList.get(i).getName());
                                startActivity(intent);
                            }
                        }
                    });
                    categoryAdapter = new CategoryAdapter(getContext(), categoryArrayList);
                    listViewCategory.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<RCategory> call, Throwable t) {
                Log.d("NVN-API", t.toString());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
        categoryAPI = (CategoryAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(CategoryAPI.class);
        if (!AppUtils.haveNetworkConnection(getContext())) {
            AppUtils.showToast_Short(getContext(), "Kiểm tra lại kết nối Internet");
        } else {
            getCategory();
        }
    }

    private void setEvent() {
        reportCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void lisenCategory() {
        listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (!AppUtils.haveNetworkConnection(getContext())) {
                    AppUtils.showToast_Short(getContext(), "Kiểm tra lại kết nối Internet");
                } else {
                    Intent intent = new Intent(getActivity(), CategoryActivity.class); //CategoryFragment.this.getActivity()
                    intent.putExtra("idCate", String.valueOf(categoryAdapter.getItemId(i)));
                    startActivity(intent);
                }
            }
        });
    }


}