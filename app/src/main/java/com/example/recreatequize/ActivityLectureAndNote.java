package com.example.recreatequize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

public class ActivityLectureAndNote extends AppCompatActivity {

    CardView CvPdfLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_and_note);


        CvPdfLayout = findViewById(R.id.bcsQuestionLayout);

        CvPdfLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),ActivityPdfViwer.class));
            }
        });
    }
}