package com.gdalamin.bcs_pro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdalamin.bcs_pro.LoginRequest;
import com.gdalamin.bcs_pro.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity {




    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    TextView signInTv,signUpTv;
    LinearLayout layoutSignIn,layoutSignUp;
    CardView layoutSignInImage;
    View devider1,devider2;

    EditText phoneEtS,fullNameEtS,passEtS,phoneEtL,passEtL;

    TextView contunueBtnS,contunueBtnL;

    private RequestQueue queue;

    private static final String url="http://192.168.0.104/api2/volley/signUpLogin.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        signInTv = findViewById(R.id.signInTv);
        signUpTv = findViewById(R.id.signUpTv);

        devider1 = findViewById(R.id.dividerI);
        devider2 = findViewById(R.id.dividerU);


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
        gsc = GoogleSignIn.getClient(this,gso);


        //    Chacking  User is Logged in or not
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
//            navigateToSecondActivity();
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //User is Logged in
//            navigateToSecondActivity();
        }else{
            //No User is Logged in
        }

        layoutSignInImage = findViewById(R.id.googleSignIN);
        layoutSignInImage.setOnClickListener(view -> {

            signIn();
        });


        contunueBtnL.setOnClickListener(view -> {
            String phone = phoneEtL.getText().toString();
            String pass = passEtL.getText().toString();


            login(phone,pass);
            

        });


        contunueBtnS.setOnClickListener(view -> {

                String name = fullNameEtS.getText().toString();
                String phone = phoneEtS.getText().toString();
                String pass = passEtS.getText().toString();

                if (name.isEmpty()){
                    fullNameEtS.setError("Full name is Required");
                    fullNameEtS.requestFocus();
                    return;
                }
                else if (phone.isEmpty()){
                    phoneEtS.setError("Please enter a Phone Number");
                    phoneEtS.requestFocus();
                    return;
                }else if (phone.toString().length()!=4){
                    phoneEtS.setError("Please enter a Valid Phone Number");
                    phoneEtS.requestFocus();
                    return;
                }
                else if (pass.isEmpty())
                {
                    passEtS.setError("Please enter a Password");
                    passEtS.requestFocus();
                    return;
                }else {
//                    signUp(name,phone,pass);
                    checkNumber(phone,name,pass);

                }

        });

    }

    public void login (String phone, String password){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    if (status.equals("success")) {
                        // Login successful
                        navigateToSecondActivity();
                        // Redirect to home screen or show a message
                    } else {
                        // Login failed
                        // Show an error message
                        Toast.makeText(ActivityLogin.this,"Login failed",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(phone, password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ActivityLogin.this);
        queue.add(loginRequest);

    }



    private void checkNumber(String number,String name,String password){

        String API_URL = "http://192.168.0.104/api2/volley/chackNumber.php?phone=" + number;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 0) {
                                // Phone number already exists
                                Toast.makeText(ActivityLogin.this,"Phone number already exists",Toast.LENGTH_SHORT).show();
                            } else if (status == 1) {
                                // Phone number is available
                                navigateToOtpActivity(number,name,password);


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






    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(ActivityLogin.this,MainActivity.class);
        startActivity(intent);
    }



    void navigateToOtpActivity(String number,String name,String password){
        Intent intent = new Intent(ActivityLogin.this,ActivityOtpLogin.class);
        intent.putExtra("mobile",number);
        intent.putExtra("name",name);
        intent.putExtra("password",password);
        startActivity(intent);
    }


}