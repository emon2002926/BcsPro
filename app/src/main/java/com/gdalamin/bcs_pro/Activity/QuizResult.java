package com.gdalamin.bcs_pro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.QuestionList;

import java.util.ArrayList;
import java.util.List;

public class QuizResult extends AppCompatActivity {


    private List<QuestionList> questionLists = new ArrayList<>();

    static String score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);


        final TextView scoreTv = findViewById(R.id.scoreTv);
        final TextView totalScoreTv = findViewById(R.id.totalScoreTv);
        final TextView correctTv = findViewById(R.id.correctTv);
        final TextView incorrect = findViewById(R.id.incorrectTv);
        final AppCompatButton reTakeBtn = findViewById(R.id.reTakeQuizBtn);

        reTakeBtn.setOnClickListener(view -> {

           onBackPressed();

        });



        questionLists = (List<QuestionList>) getIntent().getSerializableExtra("qutions");
        totalScoreTv.setText("/"+"10");


        correctTv.setText(getCorrectAnswer()+"");


        incorrect.setText(String.valueOf(10 - getCorrectAnswer()));


          score =String.valueOf(getCorrectAnswer());



    }


    private int getCorrectAnswer(){
        int correctAnswer = 0;



        for(int i =0; i < questionLists.size(); i++){

            int getUserSelectedOption = questionLists.get(i).getUserSelecedAnswer();//Get User Selected Option
            int getQuestionAnswer = questionLists.get(i).getAnswer();

//             Check UserSelected Answer is correct Answer
            if (getQuestionAnswer == getUserSelectedOption){
                correctAnswer++;
            }
        }
        return correctAnswer;
    }



//    @Override
//    public void onBackPressed(){
//
//
//        startActivity(new Intent(QuizResult.this,MainActivity.class));
//        finish();
//
//
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(QuizResult.this,MainActivity.class));
        finish();
    }
}