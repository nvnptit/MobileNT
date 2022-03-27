package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.nvn.mobilent.R;
import com.nvn.mobilent.util.AppUtils;

public class InfoCartActivity extends AppCompatActivity {
    EditText txtNguoiNhan, txtSDT, txtDiaChi;
    Button btnDatHang, btnTroVe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_cart);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (!AppUtils.haveNetworkConnection(getApplicationContext())) {
            AppUtils.showToast_Short(getApplicationContext(), "Kiểm tra lại kết nối Internet");
        } else {

            btnDatHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = txtNguoiNhan.getText().toString().trim();
                    String sdt = txtSDT.getText().toString().trim();
                    String diaChi = txtDiaChi.getText().toString().trim();
                    if ((name.length() > 0) && (sdt.length() > 0) && (diaChi.length() > 0)) {

                    } else {
                        AppUtils.showToast_Short(getApplicationContext(), "Kiểm tra lại dữ liệu nhập vào");
                    }
                }
            });
        }
    }

    private void setControl() {
        txtNguoiNhan = findViewById(R.id.txtnguoinhan);
        txtSDT = findViewById(R.id.txtsdtnguoinhan);
        txtDiaChi = findViewById(R.id.txtdiachi);
        btnDatHang = findViewById(R.id.btncheckout);
        btnTroVe = findViewById(R.id.btnbackcart);

    }
}