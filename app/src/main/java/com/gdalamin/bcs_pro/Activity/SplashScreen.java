package com.gdalamin.bcs_pro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.gdalamin.bcs_pro.R;


public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
            nextScreen();

    }

    public void nextScreen(){
        new Handler().postDelayed(() -> {

            startActivity(new Intent(SplashScreen.this,MainActivity.class));
            finish();

        }, SPLASH_TIME_OUT);

    }


}