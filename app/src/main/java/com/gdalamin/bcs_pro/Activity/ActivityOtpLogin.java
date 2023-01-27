package com.gdalamin.bcs_pro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.gdalamin.bcs_pro.R;

public class ActivityOtpLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("mobile");
            Log.d("number",value);
            //The key argument here must match that used in the other activity
        }

    }
}