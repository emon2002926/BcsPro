package com.gdalamin.bcs_pro.Activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;

public class ActivityLectureAndNote extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imageBackButton;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_and_note);

        recyclerView = findViewById(R.id.recviewLecture);
        shimmerFrameLayout = findViewById(R.id.shimer);

        shimmerFrameLayout.startShimmer();

        imageBackButton = findViewById(R.id.backButton);

        imageBackButton.setOnClickListener(view -> onBackPressed());



    }


}