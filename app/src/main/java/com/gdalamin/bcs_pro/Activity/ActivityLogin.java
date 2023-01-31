package com.gdalamin.bcs_pro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gdalamin.bcs_pro.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class ActivityLogin extends AppCompatActivity {




    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    TextView signInTv,signUpTv;
    LinearLayout layoutSignIn,layoutSignUp,layoutSignInImage;
    View devider1,devider2;

    EditText emailEtS,fullNameEt,passEtS;

    TextView contunueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //    Chacking  User is Logged in or not
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            navigateToSecondActivity();
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //User is Logged in
            navigateToSecondActivity();
        }else{
            //No User is Logged in
        }




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


        emailEtS = findViewById(R.id.mobileEtS);
        fullNameEt = findViewById(R.id.EtName);
        passEtS = findViewById(R.id.EtpassS);




        contunueBtn = findViewById(R.id.contunueEt);
        contunueBtn.setOnClickListener(view -> {


//            if (TextUtils.isEmpty(mobileSignEt.getText().toString())){
//                Toast.makeText(ActivityLogin.this,"plese Inter a number",Toast.LENGTH_SHORT).show();
//            }else {
            // that one for firebase Otp
                String number = emailEtS.getText().toString().trim();
                Intent intent = new Intent(ActivityLogin.this,ActivityOtpLogin.class);
                intent.putExtra("mobile",number);
                startActivity(intent);

                String email = emailEtS.getText().toString();
                String name = fullNameEt.getText().toString();
                String pass = passEtS.getText().toString();
                signUp(name,email,pass);

//            }
        });




        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);



        layoutSignInImage = findViewById(R.id.googleSignIN);
        layoutSignInImage.setOnClickListener(view -> {

            signIn();
        });




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


    private void signUp(String username, String email, String password) {
        // URL of the API
        String url = "http://192.168.0.104/api2/volley/signUpLogin.php";
        // RequestQueue for sending the API request
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // JSONObject for storing the user data
        JSONObject request = new JSONObject();
        try {
            request.put("username", username);
            request.put("email", email);
            request.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // JSONObjectRequest to make the API call
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");
                            if (status.equals("success")) {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                // navigate to next screen
                            } else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }




    //    void signOut(){
//        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(Task<Void> task) {
//                finish();
//                startActivity(new Intent(singintestActivity.this,ActivityLogin.class));
//            }
//        });
//    }

}