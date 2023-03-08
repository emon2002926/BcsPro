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

    private  static final String url="http://emon.searchwizy.com/";
    String APIKEY ="api2/getExamMcq.php?apiKey=abc123&apiNum=1&";


    private  static final  String saveResultUrl = "http://emon.searchwizy.com/api2/saveResult.php";
    RecyclerView recview;

    TextView textView,textViewTimer;
    FloatingActionButton floatingActionButton;
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


        recview=findViewById(R.id.recview);
        textView = findViewById(R.id.topTv);
        textViewTimer = findViewById(R.id.tvTimer);
        imageBackButton = findViewById(R.id.backButton);
        shimmerFrameLayout = findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();



        String title = getIntent().getStringExtra("UserSelectedOption");

        textView.setText(title);

        floatingActionButton = findViewById(R.id.btnSubmit);


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("my_list_action"));

        sharedPreferences = getSharedPreferences("totalQuestion", MODE_PRIVATE);


        NUM_OF_QUESTION = sharedPreferences.getInt("examQuestionNum", 0);

        int LOGIC_FOR_ALL_SUBJECT_EXAM = sharedPreferences.getInt("LogicForExam", 0);

        Log.d("LogicForExam",String.valueOf(LOGIC_FOR_ALL_SUBJECT_EXAM));





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

        /*
            Subject and Question Distribution
        Geography (Bangladesh and the World), Environment and Disaster Management = GEDM =10Qu
        International Affairs = IA =20Qu
        Bangladesh Affairs = BA =30Qu
        Bengali language and literature = BLL =35Qu
        Morals, values ​​and good governance = MVG =10Qu
        English Language and Literature = ELL =35Qu
        Mathematical logic = ML =15Qu
        Mental ability = MA = 15
        General science = GS = 15
        Computer and Information Technology = ICT = 15


 */



        if (LOGIC_FOR_ALL_SUBJECT_EXAM != 0) {

            String questionType;
            int time ;
            if (LOGIC_FOR_ALL_SUBJECT_EXAM == 200) {
                time = 200;
                questionType = APIKEY + "numIA=20&numBA=30&numBLL=35&numMVG=10&numGEDM=10&numML=15&numELL=35&numMA=15&numGS=15&numICT=15";
            } else if (LOGIC_FOR_ALL_SUBJECT_EXAM == 100) {
                time = 100;
                questionType = APIKEY + "numIA=10&numBA=15&numBLL=18&numMVG=5&numGEDM=5&numML=7&numELL=17&numMA=8&numGS=7&numICT=8";
            } else if (LOGIC_FOR_ALL_SUBJECT_EXAM == 50) {
                time = 50;
                questionType = APIKEY + "numIA=5&numBA=7&numBLL=9&numMVG=3&numGEDM=3&numML=4&numELL=8&numMA=4&numGS=3&numICT=4";
            }
            else if (LOGIC_FOR_ALL_SUBJECT_EXAM == 2) {

                time = 50;

                Intent intent = getIntent();
                // gating number of question  from user
                String numberOfQuestion = intent.getStringExtra("numOfQuestion");
                String subjectCode =intent.getStringExtra("subjectCode") ;

                questionType = "Test%20Api%27s/holder.php?apiKey=abc123&apiNum=1&"+subjectCode+"="+numberOfQuestion;



            } else {


                return;
            }

            ShowMcq showMcq = new ShowMcq(this, shimmerFrameLayout, recview, floatingActionButton, textViewTimer, time, timerCallback);
            showMcq.processdata( url+questionType);
        }else {

//
//            Intent intent = getIntent();
//            String NUM_OF_QUESTION  = intent.getStringExtra("numOfQuestion");
//
//            // todo have to create url for Subject based exam
//
//            ShowMcq showMcq = new ShowMcq(this, shimmerFrameLayout, recview, floatingActionButton, textViewTimer, 10, timerCallback);
//            showMcq.processdata("http://emon.searchwizy.com/Test%20Api%27s/holder.php?apiKey=abc123&apiNum=1&numIA="+NUM_OF_QUESTION);
//

        }



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

            startActivity(new Intent(ActivityExam.this,MainActivity.class));

            bottomSheetDialog.dismiss();
        });

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




        String examDateTime = String.valueOf(time+" of "+monthName+" "+day+" ");


        //gatting User Id
        SharedPreferences sharedPreferences1;
        sharedPreferences1 = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        String userId = sharedPreferences1.getString("key_phone", "");
        Log.d("userIdd",userId);

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

            sharedPreferences.edit().clear().apply();


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

                Intent intent1 = new Intent(ActivityExam.this, MainActivity.class);
                startActivity(intent1);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
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



}

