package com.gdalamin.bcs_pro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.QuestionList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class McqTestActivity extends AppCompatActivity {


    private final List<QuestionList> questionslists = new ArrayList<>();

    private TextView quizTimer;
    private RelativeLayout option1Layout , option2Layout , option3Layout , option4Layout ,layout;
    private TextView option1TV , option2TV , option3TV , option4TV ;
    private ImageView option1Icon , option2Icon , option3Icon , option4Icon ;

    private TextView questionTv;

    private TextView totalQuestionTV ;
    private TextView currentQuestion ;

    CountDownTimer countDownTimer;

    ///Current Question position by Default 0
    private int currenQuestiontPosition = 0;

    //seleced option valu should be be
    private  int selectedOption = 0;
    ProgressBar progressBar;

    static int getAnswer;

    static int getAnswer2;

    static int totalQuestion;

    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_test);


        quizTimer = findViewById (R.id.quizeTimer) ;

        shimmerFrameLayout = findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();
        layout = findViewById(R.id.layout);


        option1Layout = findViewById (R.id.option1Layout ) ;
        option2Layout  = findViewById (R.id.option2Layout ) ;
        option3Layout  = findViewById (R.id.option3Layout ) ;
        option4Layout = findViewById (R.id.opton4Layout ) ;


        option1TV = findViewById(R.id.option1Tv);
        option2TV = findViewById(R.id.option2Tv);
        option3TV = findViewById(R.id.option3Tv);
        option4TV = findViewById(R.id.option4Tv);

        questionTv = findViewById(R.id.questionTv);
        option1Icon = findViewById(R.id.option1Icon);
        option2Icon = findViewById(R.id.option2Icon);
        option3Icon = findViewById(R.id.option3Icon);
        option4Icon = findViewById(R.id.option4Icon);

        totalQuestionTV = findViewById ( R.id.totalQuestionTv ) ;
        currentQuestion = findViewById ( R.id.currentQuestionTv ) ;




        final AppCompatButton nextBtn = findViewById ( R.id.nextQuction ) ;

        totalQuestion = getIntent().getExtras().getInt("selectedOption");

        totalQuestionTV.setText(String.valueOf("/"+totalQuestion));


        //Show Instration Dailog

//
        progressBar =findViewById(R.id.progressBar4);
//        progressBar.setVisibility(View.VISIBLE);
//
//        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);




        option1Layout.setOnClickListener(v -> {

            //asaing  as the first option
            selectedOption = 1;

            selectedOption(option1Layout,option1Icon);
        });


        option2Layout.setOnClickListener(v -> {

            //asaing  as the scaound option
            selectedOption = 2;
            selectedOption(option2Layout,option2Icon);
        });

        option3Layout.setOnClickListener(v -> {

            //asaing  as the third option
            selectedOption = 3;
            selectedOption(option3Layout,option3Icon);
        });

        option4Layout.setOnClickListener(v -> {

            //asaing  as the 4th option
            selectedOption = 4;
            selectedOption(option4Layout,option4Icon);

        });



        nextBtn.setOnClickListener(v -> {

            if(selectedOption !=0){

                questionslists.get(currenQuestiontPosition).setUserSelecedAnswer(selectedOption);

                int i = selectedOption;


                // Showing  User Answer Is Correct Or Not
                if (getAnswer2 == 1){

                    selectedRightOption(option1Layout,option1Icon);

                }
                if (getAnswer2 == 2){

                    selectedRightOption(option2Layout,option2Icon);

                }
                if (getAnswer2 == 3){

                    selectedRightOption(option3Layout,option3Icon);
                }
                if (getAnswer2 == 4){

                    selectedRightOption(option4Layout,option4Icon);
                }


//              for right or worong logic for ui
                if (i==1  ){

                    if (i==getAnswer2){

                        MediaPlayer mp = MediaPlayer.create(McqTestActivity.this,R.raw.correct);
                        mp.start();
//                         selectedRightOption(option1Layout,option1Icon);
                    }
                    else {


                        selectedWrongOption(option1Layout,option1Icon);
                        MediaPlayer mp = MediaPlayer.create(McqTestActivity.this,R.raw.worong);
                        mp.start();
                    }


                }
                if (i==2){

                    if (i==getAnswer2){

                        MediaPlayer mp = MediaPlayer.create(McqTestActivity.this,R.raw.correct);
                        mp.start();
//                            selectedRightOption(option2Layout,option2Icon);
                    }
                    else {
                        selectedWrongOption(option2Layout,option2Icon);
                        MediaPlayer mp = MediaPlayer.create(McqTestActivity.this,R.raw.worong);
                        mp.start();
                    }
                }

                if (i==3){

                    if (i==getAnswer2){

                        MediaPlayer mp = MediaPlayer.create(McqTestActivity.this,R.raw.correct);
                        mp.start();

//                            selectedRightOption(option3Layout,option3Icon);

                    }else {

                        selectedWrongOption(option3Layout,option3Icon);
                        MediaPlayer mp = MediaPlayer.create(McqTestActivity.this,R.raw.worong);
                        mp.start();

                    }
                }

                if (i==4){
                    if (i==getAnswer2){

                        MediaPlayer mp = MediaPlayer.create(McqTestActivity.this,R.raw.correct);
                        mp.start();

//                            selectedRightOption(option4Layout,option4Icon);

                    }else {

                        selectedWrongOption(option4Layout,option4Icon);
                        MediaPlayer mp = MediaPlayer.create(McqTestActivity.this,R.raw.worong);
                        mp.start();

                    }
                }

                selectedOption = 0;
                currenQuestiontPosition++; //gating New Question

                // set  limit for Question


                if (currenQuestiontPosition ==totalQuestion){
//                        countDownTimer.cancel();
                    finishQuiz();
                }



                //Check list has more Question
                new Handler().postDelayed(() -> {
                    nextBtn.setEnabled(true);
                    if (currenQuestiontPosition < questionslists.size()){

                        selectQuestion(currenQuestiontPosition);

                    }else {
                        countDownTimer.cancel();
                        finishQuiz();
                    }

                }, 10);


            }else {
                Toast.makeText(McqTestActivity.this,"please Select A Option",Toast.LENGTH_SHORT).show();
            }

        });


