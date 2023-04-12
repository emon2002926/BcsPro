package com.gdalamin.bcs_pro.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ActivityLogin extends AppCompatActivity {


    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView signInTv, signUpTv,fbLogin;
    LinearLayout layoutSignIn, layoutSignUp;
    CardView layoutSignInImage;
    View devider1, devider2;

    EditText phoneEtS, fullNameEtS, passEtS, phoneEtL, passEtL;

    TextView contunueBtnS, contunueBtnL;

    private RequestQueue queue;



    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        signInTv = findViewById(R.id.signInTv);
        signUpTv = findViewById(R.id.signUpTv);

        devider1 = findViewById(R.id.dividerI);
        devider2 = findViewById(R.id.dividerU);
        progressBar = findViewById(R.id.progressBar4);


        layoutSignIn = findViewById(R.id.layoutSignIn);
        layoutSignUp = findViewById(R.id.layoutSignUp);
        layoutSignUp.setVisibility(View.GONE);
        layoutSignIn.setVisibility(View.VISIBLE);

        devider1.setVisibility(View.VISIBLE);
        //to Show  signIn layout
        signInTv.setOnClickListener(view -> {
            layoutSignIn.setVisibility(View.VISIBLE);
            layoutSignUp.setVisibility(View.GONE);

            devider1.setVisibility(View.VISIBLE);
            devider2.setVisibility(View.INVISIBLE);

        });
        //to Show  signUp layout
        signUpTv.setOnClickListener(view -> {
            layoutSignIn.setVisibility(View.GONE);
            layoutSignUp.setVisibility(View.VISIBLE);

            devider1.setVisibility(View.INVISIBLE);
            devider2.setVisibility(View.VISIBLE);
        });


        fullNameEtS = findViewById(R.id.EtName);
        phoneEtS = findViewById(R.id.mobileEtS);
        phoneEtL = findViewById(R.id.EtPhoneL);
        passEtS = findViewById(R.id.EtpassS);
        passEtL = findViewById(R.id.EtPassL);

        contunueBtnS = findViewById(R.id.contunueEtS);
        contunueBtnL = findViewById(R.id.contunueEtL);

        queue = Volley.newRequestQueue(this);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);




        layoutSignInImage = findViewById(R.id.googleSignIN);
        layoutSignInImage.setOnClickListener(view -> {



            signInWithGoogle();
        });


        contunueBtnL.setOnClickListener(view -> {
            String phone = phoneEtL.getText().toString();
            String pass = passEtL.getText().toString();

            if (phone.isEmpty()) {
                phoneEtL.setError("Please enter a Phone Number");
                phoneEtL.requestFocus();
                return;
            } else if (phone.length() != 11) {
                phoneEtL.setError("Please enter a Valid Phone Number");
                phoneEtL.requestFocus();
                return;

            } else if (pass.isEmpty()) {

                passEtL.setError("Please enter the Password");
                phoneEtL.requestFocus();
                return;
            }else {
                loginUser(phone,pass);
            }



        });


        contunueBtnS.setOnClickListener(view -> {

            String name = fullNameEtS.getText().toString();
            String phone = phoneEtS.getText().toString();
            String pass = passEtS.getText().toString();

            if (name.isEmpty()) {
                fullNameEtS.setError("Full name is Required");
                fullNameEtS.requestFocus();
                return;
            } else if (phone.isEmpty()) {
                phoneEtS.setError("Please enter a Phone Number");
                phoneEtS.requestFocus();
                return;
            } else if (phone.length() != 11) {
                phoneEtS.setError("Please enter a Valid Phone Number");
                phoneEtS.requestFocus();
                return;
            } else if (pass.isEmpty()) {
                passEtS.setError("Please enter a Password");
                passEtS.requestFocus();
                return;
            } else {


                checkNumber(phone, name, pass);
            }

        });


    }


    public void loginUser(final String phone, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ApiKeys.API_URL+ "api2/login.php?apiKey=ghi789",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");
                            if (status == 1) {
                                // Login successful, handle success case
                                SharedPreferences sharedPreferences = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("key_phone", phone);
                                editor.commit();
                                navigateToSecondActivity();


                            } else {
                                // Todo Login failed, handle error case
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("loginError",error.toString());
                        // Handle network error
                    }
                }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("phone", phone);
                params.put("password", password);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    //checking the number is registered or not in the database ,Before passing the data into Otp Activity
    private void checkNumber(String number, String name, String password) {
        progressBar.setVisibility(View.VISIBLE);

        String API_URL_WITH_USER_ID = ApiKeys.API_URL+"api2/volley/chackNumber.php?phone=" + number;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL_WITH_USER_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 0) {
                                // Phone number already exists
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(ActivityLogin.this, "Phone number already exists", Toast.LENGTH_SHORT).show();
                            } else if (status == 1) {
                                // Phone number is available
                                progressBar.setVisibility(View.INVISIBLE);


                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                        "+880" + number.toString(), 60, TimeUnit.SECONDS
                                        , ActivityLogin.this,
                                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                            @Override
                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                            }
                                            @Override
                                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                            }
                                            @Override
                                            public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                super.onCodeSent(backendOtp, forceResendingToken);
                                                progressBar.setVisibility(View.INVISIBLE);
                                                navigateToOtpActivity(number, name, password,backendOtp);
                                            }
                                        }
                                );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



 @Keep void signInWithGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
  @Keep  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

    void navigateToSecondActivity() {
        Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void navigateToOtpActivity(String number, String name, String password ,String otp) {
        Intent intent = new Intent(ActivityLogin.this, ActivityOtpLogin.class);
        intent.putExtra("mobile", number);
        intent.putExtra("name", name);
        intent.putExtra("password", password);
        intent.putExtra("otp",otp);
        startActivity(intent);
    }


}