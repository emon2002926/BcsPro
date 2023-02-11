package com.gdalamin.bcs_pro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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
import com.gdalamin.bcs_pro.adapter.myadapter;
import com.gdalamin.bcs_pro.modelClass.QuestionList;
import com.gdalamin.bcs_pro.modelClass.model;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityExam extends AppCompatActivity {

    private  static final String url="http://192.168.0.104/api2/allQuestion.php";

    private  static final  String saveResultUrl = "http://192.168.0.104/api2/saveResult.php";
    RecyclerView recview;

    TextView textView;
    FloatingActionButton floatingActionButton;

    int correct = 0;
    int wrong = 0;
    int getUserSelectedOption = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);



        recview=(RecyclerView)findViewById(R.id.recview);
        textView = findViewById(R.id.topTv);

        processdata();




//        textView.setText(url2);
        textView.setText("Important Question");

        floatingActionButton = findViewById(R.id.btnSubmit);


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("my_list_action"));



    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            if (intent.getAction().equals("my_list_action")) {
                ArrayList<QuestionList> questionLists = (ArrayList<QuestionList>) intent.getSerializableExtra("my_list_key");

                int totalQc = intent.getIntExtra("totalQuestion",0);
                String totalQuestion = String.valueOf(totalQc);

                // Do something with the list here

                floatingActionButton.setOnClickListener(view -> {

                    SharedPreferences sharedPreferences = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);


                    // gating user num question
                    int answeredQuestions = 0;
                    for (int i = 0; i < questionLists.size(); i++) {
                        if (questionLists.get(i).getUserSelecedAnswer() != 0) {
                            answeredQuestions++;
                        }
                    }

                    //gating correct answer


                    for(int i =0; i < questionLists.size(); i++){

                         getUserSelectedOption = questionLists.get(i).getUserSelecedAnswer();
                         //Get User Selected Option
                        int getQuestionAnswer = questionLists.get(i).getAnswer();

            //             Check UserSelected Answer is correct Answer
                        if (getQuestionAnswer == getUserSelectedOption){
                            correct++;
                        }
                    }
                    for(int i =0; i < questionLists.size(); i++){

                        getUserSelectedOption = questionLists.get(i).getUserSelecedAnswer();
                        //Get User Selected Option
                        int getQuestionAnswer = questionLists.get(i).getAnswer();

                        //             Check UserSelected Answer is correct Answer
                        if (getQuestionAnswer != getUserSelectedOption){
                            wrong++;
                        }
                    }




                    double cutMarks =(double) wrong/2;
                    double mark=(double) correct-cutMarks;


                    String answered = String.valueOf(answeredQuestions);
                    String  correctAnswer = String.valueOf(correct);
                    String userId = sharedPreferences.getString("key_phone", "");
                    String wrongAnswer = String.valueOf(wrong);
                    String totalMark = String.valueOf(mark);






                    Log.d("totalQuestion","answered "+answered+" question of "+totalQuestion+" and the correctAnswer is "+correctAnswer
                            +" and user id is"+userId+" and wrong answer is"+wrong +"your mark is "+mark);





             // for opeaning  submition menu
                    TextView textView1;
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                            ActivityExam.this,R.style.BottomSheetDailogTheme);
                    View bottomSheetView = LayoutInflater.from(ActivityExam.this)
                            .inflate(R.layout.submit_answer,(LinearLayout)
                                    view.findViewById(R.id.bottomSheetContainer));

                    textView1 = bottomSheetView.findViewById(R.id.tvDis);
                    textView1.setText("You have answered "+answered+" Question out of 50");




                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();

                    bottomSheetView.findViewById(R.id.btnSubmit).setOnClickListener(view1 -> {
//                        disable for now
//                  Passing the data to QuizResult Activity


                        saveResult(totalQuestion,correctAnswer,wrongAnswer,totalMark,userId);

                        Intent intent1 = new Intent(ActivityExam.this, ActivityTestResult.class);
                        //Creating Bundle To pass QuestionList
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("qutions",(Serializable) questionLists);
                        intent1.putExtra("answerd",answered);
                        intent1.putExtra("totalQuestion",totalQuestion);
                        intent1.putExtras(bundle);
                        startActivity(intent1);

                    });

                    bottomSheetView.findViewById(R.id.btnCancal).setOnClickListener(view1 -> {
                        bottomSheetDialog.dismiss();
                    });

                });

            }

        }
    };

    public void processdata()
    {


        // Todo got the api url

        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                model data[]=gson.fromJson(response,model[].class);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext()
                        ,LinearLayoutManager.VERTICAL,false);

                recview.setLayoutManager(linearLayoutManager);
                myadapter adapter=new myadapter(data);
                recview.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }
        );


        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    private void saveResult(final String total, final String correct ,final String wrong ,final String mark,final String userId)
    {

        StringRequest request=new StringRequest(Request.Method.POST, saveResultUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                    Toast.makeText(ActivityExam.this,"Sign Up Complete",Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                Log.d("err2",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> param=new HashMap<String,String>();
                param.put("total",total);
                param.put("correct",correct);
                param.put("wrong",wrong);
                param.put("mark",mark);
                param.put("userId",userId);

                return param;
            }
        };


        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }
}