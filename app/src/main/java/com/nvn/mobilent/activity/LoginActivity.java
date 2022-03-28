package com.nvn.mobilent.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button btnLogin;
    UserAPI userAPI;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        setControl();
        setEvent();
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }

    private void setEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData()) {
                    userAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(UserAPI.class);
                    userAPI.loginUser(email.getText().toString(), password.getText().toString()).enqueue(new Callback<RLogin>() {
                        @Override
                        public void onResponse(Call<RLogin> call, Response<RLogin> response) {
                            if (response.body().getResult()) {
                                AppUtils.showToast_Short(getApplicationContext(), "Đăng nhập thành công!");
                                User user = response.body().getData();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                            } else {
                                AppUtils.showToast_Short(getApplicationContext(), "Tài khoản hoặc mật khẩu không đúng!");
                            }
                        }

                        @Override
                        public void onFailure(Call<RLogin> call, Throwable t) {
                            Log.d("ERROR_VN", t.toString());
                            AppUtils.showToast_Short(getApplicationContext(), "Tài khoản hoặc mật khẩu không đúng!");
                        }
                    });
                }
            }
        });
    }

    private void setControl() {
        email = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpassword);
        btnLogin = findViewById(R.id.btnlogin);
        email.setText("admin@gmail.com");
        password.setText("admin@gmail.com");
        textInputLayoutEmail = findViewById(R.id.til_loginemail);
        textInputLayoutPassword = findViewById(R.id.til_loginpassword);
    }

    private boolean checkData() {
        boolean check = true;
        if (email.getText().toString().trim().equals("")) {
            textInputLayoutEmail.setError("Không được để trống");
            return false;
        } else {
            textInputLayoutEmail.setError(null);
        }
        if (password.getText().toString().trim().equals("")) {
            textInputLayoutPassword.setError("Không được để trống");
            return false;
        } else {
            textInputLayoutPassword.setError(null);
        }
        return check;
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        // overridePendingTransition(R.anim.slide_right,R.anim.stay);
    }

}