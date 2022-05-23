package com.nvn.mobilent.screens.forgot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.nvn.mobilent.R;
import com.nvn.mobilent.data.base.PathAPI;
import com.nvn.mobilent.data.base.RetrofitClient;
import com.nvn.mobilent.data.model.user.RLogin;
import com.nvn.mobilent.data.api.UserAPI;
import com.nvn.mobilent.screens.login.LoginActivity;
import com.nvn.mobilent.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPasswordActivity extends AppCompatActivity {

    EditText password, repass;
    String phone;
    Button button;
    TextInputLayout textInputLayoutPass, textInputLayoutRepass;

    UserAPI userAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        setControl();

        phone = getIntent().getStringExtra("phone_number");
        phone = "0" + phone.substring(3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData()) {
                    if (!password.getText().toString().trim().equals(repass.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    userAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(UserAPI.class);
                    userAPI.changePasswordbyPhone(phone.trim(), password.getText().toString().trim()).enqueue(new Callback<RLogin>() {
                        @Override
                        public void onResponse(Call<RLogin> call, Response<RLogin> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getResult()) {
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    AppUtils.showToast_Short(getApplicationContext(), "Khôi phục mật khẩu thành công!");
                                }
                            } else {
                                AppUtils.showToast_Short(getApplicationContext(), "Đã có lỗi xảy ra!");
                            }
                        }

                        @Override
                        public void onFailure(Call<RLogin> call, Throwable t) {
                            AppUtils.showToast_Short(getApplicationContext(), "Đã có lỗi xảy ra!");
                        }
                    });

                }
            }
        });
    }

    private void setControl() {
        password = findViewById(R.id.recnewpass1);
        repass = findViewById(R.id.recnewpass2);
        button = findViewById(R.id.cirContButton);
        textInputLayoutPass = findViewById(R.id.textInputPassword1);
        textInputLayoutRepass = findViewById(R.id.textInputRePass1);
    }

    private boolean checkData() {
        boolean check = true;

        if (password.getText().toString().trim().equals("")) {
            textInputLayoutPass.setError("Không được để trống");
            return false;
        } else {
            textInputLayoutPass.setError(null);
        }

        if (repass.getText().toString().trim().equals("")) {
            textInputLayoutRepass.setError("Không được để trống");
            return false;
        } else {
            textInputLayoutRepass.setError(null);
        }

        if (!(textInputLayoutPass.getError() == null)) {
            return false;
        }
        if (!(textInputLayoutRepass.getError() == null)) {
            return false;
        }
        return check;
    }

}