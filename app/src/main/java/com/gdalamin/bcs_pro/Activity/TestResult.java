package com.gdalamin.bcs_pro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gdalamin.bcs_pro.R;

public class TestResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        Intent intent = getIntent();

        String date = intent.getStringExtra("date");

        String total = intent.getStringExtra("total");
        String correct = intent.getStringExtra("correct");
        String wrong = intent.getStringExtra("wrong");
        String marks = intent.getStringExtra("marks");

        String totalIA = intent.getStringExtra("totalIA");
        String correctIA = intent.getStringExtra("correctIA");
        String wrongIA = intent.getStringExtra("wrongIA");
        String marksIA = intent.getStringExtra("marksIA");

        String totalBA = intent.getStringExtra("totalBA");
        String correctBA = intent.getStringExtra("correctBA");
        String wrongBA = intent.getStringExtra("wrongBA");
        String marksBA = intent.getStringExtra("marksBA");

        String totalB = intent.getStringExtra("totalB");
        String correctB = intent.getStringExtra("correctB");
        String wrongB = intent.getStringExtra("wrongB");
        String marksB = intent.getStringExtra("marksB");

        String totalMAV = intent.getStringExtra("totalMAV");
        String correctMAV = intent.getStringExtra("correctMAV");
        String wrongMAV = intent.getStringExtra("wrongMAV");
        String marksMAV = intent.getStringExtra("marksMAV");

        String totalG = intent.getStringExtra("totalG");
        String correctG = intent.getStringExtra("correctG");
        String wrongG = intent.getStringExtra("wrongG");
        String marksG = intent.getStringExtra("marksG");

        String totalML = intent.getStringExtra("totalML");
        String correctML = intent.getStringExtra("correctML");
        String wrongML = intent.getStringExtra("wrongML");
        String marksML = intent.getStringExtra("marksML");

        String totalEL = intent.getStringExtra("totalEL");
        String correctEL = intent.getStringExtra("correctEL");
        String wrongEL = intent.getStringExtra("wrongEL");
        String marksEL = intent.getStringExtra("marksEL");

        String totalMS = intent.getStringExtra("totalMS");
        String correctMS = intent.getStringExtra("correctMS");
        String wrongMS = intent.getStringExtra("wrongMS");
        String marksMS = intent.getStringExtra("marksMS");

        String totalGS = intent.getStringExtra("totalGS");
        String correctGS = intent.getStringExtra("correctGS");
        String wrongGS = intent.getStringExtra("wrongGS");
        String marksGS = intent.getStringExtra("marksGS");

        String totalICT = intent.getStringExtra("totalICT");
        String correctICT = intent.getStringExtra("correctICT");
        String wrongICT = intent.getStringExtra("wrongICT");
        String marksICT = intent.getStringExtra("marksICT");







        Toast.makeText(this, totalICT, Toast.LENGTH_SHORT).show();
    }
}