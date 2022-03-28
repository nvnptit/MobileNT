package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.nvn.mobilent.R;
import com.nvn.mobilent.base.PathAPI;
import com.nvn.mobilent.base.RetrofitClient;
import com.nvn.mobilent.model.RLogin;
import com.nvn.mobilent.model.User;
import com.nvn.mobilent.network.UserAPI;
import com.nvn.mobilent.util.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText oldPass, newPass, newRePass;
    Button btnChangePass;

    private TextInputLayout textInputLayoutOldPass;
    private TextInputLayout textInputLayoutPass;
    private TextInputLayout textInputLayoutRepass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setControl();
        setEvent();
    }

    private void setEvent() {
        User user = HomeFragment.objectUser;
        UserAPI userAPI;
        userAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(UserAPI.class);

        if (!AppUtils.haveNetworkConnection(getApplicationContext())) {
            AppUtils.showToast_Short(getApplicationContext(), "Kiểm tra lại kết nối Internet");
        } else {
            btnChangePass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtils.showToast_Short(getApplicationContext(), "Vào rồi");
                    if (checkData()) {
                        userAPI.changePassword(user.getEmail(), oldPass.getText().toString().trim(), newPass.getText().toString().trim()).enqueue(new Callback<RLogin>() {
                            @Override
                            public void onResponse(Call<RLogin> call, Response<RLogin> response) {
                                AppUtils.showToast_Short(getApplicationContext(), "Cập nhật mật khẩu thành công!");
                            }

                            @Override
                            public void onFailure(Call<RLogin> call, Throwable t) {
                                AppUtils.showToast_Short(getApplicationContext(), "Lỗi cập nhật mật khẩu rồi!");
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
    }

    private boolean checkData() {
        boolean check = true;
        if (oldPass.getText().toString().trim().equals("")) {
            textInputLayoutOldPass.setError("Không được để trống");
            return false;
        } else {
            textInputLayoutOldPass.setError(null);
        }
        if (newPass.getText().toString().trim().equals("")) {
            textInputLayoutPass.setError("Không được để trống");
            return false;
        } else {
            textInputLayoutPass.setError(null);
        }

        if (newRePass.getText().toString().trim().equals("")) {
            textInputLayoutRepass.setError("Không được để trống");
            return false;
        } else {
            textInputLayoutRepass.setError(null);
        }
        if (!newPass.getText().toString().trim().equals(newRePass.getText().toString().trim())) {
            textInputLayoutRepass.setError("Mật khẩu không trùng khớp");
            return false;
        } else {
            textInputLayoutRepass.setError(null);
        }
        return check;
    }

    private void setControl() {
        oldPass = findViewById(R.id.oldpass);
        newPass = findViewById(R.id.newpass);
        newRePass = findViewById(R.id.renewpass);
        btnChangePass = findViewById(R.id.btnchangepass);

        textInputLayoutOldPass = findViewById(R.id.til_changepass1);
        textInputLayoutPass = findViewById(R.id.til_changepass2);
        textInputLayoutRepass = findViewById(R.id.til_changepass3);
    }

    public void backChangePass(View view) {
        finish();
    }
}