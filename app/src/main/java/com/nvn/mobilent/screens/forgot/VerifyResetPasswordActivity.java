package com.nvn.mobilent.screens.forgot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nvn.mobilent.R;
import com.nvn.mobilent.screens.login.LoginActivity;

import java.util.concurrent.TimeUnit;

public class VerifyResetPasswordActivity extends AppCompatActivity {

    static final String TAG = VerifyResetPasswordActivity.class.getName();
    EditText digit1, digit2, digit3, digit4, digit5, digit6;
    String mPhoneNumber, mVerifycationId;
    PhoneAuthProvider.ForceResendingToken mForceResendingToken;
    Button button;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_reset_password);
        setControl();
        setEvent();
    }

    private void setEvent() {

        digit1.requestFocus();
        mAuth = FirebaseAuth.getInstance();
        getDataIntent();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verifyCode = digit1.getText().toString().trim();
                onClickSendOtpCode(verifyCode);
            }
        });
    }

    private void setControl() {
        digit1 = findViewById(R.id.digit1Reset);
        button = findViewById(R.id.cirVerifyButtonReset);

    }

    private void getDataIntent() {
        mPhoneNumber = getIntent().getStringExtra("phone_number");
        mVerifycationId = getIntent().getStringExtra("verifycationId");
    }

    private void onClickSendOtpCode(String verifyCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerifycationId, verifyCode);
        signInWithPhoneAuthCredential(credential);
    }

    public void onLoginClick(View view) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)
                        .setForceResendingToken(mForceResendingToken)
                        // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyResetPasswordActivity.this, "Verify Failed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verifycationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verifycationId, forceResendingToken);
                                mVerifycationId = verifycationId;
                                mForceResendingToken = forceResendingToken;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void backLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            gotoChangePasswordActivity(mPhoneNumber);

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(VerifyResetPasswordActivity.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                });
    }

    private void gotoChangePasswordActivity(String phoneNumber) {
        Intent intent = new Intent(this, NewPasswordActivity.class);
        intent.putExtra("phone_number", phoneNumber);
        startActivity(intent);
    }
}