package com.gdalamin.bcs_pro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.myadapter;
import com.gdalamin.bcs_pro.downloader.ShowMcq;
import com.gdalamin.bcs_pro.fragment.HomeFragment;
import com.gdalamin.bcs_pro.modelClass.QuestionList;
import com.gdalamin.bcs_pro.modelClass.model;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ActivityExam extends AppCompatActivity {

    private  static final String url="http://192.168.0.104/api2/getData.php?apiKey=abc123&apiNum=1";

    private  static final  String saveResultUrl = "http://192.168.0.104/api2/saveResult.php";
    RecyclerView recview;

    TextView textView,textViewTimer;
    FloatingActionButton floatingActionButton;

    CountDownTimer countDownTimer;

     ArrayList<QuestionList> questionLists = new ArrayList<QuestionList>();

    String totalQuestion = "";

    SharedPreferences sharedPreferences;

    int NUM_OF_QUESTION =0;

    ImageView imageBackButton;

    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);


        recview=(RecyclerView)findViewById(R.id.recview);
        textView = findViewById(R.id.topTv);
        textViewTimer = findViewById(R.id.tvTimer);
        imageBackButton = findViewById(R.id.backButton);
        shimmerFrameLayout = findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();




//        processdata();



        String title = getIntent().getStringExtra("UserSelectedOption");

        textView.setText(title);

        floatingActionButton = findViewById(R.id.btnSubmit);


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("my_list_action"));

         sharedPreferences = getSharedPreferences("totalQuestion", MODE_PRIVATE);


         NUM_OF_QUESTION = sharedPreferences.getInt("examQuestionNum", 0);




        imageBackButton.setOnClickListener(view -> {


            onBackPressed();

                });


        ShowMcq.TimerCallback timerCallback = new ShowMcq.TimerCallback() {
            @Override
            public void onTimerFinish() {
                // Handle timer finish event here
                Toast.makeText(ActivityExam.this,"Times Up boys",Toast.LENGTH_SHORT).show();
                finishExam();
            }
        };

        ShowMcq showMcq = new ShowMcq(this, shimmerFrameLayout, recview, floatingActionButton,
                textViewTimer, NUM_OF_QUESTION, timerCallback);
        showMcq.processdata(url);


