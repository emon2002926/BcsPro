package com.gdalamin.bcs_pro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdalamin.bcs_pro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ActivityOtpLogin extends AppCompatActivity {


    FirebaseAuth auth;
    String verificationId;

    TextView btnVerifyOTP;
    EditText inputNumber1,inputNumber2,inputNumber3,inputNumber4,inputNumber5,inputNumber6;

    private static final String url="http://192.168.0.104/api2/volley/signUpLogin.php";

     String number,name,password,firbaseOtp;
     ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);


        btnVerifyOTP = findViewById(R.id.chackOtp);
        inputNumber1 = findViewById(R.id.inputOtp1);
        inputNumber2 = findViewById(R.id.inputOtp2);
        inputNumber3 = findViewById(R.id.inputOtp3);
        inputNumber4 = findViewById(R.id.inputOtp4);
        inputNumber5 = findViewById(R.id.inputOtp5);
        inputNumber6 = findViewById(R.id.inputOtp6);
        progressBar = findViewById(R.id.progressBar4);

        auth = FirebaseAuth.getInstance();

        Bundle extras = getIntent().getExtras();

         number = extras.getString("mobile");
         name = extras.getString("name");
         password = extras.getString("password");
         firbaseOtp = extras.getString("otp");

        Log.d("number",number+name+password);




        btnVerifyOTP.setOnClickListener(view -> {
            if (!inputNumber1.getText().toString().trim().isEmpty() && !inputNumber2.getText().toString().trim().isEmpty() && !inputNumber3.getText().toString().trim().isEmpty()&& !inputNumber4.getText().toString().trim().isEmpty() && !inputNumber5.getText().toString().trim().isEmpty()&& !inputNumber6.getText().toString().trim().isEmpty())
            {
                String userInputOtp = inputNumber1.getText().toString()+
                        inputNumber2.getText().toString()+
                        inputNumber3.getText().toString()+
                        inputNumber4.getText().toString()+
                        inputNumber5.getText().toString()+
                        inputNumber6.getText().toString();
                progressBar.setVisibility(View.VISIBLE);

                if (firbaseOtp !=null){

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            firbaseOtp,userInputOtp);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()){

                                        Toast.makeText(getApplicationContext(),"Otp verified",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                        signUp(name,number,password);

                                    }else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext()," Enter correct Otp",Toast.LENGTH_SHORT).show();

                                    }

                                }

                            });
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ActivityOtpLogin.this,"Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
            else {


            }


        });
        signUp(name,number,password);

        numberOtpMove();


    }


    private void numberOtpMove() {
        // Store all EditTexts in an array for easy looping
        final EditText[] editTexts = {inputNumber1, inputNumber2, inputNumber3, inputNumber4, inputNumber5, inputNumber6};

        // Loop through all EditTexts except the last one
        for (int i = 0; i < editTexts.length; i++) {
            final int currentIndex = i;
            final int nextIndex = i + 1;

            // If there is a next EditText, set a TextWatcher on the current EditText
            if (nextIndex < editTexts.length) {
                editTexts[currentIndex].addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // If the input is not empty, move the focus to the next EditText
                        if (s.toString().trim().length() == 1) {
                            editTexts[nextIndex].requestFocus();
                        }
                    }
                    // Unused methods from TextWatcher
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                    @Override
                    public void afterTextChanged(Editable s) { }
                });
            }
        }
    }


    private void signUp(final String name, final String phone ,final String pwd ) {
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toString().equals("exists")) {
                    Toast.makeText(ActivityOtpLogin.this, "This Number Already Exists", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityOtpLogin.this, "Sign Up Complete", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ActivityOtpLogin.this, ActivityLogin.class));
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.d("err2", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("name", name);
                params.put("phone", phone);
                params.put("password", pwd);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }




//    private void signUp(final String name, final String phone ,final String pwd )
//    {
//        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response)
//            {
//
//
//                if (response.toString().equals("exists")){
//
//                    Toast.makeText(ActivityOtpLogin.this,"This Number Already Exists",Toast.LENGTH_SHORT).show();
//
//                }else {
//
//                    Toast.makeText(ActivityOtpLogin.this,"Sign Up Complete",Toast.LENGTH_SHORT).show();
//
//                    startActivity(new Intent(ActivityOtpLogin.this,ActivityLogin.class));
//                    finish();
//
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
//                Log.d("err2",error.toString());
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError
//            {
//                Map<String,String> param=new HashMap<String,String>();
//                param.put("name",name);
//                param.put("phone",phone);
//                param.put("password",pwd);
//                return param;
//            }
//        };
//
//
//        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
//        queue.add(request);
//
//    }
}