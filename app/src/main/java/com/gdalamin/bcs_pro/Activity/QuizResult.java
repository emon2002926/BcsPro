package com.gdalamin.bcs_pro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.QuestionList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        final AppCompatButton shareBtn = findViewById(R.id.shareBtn);
        final AppCompatButton reTakeBtn = findViewById(R.id.reTakeQuizBtn);
        final TextView textViewCongras = findViewById(R.id.congratulation);
        final TextView textViewgerting = findViewById(R.id.textViewgereting);
        reTakeBtn.setOnClickListener(view -> {
            finish();
            onBackPressed();

        });


        questionLists = (List<QuestionList>) getIntent().getSerializableExtra("qutions");
        totalScoreTv.setText("/"+"10");

        String answerd = getIntent().getStringExtra("answerd");
        scoreTv.setText(String.valueOf(getCorrectAnswer()+""));


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


/*
private  int answeredQuestion (){

    for (int i = 0; i < questionLists.size(); i++) {
        if (questionLists.get(i).getUserSelecedAnswer() != 0) {
            answeredQuestions++;

        }
    }
    return answeredQuestions;

}

 */





}