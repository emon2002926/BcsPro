package com.gdalamin.bcs_pro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gdalamin.bcs_pro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ActivityOtpLogin extends AppCompatActivity {


    FirebaseAuth auth;
    String verificationId;

    Button btnVerify;
    EditText otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);


        otp = findViewById(R.id.otpEt);
        btnVerify = findViewById(R.id.chackOtp);

        auth = FirebaseAuth.getInstance();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String number = extras.getString("mobile");
            //desabling for testing
            sendVerificationCode(number);
            Log.d("number",number);

            //The key argument here must match that used in the other activity
        }

        btnVerify.setOnClickListener(view -> {

            if (TextUtils.isEmpty(otp.getText().toString())){
                Toast.makeText(ActivityOtpLogin.this,"plese otp a number",Toast.LENGTH_SHORT).show();
            }else {
                verifyCode(otp.getText().toString().trim());
            }

        });


    }


    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+880"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
        {

            final String code = credential.getSmsCode();
            if (code !=null){
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {


            Toast.makeText(ActivityOtpLogin.this,"Verification Failled",Toast.LENGTH_SHORT).show();

            // Show a message and update the UI
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {

            super.onCodeSent(s,token);
            verificationId =s;

        }
    };
    private void verifyCode(String Code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,Code);


        singInByCredential(credential);


    }

    private void singInByCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ActivityOtpLogin.this,"Login Passed",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ActivityOtpLogin.this,ActivityLogin.class));



                }
            }
        });
    }




}