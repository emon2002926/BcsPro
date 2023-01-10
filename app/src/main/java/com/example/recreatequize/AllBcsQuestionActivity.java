package com.example.recreatequize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AllBcsQuestionActivity extends AppCompatActivity {

    Button buttonShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bcs_question);

        buttonShare = findViewById(R.id.buttonShow);

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AllBcsQuestionActivity.this
                        ,R.style.BottomSheetDailogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.layout_bottom_sheet,(LinearLayout)findViewById(R.id.bottomSheetContainer));
                bottomSheetView.findViewById(R.id.buttonShare).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(AllBcsQuestionActivity.this, "Share....", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }
}