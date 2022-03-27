package com.nvn.mobilent.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.nvn.mobilent.R;
import com.nvn.mobilent.base.PathAPI;
import com.nvn.mobilent.base.RetrofitClient;
import com.nvn.mobilent.model.RLogin;
import com.nvn.mobilent.model.Register;
import com.nvn.mobilent.network.UserAPI;
import com.nvn.mobilent.util.AppUtils;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText firstName, lastName, birthday, address, phone, email, pass, repass;
    RadioButton rb1, rb2;
    Button btnRegister;
    UserAPI userAPI;
    private TextInputLayout textInputLayoutFirstName;
    private TextInputLayout textInputLayoutLastName;
    private TextInputLayout textInputLayoutBirthDay;
    private TextInputLayout textInputLayoutAddress;
    private TextInputLayout textInputLayoutPhone;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutGender;
    private TextInputLayout textInputLayoutPass;
    private TextInputLayout textInputLayoutRepass;

    private String regexName = "[aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆ fFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTu UùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ]+";
    private String regexEmail = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    private String regexDate = "^(3[01]|[12][0-9]|0[1-9]|[1-9])/(1[0-2]|0[1-9]|[1-9])/[0-9]{4}$";
    private String regexPhone = "[0]{1}\\d{9}";

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
        setContentView(R.layout.activity_register);

        setControl();
        setEvent();
        catchData();
    }

    private boolean checkData() {
        boolean check = true;
        if (firstName.getText().toString().trim().equals("")) {
            textInputLayoutFirstName.setError("Không được để trống!");
            return false;
        }
        if (lastName.getText().toString().trim().equals("")) {
            textInputLayoutLastName.setError("Không được để trống!");
            return false;
        }
        if (!rb1.isChecked() && !rb2.isChecked()) {
            textInputLayoutGender.setError("Không được để trống!");
            return false;
        } else {
            textInputLayoutGender.setError(null);
        }
        if (birthday.getText().toString().trim().equals("")) {
            textInputLayoutBirthDay.setError("Không được để trống!");
            return false;
        }
        if (address.getText().toString().trim().equals("")) {
            textInputLayoutAddress.setError("Không được để trống!");
            return false;
        }
        if (phone.getText().toString().trim().equals("")) {
            textInputLayoutPhone.setError("Không được để trống!");
            return false;
        }
        if (email.getText().toString().trim().equals("")) {
            textInputLayoutEmail.setError("Không được để trống!");
            return false;
        }
        if (pass.getText().toString().trim().equals("")) {
            textInputLayoutPass.setError("Không được để trống!");
            return false;
        } else {
            textInputLayoutPass.setError(null);
        }
        if (repass.getText().toString().trim().equals("")) {
            textInputLayoutRepass.setError("Không được để trống!");
            return false;
        } else {
            textInputLayoutRepass.setError(null);
        }
        if (!repass.getText().toString().trim().equals(pass.getText().toString().trim())) {
            textInputLayoutRepass.setError("Mật khẩu không khớp!");
            return false;
        } else {
            textInputLayoutRepass.setError(null);
        }
        return check;
    }

    private void catchBirthDay() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1; // 0-11
                        String date = day + "/" + month + "/" + year;
                        birthday.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        birthday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty() && !charSequence.toString().matches(regexDate)) {
                    textInputLayoutBirthDay.setError("Định dạng dd/MM/yyyy");
                } else {
                    textInputLayoutBirthDay.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void catchData() {
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty() && !charSequence.toString().matches(regexName)) {
                    textInputLayoutFirstName.setError("Chỉ dùng các chữ cái");
                } else {
                    textInputLayoutFirstName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty() && !charSequence.toString().matches(regexName)) {
                    textInputLayoutLastName.setError("Chỉ dùng các chữ cái");
                } else {
                    textInputLayoutLastName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        catchBirthDay();
        // Address chữ và số
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty() && !charSequence.toString().matches(regexPhone)) {
                    textInputLayoutPhone.setError("Số điện thoại gồm 10 số và bắt đầu bằng 0");
                } else {
                    textInputLayoutPhone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
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
    }

    private void setEvent() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData()) {
                    int gender = rb1.isChecked() ? 0 : 1;
                    Register register = new Register(
                            email.getText().toString().trim(),
                            firstName.getText().toString().trim(),
                            lastName.getText().toString().trim(),
                            pass.getText().toString().trim(),
                            address.getText().toString().trim(),
                            phone.getText().toString().trim(),
                            gender,
                            birthday.getText().toString().trim()
                    );
                    userAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(UserAPI.class);
                    if (!AppUtils.haveNetworkConnection(getApplicationContext())) {
                        AppUtils.showToast_Short(getApplicationContext(), "Kiểm tra lại kết nối Internet");
                    } else {
                        userAPI.registerUser(register.getEmail(), register.getFirstname(), register.getLastname(),
                                register.getPassword(), register.getAddress(), register.getPhone(), register.getSex(), register.getBirthday()).enqueue(new Callback<RLogin>() {
                            @Override
                            public void onResponse(Call<RLogin> call, Response<RLogin> response) {
                                System.out.println("DONE");
                                System.out.println(response.body().getData().getFirstname());
                            }

                            @Override
                            public void onFailure(Call<RLogin> call, Throwable t) {

                            }
                        });
                        AppUtils.showToast_Short(getApplicationContext(), "Đăng ký thành công!");
                    }
                }
            }
        });

    }

    private void setControl() {
        firstName = findViewById(R.id.editfisrtname);
        lastName = findViewById(R.id.editlastname);
        birthday = findViewById(R.id.editabirthday);
        address = findViewById(R.id.editaddress);
        phone = findViewById(R.id.editphone);
        email = findViewById(R.id.editTextEmail);
        pass = findViewById(R.id.regpass);
        repass = findViewById(R.id.regrepass);
        rb1 = findViewById(R.id.rbnam);
        rb2 = findViewById(R.id.rbnu);
        btnRegister = findViewById(R.id.btnRegister);

        textInputLayoutFirstName = findViewById(R.id.til_regfname);
        textInputLayoutLastName = findViewById(R.id.til_reglname);
        textInputLayoutBirthDay = findViewById(R.id.til_regbirthday);
        textInputLayoutAddress = findViewById(R.id.til_regaddress);
        textInputLayoutPhone = findViewById(R.id.til_regphone);
        textInputLayoutEmail = findViewById(R.id.til_regemail);
        textInputLayoutGender = findViewById(R.id.til_reggender);
        textInputLayoutPass = findViewById(R.id.til_regpass);
        textInputLayoutRepass = findViewById(R.id.til_regrepass);
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_left, R.anim.stay);
    }
}
