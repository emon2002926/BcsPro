package com.gdalamin.bcs_pro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

    private static final String url="http://192.168.0.104/api/db_insert.php";

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


        questionLists = (List<QuestionList>) getIntent().getSerializableExtra("qutions");
        totalScoreTv.setText("/"+"10");


        scoreTv.setText(getCorrectAnswer()+"");
        correctTv.setText(getCorrectAnswer()+"");

//        incorrect.setText(String.valueOf(questionLists.size() - getCorrectAnswer()));

        incorrect.setText(String.valueOf(10 - getCorrectAnswer()));


          score =String.valueOf(getCorrectAnswer());

//
//        int myNum ;
//        myNum = Integer.parseInt(score);
//
//        if (myNum>1){
//
//            Log.d("score",String.valueOf(myNum));
//        }else {}


        insertdata();

    }


    private void insertdata()
    {


        String uname ="";
        String pwd = "";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {


                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                Log.d("err",error.getStackTrace().toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> param=new HashMap<String,String>();

                int num = Integer.parseInt(score);
                if (num>4){

                    param.put("t1",score);

                }
                else {

                    param.put("t2",score);
                }


                param.put("t3",pwd);

                Log.d("score",score);
                return param;
            }
        };


        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }



    private int getCorrectAnswer(){
        int correctAnswer = 0;

        for(int i =0; i < questionLists.size(); i++){

            int getUserSelectedOption = questionLists.get(i).getUserSelecedAnswer();//Get User Selected Option
            int getQuestionAnswer = questionLists.get(i).getAnswer();

            // Check UserSelected Answer is correct Answer
            if (getQuestionAnswer == getUserSelectedOption){
                correctAnswer++;
            }
        }
        return correctAnswer;
    }




}