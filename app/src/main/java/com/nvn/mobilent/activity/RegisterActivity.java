package com.nvn.mobilent.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.nvn.mobilent.R;

public class RegisterActivity extends AppCompatActivity {
    EditText firstName, lastName, email, address, phone, pass, repass, birthday;
    RadioButton rb1, rb2;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_register);

        setControl();
        setEvent();

    }

    private void setEvent() {

    }

    private void setControl() {
        firstName = findViewById(R.id.editfisrtname);
        lastName = findViewById(R.id.editlastname);
        email = findViewById(R.id.editTextEmail);
        address = findViewById(R.id.editaddress);
        phone = findViewById(R.id.editphone);
        birthday = findViewById(R.id.editabirthday);
        pass = findViewById(R.id.regpass);
        repass = findViewById(R.id.regrepass);
        rb1 = findViewById(R.id.rbnam);
        rb2 = findViewById(R.id.rbnu);
        btnRegister = findViewById(R.id.btnRegister);
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_left, R.anim.stay);
    }
}