package com.gdalamin.bcs_pro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdalamin.bcs_pro.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class singintestActivity extends AppCompatActivity {

//
//    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;
//    TextView name,email;
//    Button signOutBtn;


    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    TextView signInTv,signUpTv;
    LinearLayout layoutSignIn,layoutSignUp,layoutSignInImage;
    View devider1,devider2;

    EditText mobileSignEt;

    TextView contunueBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singintest);

/*
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        signOutBtn = findViewById(R.id.signout);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            name.setText(personName);
            email.setText(personEmail);
        }

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


 */

        signInTv = findViewById(R.id.signInTv);
        signUpTv = findViewById(R.id.signUpTv);

        devider1 = findViewById(R.id.dividerI);
        devider2 = findViewById(R.id.dividerU);


        layoutSignIn = findViewById(R.id.layoutSignIn);
        layoutSignUp = findViewById(R.id.layoutSignUp);
        layoutSignUp.setVisibility(View.GONE);
        layoutSignIn.setVisibility(View.VISIBLE);

        devider1.setVisibility(View.VISIBLE);



        signInTv.setOnClickListener(view -> {
            layoutSignIn.setVisibility(View.VISIBLE);
            layoutSignUp.setVisibility(View.GONE);

            devider1.setVisibility(View.VISIBLE);
            devider2.setVisibility(View.INVISIBLE);

        });
        signUpTv.setOnClickListener(view -> {
            layoutSignIn.setVisibility(View.GONE);
            layoutSignUp.setVisibility(View.VISIBLE);

            devider1.setVisibility(View.INVISIBLE);
            devider2.setVisibility(View.VISIBLE);
        });


        mobileSignEt = findViewById(R.id.mobileEt);

        contunueBtn = findViewById(R.id.contunueEt);
        contunueBtn.setOnClickListener(view -> {


            if (TextUtils.isEmpty(mobileSignEt.getText().toString())){
                Toast.makeText(singintestActivity.this,"plese Inter a number",Toast.LENGTH_SHORT).show();
            }else {
                String number = mobileSignEt.getText().toString().trim();
                Intent intent = new Intent(singintestActivity.this,ActivityOtpLogin.class);
                intent.putExtra("mobile",number);
                startActivity(intent);


            }
        });









        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//        if(acct!=null){
//            navigateToSecondActivity();
//        }

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
        Intent intent = new Intent(singintestActivity.this,MainActivity.class);
        startActivity(intent);
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