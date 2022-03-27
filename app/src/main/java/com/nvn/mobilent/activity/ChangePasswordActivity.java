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
import com.nvn.mobilent.network.UserAPI;

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
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData()) {
                    UserAPI userAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(UserAPI.class);
//                    userAPI.changePassword()
                }
            }
        });

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
        return check;
    }

    private void setControl() {
        oldPass = findViewById(R.id.oldpass);
        newPass = findViewById(R.id.newpass);
        newRePass = findViewById(R.id.renewpass);

        textInputLayoutOldPass = findViewById(R.id.til_changepass1);
        textInputLayoutPass = findViewById(R.id.til_changepass2);
        textInputLayoutRepass = findViewById(R.id.til_changepass3);
    }

    public void backChangePass(View view) {
        finish();
    }
}