//        processdata();


    }



    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            if (intent.getAction().equals("my_list_action")) {

                // Get the list of QuestionList objects from the intent
                 questionLists = (ArrayList<QuestionList>) intent.getSerializableExtra("my_list_key");

                // Get the total number of questions from the intent


                 Log.d("timerw",String.valueOf(NUM_OF_QUESTION));


                // Set a click listener for the floating action button
                floatingActionButton.setOnClickListener(view -> {
                    // Initialize counters for answered questions, correct answers, and wrong answers


                    int answeredQuestions = 0;


                    for (QuestionList question : questionLists) {
                        int getUserSelectedOption = question.getUserSelecedAnswer();
                        if (getUserSelectedOption != 0) {
                            answeredQuestions++;
                        }
                    }
                    String answered = String.valueOf(answeredQuestions);

                    //  Show a bottom sheet dialog to allow the user to submit the answers
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ActivityExam.this, R.style.BottomSheetDailogTheme);
                    View bottomSheetView = LayoutInflater.from(ActivityExam.this)
                            .inflate(R.layout.submit_answer, (LinearLayout) bottomSheetDialog.findViewById(R.id.bottomSheetContainer));

                    TextView textView = bottomSheetView.findViewById(R.id.tvDis);
                    textView.setText("You have answered " + answered + " Question out of "+NUM_OF_QUESTION);

                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();

                    bottomSheetView.findViewById(R.id.btnSubmit).setOnClickListener(submitView -> {


                        finishExam();

                    });

                    bottomSheetView.findViewById(R.id.btnCancal).setOnClickListener(cancelView -> {
                        bottomSheetDialog.dismiss();
                    });
                });
            }

        }
    };

    @Override
    public void onBackPressed(){



        int answeredQuestions = 0;


        for (QuestionList question : questionLists) {
            int getUserSelectedOption = question.getUserSelecedAnswer();
            if (getUserSelectedOption != 0) {
                answeredQuestions++;
            }
        }
        String answered = String.valueOf(answeredQuestions);

        //  Show a bottom sheet dialog to allow the user to submit the answers
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ActivityExam.this, R.style.BottomSheetDailogTheme);

        View bottomSheetView = LayoutInflater.from(ActivityExam.this)
                .inflate(R.layout.submit_answer, (LinearLayout) bottomSheetDialog.findViewById(R.id.bottomSheetContainer));

        TextView textView = bottomSheetView.findViewById(R.id.tvDis);
        textView.setText("You have answered " + answered + " Question out of "+NUM_OF_QUESTION);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        bottomSheetView.findViewById(R.id.btnSubmit).setOnClickListener(submitView -> {


            finishExam();

        });

        bottomSheetView.findViewById(R.id.btnCancal).setOnClickListener(cancelView -> {
            //added for testing
            startActivity(new Intent(ActivityExam.this, HomeFragment.class));

            bottomSheetDialog.dismiss();
        });

    }
    public void processdata()
    {
        // Todo got the api url

        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recview.setVisibility(View.VISIBLE);

                startTimer(NUM_OF_QUESTION*30);

                floatingActionButton.setVisibility(View.VISIBLE);

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

    public void  finishExam(){

        //gating date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Note that months start from 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String monthName = new DateFormatSymbols().getMonths()[month];


        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String time = timeFormat.format(calendar.getTime());
        Log.d("formattedTime",time);



        String examDateTime = String.valueOf(time+" of "+monthName+" "+day+" ");

        // Print the date and time

        //gatting User Id
        SharedPreferences sharedPreferences1;
        sharedPreferences1 = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        String userId = sharedPreferences1.getString("key_phone", "");

        if (questionLists !=null){

            int answeredQuestions = 0;
            int correct = 0;
            int wrong = 0;


            for (QuestionList question : questionLists) {

                // Get the correct answer for the current question
                int getQuestionAnswer = question.getAnswer();

                // Get the answer selected by the user for the current question
                int getUserSelectedOption = question.getUserSelecedAnswer();
                // If the user has selected an answer, increment the answeredQuestions counter
                if (getUserSelectedOption != 0) {
                    answeredQuestions++;
                }

                // If the user's selected answer is the same as the correct answer, increment the correct counter
                if (getQuestionAnswer == getUserSelectedOption) {
                    correct++;
                }

                if (getUserSelectedOption !=0 && getQuestionAnswer!=getUserSelectedOption)
                {

                    wrong++;
                }

            }

            /////
            // Calculateing  the marks
            double cutMarks = (double) wrong / 2;
            double mark = (double) correct - cutMarks;

            String answered = String.valueOf(answeredQuestions);
            String correctAnswer = String.valueOf(correct);
            String wrongAnswer = String.valueOf(wrong);
            String totalMark = String.valueOf(mark);

            Log.d("totalQuestion", "answered " + answered + " question of " + totalQuestion + " and the correctAnswer is " + correctAnswer
                    + " and user id is" + userId + " and wrong answer is " + wrongAnswer + " your mark is " + totalMark);


            saveResult(totalQuestion, correctAnswer, wrongAnswer, totalMark, userId,examDateTime);


        }



    }


    private void saveResult(final String total, final String correct ,final String wrong
            ,final String mark,final String userId,String date)
    {
        StringRequest request=new StringRequest(Request.Method.POST, saveResultUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                Toast.makeText(ActivityExam.this,"Result saved",Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(ActivityExam.this, ActivityTestResult.class);
                startActivity(intent1);
                finish();

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
                param.put("date",date);

                return param;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }



    private void startTimer(int maxTimerSceounds){
        countDownTimer = new CountDownTimer(maxTimerSceounds*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {


                long getHour = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                long getMinutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long getSceond = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                String genarateTime = String.format(Locale.getDefault(),"%02d:%02d:%02d",getHour,
                        getMinutes - TimeUnit.HOURS.toMinutes(getHour),
                        getSceond - TimeUnit.MINUTES.toSeconds(getMinutes));

                textViewTimer.setText(genarateTime);

            }

            @Override
            public void onFinish() {


                finishExam();
            }


        };
        countDownTimer.start();
    }

}

