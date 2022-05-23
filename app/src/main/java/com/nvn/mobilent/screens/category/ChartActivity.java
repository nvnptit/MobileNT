package com.nvn.mobilent.screens.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.nvn.mobilent.R;
import com.nvn.mobilent.data.base.PathAPI;
import com.nvn.mobilent.data.base.RetrofitClient;
import com.nvn.mobilent.data.model.chart.ChartCategory;
import com.nvn.mobilent.data.model.chart.RChart;
import com.nvn.mobilent.data.api.CategoryAPI;
import com.nvn.mobilent.utils.AppUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartActivity extends AppCompatActivity {

    BarChart barChart;
    PieChart pieChart;
    Toolbar toolbar;
    CategoryAPI categoryAPI;
    ArrayList<ChartCategory> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        setControl();
        actionToolBar();
        if (!AppUtils.haveNetworkConnection(getApplicationContext())) {
            AppUtils.showToast_Short(getApplicationContext(), "Kiểm tra lại kết nối Internet");
        }
        arrayList = new ArrayList<>();
        categoryAPI = (CategoryAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(CategoryAPI.class);
        setEvent();
    }

    private void actionToolBar() {
        toolbar.setTitle("    Biểu đồ sản phẩm");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //tạo nút home
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setEvent() {
        //Initialize array list
        categoryAPI.getChartCategory().enqueue(new Callback<RChart>() {
            @Override
            public void onResponse(Call<RChart> call, Response<RChart> response) {
                if (response.isSuccessful()){
                    arrayList.addAll(response.body().getData());
                    ArrayList<BarEntry> barEntries = new ArrayList<>();
                    ArrayList<PieEntry> pieEntries = new ArrayList<>();
                    int sum= 0;
                    barEntries = new ArrayList<>();
                    pieEntries = new ArrayList<>();
                    for (int i=0;i<arrayList.size();i++){
                        sum+= arrayList.get(i).getSoLuong();
                    }
                    for (int i=0;i<arrayList.size();i++){
                        barEntries.add(new BarEntry(i+1, (arrayList.get(i).getSoLuong() * 100 / sum)));
                        pieEntries.add(new PieEntry(arrayList.get(i).getSoLuong() *100 /sum, arrayList.get(i).getName()));
                    }

                    //Initialize bar data set
                    BarDataSet barDataSet = new BarDataSet(barEntries, "Danh mục sản phẩm");
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    barDataSet.setDrawValues(false); //Hide draw value
                    barChart.setData(new BarData(barDataSet));
                    barChart.animateY(5000); //Set animation
                    barChart.getDescription().setText("");

                    //Initialize pie data set
                    PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieChart.setData(new PieData(pieDataSet));
                    pieChart.animateXY(5000, 5000);
                    pieChart.getDescription().setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<RChart> call, Throwable t) {

            }
        });
    }

    private void setControl() {
        barChart = findViewById(R.id.bar_chart);
        pieChart = findViewById(R.id.pie_chart);
        toolbar = findViewById(R.id.toolbar_chart);
    }
}