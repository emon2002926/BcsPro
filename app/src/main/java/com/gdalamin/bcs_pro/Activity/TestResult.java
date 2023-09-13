package com.gdalamin.bcs_pro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gdalamin.bcs_pro.R;

import java.text.NumberFormat;
import java.util.Locale;

public class TestResult extends AppCompatActivity {

    ImageView imageBackButton;

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




        TextView totalTV = findViewById(R.id.totalTv);
        TextView correctTv = findViewById(R.id.correctTv);
        TextView wrongTv = findViewById(R.id.wrongTv);
        TextView marksTv = findViewById(R.id.marksTv);
        setResultIntoTextView(totalTV,correctTv,wrongTv,marksTv,total,correct,wrong,marks);

        TextView totalTVIA = findViewById(R.id.totalTvIA);
        TextView correctTvIA = findViewById(R.id.correctTvIA);
        TextView wrongTvIA = findViewById(R.id.wrongTvIA);
        TextView marksTvIA = findViewById(R.id.marksTvIA);
        setResultIntoTextView(totalTVIA,correctTvIA,wrongTvIA,marksTvIA,totalIA,correctIA,wrongIA,marksIA);

        TextView totalTVBA = findViewById(R.id.totalTvBA);
        TextView correctTvBA =findViewById(R.id.correctTvBA);
        TextView wrongTvBA = findViewById(R.id.wrongTvBA);
        TextView marksTvBA = findViewById(R.id.marksTvBA);
        setResultIntoTextView(totalTVBA,correctTvBA,wrongTvBA,marksTvBA,totalBA,correctBA,wrongBA,marksBA);

        TextView totalTVB = findViewById(R.id.totalTvB);
        TextView correctTvB = findViewById(R.id.correctTvB);
        TextView wrongTvB = findViewById(R.id.wrongTvB);
        TextView marksTvB = findViewById(R.id.marksTvB);
        setResultIntoTextView(totalTVB,correctTvB,wrongTvB,marksTvB,totalB,correctB,wrongB,marksB);

        TextView totalTVMAV = findViewById(R.id.totalTvMAV);
        TextView correctTvMAV = findViewById(R.id.correctTvMAV);
        TextView wrongTvMAV = findViewById(R.id.wrongTvMAV);
        TextView marksTvMAV = findViewById(R.id.marksTvMAV);
        setResultIntoTextView(totalTVMAV,correctTvMAV,wrongTvMAV,marksTvMAV,totalMAV,correctMAV,wrongMAV,marksMAV);

        TextView totalTVG = findViewById(R.id.totalTvG);
        TextView correctTvG = findViewById(R.id.correctTvG);
        TextView wrongTvG = findViewById(R.id.wrongTvG);
        TextView marksTvG = findViewById(R.id.marksTvG);
        setResultIntoTextView(totalTVG,correctTvG,wrongTvG,marksTvG,totalG,correctG,wrongG,marksG);


        TextView totalTVML = findViewById(R.id.totalTvML);
        TextView correctTvML = findViewById(R.id.correctTvML);
        TextView wrongTvML = findViewById(R.id.wrongTvML);
        TextView marksTvML = findViewById(R.id.marksTvML);
        setResultIntoTextView(totalTVML,correctTvML,wrongTvML,marksTvML,totalML,correctML,wrongML,marksML);

        TextView totalTVEL = findViewById(R.id.totalTvEL);
        TextView correctTvEL = findViewById(R.id.correctTvEL);
        TextView wrongTvEL = findViewById(R.id.wrongTvEL);
        TextView marksTvEL = findViewById(R.id.marksTvEL);
        setResultIntoTextView(totalTVEL,correctTvEL,wrongTvEL,marksTvEL,totalEL,correctEL,wrongEL,marksEL);


        TextView totalTVMS = findViewById(R.id.totalTvMS);
        TextView correctTvMS = findViewById(R.id.correctTvMS);
        TextView wrongTvMS = findViewById(R.id.wrongTvMS);
        TextView marksTvMS = findViewById(R.id.marksTvMS);
        setResultIntoTextView(totalTVMS,correctTvMS,wrongTvMS,marksTvMS,totalMS,correctMS,wrongMS,marksMS);

        TextView totalTVGS = findViewById(R.id.totalTvGS);
        TextView correctTvGS = findViewById(R.id.correctTvGS);
        TextView wrongTvGS = findViewById(R.id.wrongTvGS);
        TextView marksTvGS = findViewById(R.id.marksTvGS);
        setResultIntoTextView(totalTVGS,correctTvGS,wrongTvGS,marksTvGS,totalGS,correctGS,wrongGS,marksGS);

        TextView totalTVICT = findViewById(R.id.totalTvICT);
        TextView correctTvICT = findViewById(R.id.correctTvICT);
        TextView wrongTvICT = findViewById(R.id.wrongTvICT);
        TextView marksTvICT = findViewById(R.id.marksTvICT);
        setResultIntoTextView(totalTVICT,correctTvICT,wrongTvICT,marksTvICT,totalICT,correctICT,wrongICT,marksICT);



        imageBackButton = findViewById(R.id.backButton);
        imageBackButton.setOnClickListener(view -> {
            onBackPressed();
        });


        TextView backToHome = findViewById(R.id.btnBackToHome);
        backToHome.setOnClickListener(view -> {
            startActivity(new Intent(TestResult.this,MainActivity.class));
            finish();
        });

    }



    public  void setResultIntoTextView(TextView totalTV,TextView correctTV,TextView wrongTV,TextView marksTV ,String total,
                                       String correct,String wrong,String marks){
        totalTV.setText(convertToBengaliString(total));
        correctTV.setText(convertToBengaliString(correct));
        wrongTV.setText(convertToBengaliString(wrong));
        marksTV.setText(convertToBengaliString(marks));

    }
    public String convertToBengaliString(String numberStr) {
        try {
            double number = Double.parseDouble(numberStr);
            Locale bengaliLocale = new Locale("bn", "BD");
            NumberFormat bengaliNumberFormat = NumberFormat.getNumberInstance(bengaliLocale);
            return bengaliNumberFormat.format(number);
        } catch (NumberFormatException e) {
            e.printStackTrace(); // You can log or handle the error here
            return numberStr; // Return the original string as-is
        }
    }
}