//
        get();
    }


    public  void  get(){

        RequestQueue queue = Volley.newRequestQueue(this);

    // URL to the PHP API
        String url = "http://emon.searchwizy.com/api2/getData.php?apiKey=abc123&apiNum=1";

    // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse the JSON response
                        try {
                            JSONArray data = new JSONArray(response);
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject row = data.getJSONObject(i);
                                String question = row.getString("question");
                                String option1 = row.getString("option1");
                                String option2 = row.getString("option2");
                                String option3 = row.getString("option3");
                                String option4 = row.getString("option4");
                                int answer = row.getInt("answer");

                                startQuizeTimer(180);

                                QuestionList questionList = new QuestionList(question,option1,option2,option3,option4,answer);

                                questionslists.add(questionList);
                                Collections.shuffle(questionslists);
                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);
                                layout.setVisibility(View.VISIBLE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                                // Display the data in a TextView
//                                textView.setText(name + " - " + email + " - " + age);
                            }
                            selectQuestion(currenQuestiontPosition);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


    //////////////////////////
    private void finishQuiz(){

        Intent intent = new Intent(McqTestActivity.this, QuizResult.class);

        //Creating Bundle To pass QuestionList

        Bundle bundle = new Bundle();
        bundle.putSerializable("qutions",(Serializable) questionslists);

        intent.putExtras(bundle);
        startActivity(intent);

        ////Distroy Current Activity
        finish();
    }


    // ToDo method to start QuizeTimer
    private void startQuizeTimer(int maxTimerSceounds){
        countDownTimer = new CountDownTimer(maxTimerSceounds*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {


                long getHour = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                long getMinutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long getSceond = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                String genarateTime = String.format(Locale.getDefault(),"%02d:%02d:%02d",getHour,
                        getMinutes - TimeUnit.HOURS.toMinutes(getHour),
                        getSceond - TimeUnit.MINUTES.toSeconds(getMinutes));

                quizTimer.setText(genarateTime);

            }
            @Override
            public void onFinish() {

                //Finish  quiz when time is Over
                finishQuiz();

            }


        };
        countDownTimer.start();
    }

    private void selectQuestion(int questionListPositon){

        restOption();
//        Collections.shuffle(questionslists);
//        gating Question Ditals and set to Textview

        questionTv.setText(convertToUTF8(questionslists.get(questionListPositon).getQuestion().trim()));
        option1TV.setText(convertToUTF8(questionslists.get(questionListPositon).getOption1().trim()));
        option2TV.setText(convertToUTF8(questionslists.get(questionListPositon).getOption2().trim()));
        option3TV.setText(convertToUTF8(questionslists.get(questionListPositon).getOption3().trim()));
        option4TV.setText(convertToUTF8(questionslists.get(questionListPositon).getOption4().trim()));

        //sating current question By Number

        int i = questionListPositon;

        int questionPositon2 = i+1;

        String myString = Integer.toString(questionPositon2);


        currentQuestion.setText("Question "+questionPositon2);

        getAnswer2 =questionslists.get(questionListPositon).getAnswer();




    }
    private  void restOption () {

//        Collections.shuffle(questionslists);

        option1Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option2Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option3Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option4Layout.setBackgroundResource(R.drawable.round_back_white50_10);

        option1Icon.setImageResource(R.drawable.round_back_white50_100);
        option2Icon.setImageResource(R.drawable.round_back_white50_100);
        option3Icon.setImageResource(R.drawable.round_back_white50_100);
        option4Icon.setImageResource(R.drawable.round_back_white50_100);

    }

    private void selectedOption(RelativeLayout selectedOptionLayout , ImageView selectedOptionIcon) {

        restOption();
        selectedOptionIcon.setImageResource(R.drawable.chack);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_option);

    }

    private void selectedWrongOption(RelativeLayout selectedOptionLayout,ImageView selectedOptionIcon){
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_red50_10);
        selectedOptionIcon.setImageResource(R.drawable.cross);

    }

    private void selectedRightOption(RelativeLayout selectedOptionLayout,ImageView selectedOptionIcon){
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_green50_10);
        selectedOptionIcon.setImageResource(R.drawable.chack);

    }

    private String convertToUTF8(String inputString) {
        try {
            return new String(inputString.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}