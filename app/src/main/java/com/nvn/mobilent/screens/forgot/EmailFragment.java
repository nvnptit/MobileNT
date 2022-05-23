package com.nvn.mobilent.screens.forgot;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.nvn.mobilent.R;
import com.nvn.mobilent.data.base.PathAPI;
import com.nvn.mobilent.data.base.RetrofitClient;
import com.nvn.mobilent.data.model.user.REmail;
import com.nvn.mobilent.data.api.UserAPI;
import com.nvn.mobilent.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailFragment extends Fragment {
    EditText email;
    Button btnRecovery;
    UserAPI userAPI;
    private TextInputLayout textInputLayoutEmail;
    private String regexEmail = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";

    public EmailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        // Check TextInput null
        if (!(textInputLayoutEmail.getError() == null)) {
            return false;
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
        userAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(UserAPI.class);
        btnRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData()) {
                    userAPI.forgotPassword(email.getText().toString().trim()).enqueue(new Callback<REmail>() {
                        @Override
                        public void onResponse(Call<REmail> call, Response<REmail> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getResult()) {
                                    AppUtils.showToast_Short(getContext(), "Đã gửi mật khẩu mới về email của bạn!");
                                    email.setText("");
                                    getActivity().finish();
                                }
                            } else {
                                AppUtils.showToast_Short(getContext(), "Email không tồn tại trên hệ thống!");
                            }
                        }

                        @Override
                        public void onFailure(Call<REmail> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void setControl() {
        email = getActivity().findViewById(R.id.recoveryemail);
        btnRecovery = getActivity().findViewById(R.id.btnrecovery);
        textInputLayoutEmail = getActivity().findViewById(R.id.til_recoveremail);
    }
}