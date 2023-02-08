package com.gdalamin.bcs_pro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.QuestionList;

import java.util.ArrayList;
import java.util.List;

public class ActivityTestResult extends AppCompatActivity {


    private List<QuestionList> questionLists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);


        questionLists = (List<QuestionList>) getIntent().getSerializableExtra("qutions");


        String answerd = getIntent().getStringExtra("answerd");


        String correctAnswer = String.valueOf(getCorrectAnswer());

        String totalQuestion = getIntent().getStringExtra("totalQuestion");

        Log.d("totalQuestion","answered "+answerd+"question of "+totalQuestion+"and correctAnswer is "+correctAnswer);






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

}