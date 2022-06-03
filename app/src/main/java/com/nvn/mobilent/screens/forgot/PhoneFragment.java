package com.nvn.mobilent.screens.forgot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nvn.mobilent.R;
import com.nvn.mobilent.screens.forgot.VerifyResetPasswordActivity;

import java.util.concurrent.TimeUnit;

public class PhoneFragment extends Fragment {
    EditText edt_phone;
    Button btnRecovery;
    String regexPhone = "0+\\d{9}";
    private TextInputLayout textInputLayoutPhone;
    private FirebaseAuth mAuth;

    public PhoneFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private void setEvent() {
        edt_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty() && !charSequence.toString().matches(regexPhone)) {
                    textInputLayoutPhone.setError("Số điện thoại gồm 10 số");
                } else {
                    textInputLayoutPhone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        btnRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                if (checkData()) {
                    String getPhone = edt_phone.getText().toString().trim();
                    String phone = "+84" + edt_phone.getText().toString().substring(1, getPhone.length());
//                    if (db.checkPhone(phone)) {
//                        loadingDialog.startLoadingDialog();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onClickVerify(phone);
                        }
                    }, 3000);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Số điện thoại không khớp với bất kỳ tài khoản nào", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
    }

    private void onClickVerify(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(getContext(), "Lỗi xác thực", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verifycationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verifycationId, forceResendingToken);
                                System.out.println("Đã gửi OTP");
                                goToVerifyResetPasswordActivity(phone, verifycationId);
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("TAG", "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getActivity(), "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void goToVerifyResetPasswordActivity(String phone, String verifycationId) {
        Intent intent = new Intent(getActivity(), VerifyResetPasswordActivity.class);
        intent.putExtra("phone_number", phone);
        intent.putExtra("verifycationId", verifycationId);
        startActivity(intent);
    }


    private void setControl() {
        edt_phone = getActivity().findViewById(R.id.recoveryphone);
        textInputLayoutPhone = getActivity().findViewById(R.id.til_recoverphone);
        btnRecovery = getActivity().findViewById(R.id.btnsdtrecovery);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }


    private boolean checkData() {
        boolean check = true;
        if (edt_phone.getText().toString().trim().equals("")) {
            textInputLayoutPhone.setError("Không được để trống!");
            return false;
        } else {
            textInputLayoutPhone.setError(null);
        }
        // Check TextInput null
        if (!(textInputLayoutPhone.getError() == null)) {
            return false;
        }
        return check;
    }

}