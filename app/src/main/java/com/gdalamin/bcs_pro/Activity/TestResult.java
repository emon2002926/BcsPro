package com.gdalamin.bcs_pro.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.ExamResult;

public class TestResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        ExamResult examResult = new ExamResult();
        String marks = examResult.getMark();

        Log.d("marks33",marks);
    }
}