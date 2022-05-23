package com.nvn.mobilent.screens.orders;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.nvn.mobilent.BuildConfig;
import com.nvn.mobilent.R;
import com.nvn.mobilent.data.adapter.OrderItemAdapter;
import com.nvn.mobilent.data.base.PathAPI;
import com.nvn.mobilent.data.base.RetrofitClient;
import com.nvn.mobilent.data.model.order.ListOrderItem;
import com.nvn.mobilent.data.model.order.Order;
import com.nvn.mobilent.data.model.order.ROrderItemDetail;
import com.nvn.mobilent.data.api.OrderAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    Order orderSelected;
    ImageView imageView;
    TextView name, price, amount;
    Toolbar toolbar;
    int idOrder;

    OrderAPI orderAPI;
    ListView listOrderDetail;
    ArrayList<ListOrderItem> orderItemArrayList;
    OrderItemAdapter orderItemAdapter;
    DateFormat dateFormat;

    private void setEvent() {
    }

    private void setControl() {
        orderItemArrayList = new ArrayList<>();
        listOrderDetail = findViewById(R.id.listorderdetail);
        toolbar = findViewById(R.id.toolbar_orderdetail);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //tạo nút home
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getOrderbyOrderId(int idOrder) {
        orderAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(OrderAPI.class);
        orderAPI.getDetailOrderbyIdOrder(idOrder).enqueue(new Callback<ROrderItemDetail>() {
            @Override
            public void onResponse(Call<ROrderItemDetail> call, Response<ROrderItemDetail> response) {
                if (response.isSuccessful()) {
                    for (ListOrderItem loi : response.body().getData()) {
                        orderItemArrayList.add(new ListOrderItem(loi));
                    }
                    System.out.println(orderItemArrayList.size());
                    System.out.println("SIZELiSTORDERITEM: " + orderItemArrayList.size());
                    orderItemAdapter = new OrderItemAdapter(getApplicationContext(), R.layout.line_orderdetail, orderItemArrayList);
                    listOrderDetail.setAdapter(orderItemAdapter);
                    orderItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ROrderItemDetail> call, Throwable t) {

            }
        });
    }

    private int pageWidth = 1200, pageHeight = 2010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        orderSelected = (Order) getIntent().getSerializableExtra("order");
        idOrder = orderSelected.getId();

        System.out.println("IDORDER:" + idOrder);
        setControl();
        actionToolBar();
        setEvent();
        getOrderbyOrderId(idOrder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pdf_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.pdfmenu: {
                setOnPdfEvent();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setOnPdfEvent() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, PackageManager.PERMISSION_GRANTED);

        Date date = new Date();
        orderAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(OrderAPI.class);
        orderAPI.getDetailOrderbyIdOrder(idOrder).enqueue(new Callback<ROrderItemDetail>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ROrderItemDetail> call, Response<ROrderItemDetail> response) {
                if (response.isSuccessful()) {
                    {
                        ArrayList<ListOrderItem> data = response.body().getData();
                        PdfDocument pdfDocument = new PdfDocument();
                        Paint Titlepaint = new Paint();
                        Paint myPaint = new Paint();
                        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
                        PdfDocument.Page page = pdfDocument.startPage((pageInfo));
                        Canvas canvas = page.getCanvas();
                        Titlepaint.setTextSize(60f);
                        Titlepaint.setColor(Color.BLUE);
                        Titlepaint.setTextAlign(Paint.Align.CENTER);
                        canvas.drawText("Danh sách chi tiết đơn hàng " + idOrder, pageWidth / 2, 300, Titlepaint);
                        myPaint.setTextAlign(Paint.Align.LEFT);
                        myPaint.setTextSize(35f);
                        myPaint.setColor(Color.BLACK);
                        canvas.drawText("Người nhận: " + orderSelected.getRecipientName(), 20, 500, myPaint);
                        canvas.drawText("Địa chỉ nhận: " + orderSelected.getDeliveryAddress(), 20, 640, myPaint);
                        myPaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText("Ngày mua hàng: " + orderSelected.getBuyDate(), pageWidth - 20, 500, myPaint);

                        dateFormat = new SimpleDateFormat("dd/MM/yy");
                        canvas.drawText("Ngày in hoá đơn: " + dateFormat.format(date), pageWidth - 20, 640, myPaint);
                        dateFormat = new SimpleDateFormat("HH:mm:ss");
                        canvas.drawText("Thời gian: " + dateFormat.format(date), pageWidth - 20, 690, myPaint);

                        myPaint.setStyle(Paint.Style.STROKE);
                        myPaint.setStrokeWidth(2);
                        canvas.drawRect(20, 780, pageWidth - 20, 860, myPaint);
                        myPaint.setTextAlign(Paint.Align.LEFT);
                        myPaint.setStyle(Paint.Style.FILL);

                        canvas.drawText("Tên sản phẩm", 20, 830, myPaint);
                        canvas.drawText("Đơn giá", 820, 830, myPaint);
                        canvas.drawText("Số lượng", 1000, 830, myPaint);

                        canvas.drawLine(800, 790, 800, 840, myPaint);
                        canvas.drawLine(970, 790, 970, 840, myPaint);
                        int money = 0;
                        int yChuan = 950;
                        int yThem = 100;
                        int rowNumber = -2;

                        for (int i = 0; i < data.size(); i++) {
                            money = money + data.get(i).getPrice() * data.get(i).getQuantity();
                            rowNumber++;
                            canvas.drawText(data.get(i).getName() + "", 20, yChuan, myPaint);
                            canvas.drawText(data.get(i).getPrice() + "", 820, yChuan, myPaint);
                            canvas.drawText(data.get(i).getQuantity() + "", 1100, yChuan, myPaint);
                            yChuan += yThem;
                        }
                        myPaint.setTextAlign(Paint.Align.RIGHT);
                        myPaint.setTextAlign(Paint.Align.LEFT);
                        myPaint.setColor(Color.rgb(247, 147, 30));
                        canvas.drawRect(300, 1350 + rowNumber * 100, pageWidth - 20, 1450 + rowNumber * 100, myPaint);
                        myPaint.setColor(Color.BLACK);
                        myPaint.setTextSize(50f);
                        myPaint.setTextAlign(Paint.Align.LEFT);
                        canvas.drawText("Tổng tiền thanh toán  :", 300, 1415 + rowNumber * 100, myPaint);
                        myPaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(String.valueOf(money), pageWidth - 40, 1415 + rowNumber * 100, myPaint);
                        pdfDocument.finishPage(page);
                        dateFormat = new SimpleDateFormat("dd-MM-yy-HH-mm-ss");
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                                File.separator + "Report" + dateFormat.format(date) + ".pdf");
                        try {
                            pdfDocument.writeTo(new FileOutputStream(file));
                        } catch (Exception e) {
                            Log.d("PDFERROR", e.toString());
                        }
                        pdfDocument.close();
                        Intent target = new Intent(Intent.ACTION_VIEW);
                        target.setDataAndType(FileProvider.getUriForFile(OrderDetailActivity.this, BuildConfig.APPLICATION_ID + ".provider", file), "application/pdf");
                        target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY);


                        Intent intent = Intent.createChooser(target, "Open File");

                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            // Instruct the user to install a PDF reader here, or something
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ROrderItemDetail> call, Throwable t) {

            }
        });


    }

}