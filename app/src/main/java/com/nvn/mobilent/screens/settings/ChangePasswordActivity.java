package com.nvn.mobilent.screens.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.nvn.mobilent.R;
import com.nvn.mobilent.data.base.PathAPI;
import com.nvn.mobilent.data.base.RetrofitClient;
import com.nvn.mobilent.data.datalocal.DataLocalManager;
import com.nvn.mobilent.data.model.user.RLogin;
import com.nvn.mobilent.data.model.user.User;
import com.nvn.mobilent.data.api.UserAPI;
import com.nvn.mobilent.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText oldPass, newPass, newRePass;
    Button btnChangePass;

    Toolbar toolbar;
    private TextInputLayout textInputLayoutOldPass;
    private TextInputLayout textInputLayoutPass;
    private TextInputLayout textInputLayoutRepass;
    User user;
    UserAPI userAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        user = DataLocalManager.getUser();
        setControl();
        setEvent();
        actionToolBar();
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

    private void setEvent() {
        userAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(UserAPI.class);
        if (!AppUtils.haveNetworkConnection(getApplicationContext())) {
            AppUtils.showToast_Short(getApplicationContext(), "Kiểm tra lại kết nối Internet");
        } else {
            btnChangePass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkData()) {
                        userAPI.changePassword(user.getEmail(), oldPass.getText().toString().trim(), newPass.getText().toString().trim()).enqueue(new Callback<RLogin>() {
                            @Override
                            public void onResponse(Call<RLogin> call, Response<RLogin> response) {
                                if (response.body().getResult()) {
                                    AppUtils.showToast_Short(getApplicationContext(), "Cập nhật mật khẩu thành công!");
                                    finish();
                                }
                            }
                            @Override
                            public void onFailure(Call<RLogin> call, Throwable t) {
                                AppUtils.showToast_Short(getApplicationContext(), "Mật khẩu hiện tại không đúng!");
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

        // Check TextInput null
        if (!(textInputLayoutOldPass.getError() == null)) {
            return false;
        }
        if (!(textInputLayoutPass.getError() == null)) {
            return false;
        }
        if (!(textInputLayoutRepass.getError() == null)) {
            return false;
        }
        return check;
    }

    private void setControl() {
        oldPass = findViewById(R.id.oldpass);
        newPass = findViewById(R.id.newpass);
        newRePass = findViewById(R.id.renewpass);
        btnChangePass = findViewById(R.id.btnchangepass);
        toolbar = findViewById(R.id.toolbar_changepass);

        textInputLayoutOldPass = findViewById(R.id.til_changepass1);
        textInputLayoutPass = findViewById(R.id.til_changepass2);
        textInputLayoutRepass = findViewById(R.id.til_changepass3);
    }


}