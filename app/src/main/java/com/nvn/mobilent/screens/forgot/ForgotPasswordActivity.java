package com.nvn.mobilent.screens.forgot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.nvn.mobilent.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnEmail, btnPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
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
        PhoneFragment phoneFragment = new PhoneFragment();
        EmailFragment emailFragment = new EmailFragment();
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(emailFragment);
            }
        });
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(phoneFragment);
            }
        });
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar_forgot);
        btnEmail = findViewById(R.id.btnrecemail);
        btnPhone = findViewById(R.id.btnrecsdt);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container1, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
    }
}