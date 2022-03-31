package com.nvn.mobilent.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.nvn.mobilent.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText email;
    Toolbar toolbar;
    Button btnRecovery;

    private TextInputLayout textInputLayoutEmail;
    private String regexEmail = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setControl();
        setEvent();
    }

    private boolean checkData() {
        boolean check = true;
        if (email.getText().toString().trim().equals("")) {
            textInputLayoutEmail.setError("Không được để trống!");
            return false;
        } else {
            textInputLayoutEmail.setError(null);
        }
        return check;
    }

    private void setEvent() {
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty() && !charSequence.toString().matches(regexEmail)) {
                    textInputLayoutEmail.setError("Email có định dạng username@abc.com");
                } else {
                    textInputLayoutEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        btnRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData()) {
                    //Do something!
                }
            }
        });
    }

    private void setControl() {
        email = findViewById(R.id.recoveryemail);
        toolbar = findViewById(R.id.toolbar_forgot);
        btnRecovery = findViewById(R.id.btnrecovery);
        textInputLayoutEmail = findViewById(R.id.til_recoveremail);
    }
}