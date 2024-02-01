package com.gdalamin.bcs_pro.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.McqLoaderAdapter;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.api.ResultPref;
import com.gdalamin.bcs_pro.api.SharedPreferencesManagerAppLogic;
import com.gdalamin.bcs_pro.modelClass.QuestionList;
import com.gdalamin.bcs_pro.modelClass.model;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ActivityExam extends AppCompatActivity {

    private static final String APIKEY ="api/getExamMcq.php?apiKey=abc123&apiNum=1&";
    String API_URL= ApiKeys.API_URL;
    RecyclerView recView;
    TextView titleView,textViewTimer;
    FloatingActionButton floatingActionButton;
    ArrayList<QuestionList> questionLists = new ArrayList<>();
    int NUM_OF_QUESTION =0;
    ImageView imageBackButton;
    ShimmerFrameLayout shimmerFrameLayout;
    SharedPreferencesManagerAppLogic preferencesManager;
    ResultPref resultPref;

    private boolean mBooleanValue = true;
    private CountDownTimer countDownTimer;
    public static int REQ_CODE2 = 0;
    private BottomSheetDialog bottomSheetDialog;
    private View bottomSheetView;
    private static String subjectName;
    private TimerCallback timerCallback;
    int examTime;
    LinearLayout tryAgainLayout;
    TextView retryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        recView=findViewById(R.id.recview);
        titleView = findViewById(R.id.topTv);
        textViewTimer = findViewById(R.id.tvTimer);
        imageBackButton = findViewById(R.id.backButton);
        shimmerFrameLayout = findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        tryAgainLayout = findViewById(R.id.tryAgainLayout);
        retryBtn = findViewById(R.id.retryBtn);

        subjectName = getIntent().getStringExtra("titleText");

        titleView.setText(subjectName);

        floatingActionButton = findViewById(R.id.btnSubmit);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("xy@4gfk@9*2cxlds&0k@#hLAnsx!"));

        preferencesManager = new SharedPreferencesManagerAppLogic(this);
        resultPref = new ResultPref(this);

        NUM_OF_QUESTION = preferencesManager.getInt("examQuestionNum");

        int LOGIC_FOR_ALL_SUBJECT_EXAM = preferencesManager.getInt("LogicForExam");
        examTime = preferencesManager.getInt("time");

        imageBackButton.setOnClickListener(view -> onBackPressed());

        if (LOGIC_FOR_ALL_SUBJECT_EXAM != 0) {

            String questionType;
            if (LOGIC_FOR_ALL_SUBJECT_EXAM == 200) {

                questionType =API_URL+APIKEY + "numIA=20&numBA=30&numBLL=35&numMVG=10&numGEDM=10&numML=15&numELL=35&numMA=15&numGS=15&numICT=15";
            }
            else if (LOGIC_FOR_ALL_SUBJECT_EXAM == 100) {

                questionType = API_URL+APIKEY + "numIA=10&numBA=15&numBLL=18&numMVG=5&numGEDM=5&numML=7&numELL=17&numMA=8&numGS=7&numICT=8";
            }
            else if (LOGIC_FOR_ALL_SUBJECT_EXAM == 50) {
                questionType = API_URL+APIKEY + "numIA=5&numBA=7&numBLL=9&numMVG=3&numGEDM=3&numML=4&numELL=8&numMA=4&numGS=3&numICT=4";
            }
            else if (LOGIC_FOR_ALL_SUBJECT_EXAM == 2) {
                NUM_OF_QUESTION= (preferencesManager.getInt("time"))*2;

                 int questionNumber = preferencesManager.getInt("examQuestionNum");
                questionType = API_URL+"api/getSubjectBasedExam.php?apiKey=abc123&apiNum=2&IA="+questionNumber;

            }
            else {

                return;
           }
            getExamQuestions( questionType);
//            retryBtn.setOnClickListener(view -> );
            retryBtn.setOnClickListener(v -> {
//                shimmerFrameLayout.setVisibility(View.VISIBLE);
                getExamQuestions(questionType);
            });
        }
    }

    public void getExamQuestions(String url ) {
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    REQ_CODE2 = 0;
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    recView.setVisibility(View.VISIBLE);
                    floatingActionButton.setVisibility(View.VISIBLE);
                    tryAgainLayout.setVisibility(View.GONE);
                    textViewTimer.setVisibility(View.VISIBLE);
                    startTimer(NUM_OF_QUESTION * 30, textViewTimer);

                    GsonBuilder builder = new GsonBuilder().setLenient();
                    Gson gson = builder.create();
                    JsonReader reader = new JsonReader(new StringReader(response));
                    reader.setLenient(true);
                    model[] data = gson.fromJson(reader, model[].class);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityExam.this,
                            LinearLayoutManager.VERTICAL, false);

                    recView.setLayoutManager(linearLayoutManager);
                    McqLoaderAdapter adapter = new McqLoaderAdapter(data);
                    recView.setAdapter(adapter);

                    timerCallback = () ->{

                        REQ_CODE2 = 2;
                        mBooleanValue = !mBooleanValue;
                        adapter.setBooleanValue(true);
                        LinearLayout recViewBackground = findViewById(R.id.recviewBackground);
                        recViewBackground.setBackgroundResource(R.color.recviewBagColor);
                        floatingActionButton.setImageResource(R.drawable.baseline_keyboard_double_arrow_up_24);

                        finishExam();

                    };

                    floatingActionButton.setOnClickListener(view -> {
                        // Initialize counters for answered questions,
                        int answeredQuestions = 0;
                        for (QuestionList question : questionLists) {
                            int getUserSelectedOption = question.getUserSelecedAnswer();
                            if (getUserSelectedOption != 0) {
                                answeredQuestions++;
                            }
                        }
                        String answered = String.valueOf(answeredQuestions);
                        if (REQ_CODE2 == 0){
                            showSubmissionOption(answered, () -> {
                                mBooleanValue = !mBooleanValue;
                                adapter.setBooleanValue(true);
                                LinearLayout recViewBackground = findViewById(R.id.recviewBackground);
                                recViewBackground.setBackgroundResource(R.color.recviewBagColor);
                                floatingActionButton.setImageResource(R.drawable.baseline_keyboard_double_arrow_up_24);

                            });

                        }
                        else if (REQ_CODE2 ==2){

                            if (bottomSheetDialog.isShowing()) {
                                bottomSheetDialog.dismiss();

                            } else {
                                // If not showing, show it to expand
                                bottomSheetDialog.setContentView(bottomSheetView);
                                bottomSheetDialog.show();
                            }}
                    });
                },
                error ->
                {
//                    tryAgainLayout.setVisibility(View.VISIBLE);

//                    tryAgainLayout.setVisibility(View.VISIBLE);
                    delayTryAgainLayout();
                }
        );
        RequestQueue queue = Volley.newRequestQueue(ActivityExam.this);
        queue.add(request);
    }
    public void delayTryAgainLayout(){
        REQ_CODE2 = 2;
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        tryAgainLayout.setVisibility(View.GONE);
        new Handler().postDelayed(() -> {
//            tryAgainLayout.setVisibility(View.VISIBLE);
            tryAgainLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }, 5000);
    }
    private void startTimer(int maxTimerSeconds, TextView textViewTimer) {
        countDownTimer = new CountDownTimer(maxTimerSeconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long getHour = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                long getMinutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long getSecond = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                String generateTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", getHour,
                        getMinutes - TimeUnit.HOURS.toMinutes(getHour),
                        getSecond - TimeUnit.MINUTES.toSeconds(getMinutes));
                textViewTimer.setText(convertToBengaliString(generateTime));
            }
            @Override
            public void onFinish() {
                if (timerCallback != null) {
                    timerCallback.onTimerFinish();
                }
            }
        };
        countDownTimer.start();
    }
    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            textViewTimer.setVisibility(View.GONE);
        }
    }
    public interface TimerCallback {
        void onTimerFinish();
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            if (Objects.equals(intent.getAction(), "xy@4gfk@9*2cxlds&0k@#hLAnsx!")) {
                if (intent.hasExtra("xy@4gfk@9*2cxlds&0k@#hLAnsx!")) {
                    ArrayList<QuestionList> receivedList = (ArrayList<QuestionList>) intent.getSerializableExtra("xy@4gfk@9*2cxlds&0k@#hLAnsx!");

                    if (receivedList != null) {
                        questionLists = receivedList;
                        // Process the received data here
                        // For example: update UI with the new data
                    }
                }
            }


        }};

    public void  finishExam(){
        //gating date
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH); // Note that months start from 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String monthName = new DateFormatSymbols().getMonths()[month];
        preferencesManager = new SharedPreferencesManagerAppLogic(this);
        int LOGIC_FOR_ALL_SUBJECT_EXAM = preferencesManager.getInt("LogicForExam");
//        resultPref.saveInt("totalQuestions1",LOGIC_FOR_ALL_SUBJECT_EXAM);

        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String time = timeFormat.format(calendar.getTime());
        String examDateTime = time+" of "+monthName+" "+day+" ";


        bottomSheetDialog = new BottomSheetDialog(ActivityExam.this, R.style.BottomSheetDailogTheme);
        bottomSheetView = LayoutInflater.from(ActivityExam.this).inflate(R.layout.bottom_sheet_result_view, bottomSheetDialog.findViewById(R.id.bottomSheetContainer));

        TextView totalTV = bottomSheetView.findViewById(R.id.totalTv);
        TextView correctTv = bottomSheetView.findViewById(R.id.correctTv);
        TextView wrongTv = bottomSheetView.findViewById(R.id.wrongTv);
        TextView marksTv = bottomSheetView.findViewById(R.id.marksTv);

        TextView totalTVIA = bottomSheetView.findViewById(R.id.totalTvIA);
        TextView correctTvIA = bottomSheetView.findViewById(R.id.correctTvIA);
        TextView wrongTvIA = bottomSheetView.findViewById(R.id.wrongTvIA);
        TextView marksTvIA = bottomSheetView.findViewById(R.id.marksTvIA);

        TextView totalTVBA = bottomSheetView.findViewById(R.id.totalTvBA);
        TextView correctTvBA = bottomSheetView.findViewById(R.id.correctTvBA);
        TextView wrongTvBA = bottomSheetView.findViewById(R.id.wrongTvBA);
        TextView marksTvBA = bottomSheetView.findViewById(R.id.marksTvBA);

        TextView totalTVB = bottomSheetView.findViewById(R.id.totalTvB);
        TextView correctTvB = bottomSheetView.findViewById(R.id.correctTvB);
        TextView wrongTvB = bottomSheetView.findViewById(R.id.wrongTvB);
        TextView marksTvB = bottomSheetView.findViewById(R.id.marksTvB);

        TextView totalTVMAV = bottomSheetView.findViewById(R.id.totalTvMAV);
        TextView correctTvMAV = bottomSheetView.findViewById(R.id.correctTvMAV);
        TextView wrongTvMAV = bottomSheetView.findViewById(R.id.wrongTvMAV);
        TextView marksTvMAV = bottomSheetView.findViewById(R.id.marksTvMAV);

        TextView totalTVG = bottomSheetView.findViewById(R.id.totalTvG);
        TextView correctTvG = bottomSheetView.findViewById(R.id.correctTvG);
        TextView wrongTvG = bottomSheetView.findViewById(R.id.wrongTvG);
        TextView marksTvG = bottomSheetView.findViewById(R.id.marksTvG);

        TextView totalTVML = bottomSheetView.findViewById(R.id.totalTvML);
        TextView correctTvML = bottomSheetView.findViewById(R.id.correctTvML);
        TextView wrongTvML = bottomSheetView.findViewById(R.id.wrongTvML);
        TextView marksTvML = bottomSheetView.findViewById(R.id.marksTvML);

        TextView totalTVEL = bottomSheetView.findViewById(R.id.totalTvEL);
        TextView correctTvEL = bottomSheetView.findViewById(R.id.correctTvEL);
        TextView wrongTvEL = bottomSheetView.findViewById(R.id.wrongTvEL);
        TextView marksTvEL = bottomSheetView.findViewById(R.id.marksTvEL);

        TextView totalTVMS = bottomSheetView.findViewById(R.id.totalTvMS);
        TextView correctTvMS = bottomSheetView.findViewById(R.id.correctTvMS);
        TextView wrongTvMS = bottomSheetView.findViewById(R.id.wrongTvMS);
        TextView marksTvMS = bottomSheetView.findViewById(R.id.marksTvMS);

        TextView totalTVGS = bottomSheetView.findViewById(R.id.totalTvGS);
        TextView correctTvGS = bottomSheetView.findViewById(R.id.correctTvGS);
        TextView wrongTvGS = bottomSheetView.findViewById(R.id.wrongTvGS);
        TextView marksTvGS = bottomSheetView.findViewById(R.id.marksTvGS);

        TextView totalTVICT = bottomSheetView.findViewById(R.id.totalTvICT);
        TextView correctTvICT = bottomSheetView.findViewById(R.id.correctTvICT);
        TextView wrongTvICT = bottomSheetView.findViewById(R.id.wrongTvICT);
        TextView marksTvICT = bottomSheetView.findViewById(R.id.marksTvICT);

        /// todo fixthere
        String subCode = preferencesManager.getString("subjectPosition");

        if (!subCode.isEmpty()) {
            //This is for SubjectBased Exam
            int totalQuestion = preferencesManager.getInt("examQuestionNum");

            int subCode2 = Integer.parseInt(subCode);
            if(subCode2 >= 1){
                if (questionLists != null){
                    int correctAnswer = 0;
                    int totalAnswered = 0;
                    int totalWrong = 0;

                    for (int i =0; i < questionLists.size(); i++){
                        int getUserSelectedOption = questionLists.get(i).getUserSelecedAnswer();//Get User Selected Option
                        int getQuestionAnswer = questionLists.get(i).getAnswer();
//                Check UserSelected Answer is correct Answer
                        if (getQuestionAnswer == getUserSelectedOption){
                            correctAnswer++;
                        }
                        //Check amount total answred
                        if (getUserSelectedOption != 0) {
                            totalAnswered++;
                        }
                        //check amount of wrong answred
                        if (getUserSelectedOption != 0 && getQuestionAnswer != getUserSelectedOption) {
                            totalWrong++;
                        }
                    }
                    double overallCutMarks = (double) totalWrong / 2;
                    double overallMark = (double) correctAnswer - overallCutMarks;
                    int overallNotAnswered = totalQuestion - totalAnswered;
                    String overallCorrectAnswer = String.valueOf(correctAnswer);
                    String overallWrongAnswer = String.valueOf(totalWrong);
                    String overallTotalMark = String.valueOf(overallMark);

                    LinearLayout halfLayout = bottomSheetView.findViewById(R.id.halfLayout);
                    halfLayout.setVisibility(View.GONE);

                    TextView resultSubName = bottomSheetView.findViewById(R.id.resultSubName);
                    resultSubName.setText(subjectName);

                    totalTV.setText(String.valueOf(totalQuestion));

                    correctTv.setText(overallCorrectAnswer);
                    wrongTv.setText(overallWrongAnswer);
                    marksTv.setText(overallTotalMark);

                    View viewDevider = bottomSheetView.findViewById(R.id.viewDivider);
                    viewDevider.setVisibility(View.VISIBLE);

                    if (resultPref.getInt("totalQuestions1") > 2){
                        int totalExam = ((resultPref.getInt("totalExam"))+1);
                        int totalQuestion11 = ((resultPref.getInt("totalQuestions1"))+totalQuestion);
                        int overAllCorrectAnswer = ((resultPref.getInt("overAllCorrectAnswer"))+correctAnswer);
                        int overAllWrongAnswer = ((resultPref.getInt("overAllWrongAnswer"))+totalWrong);
                        int overAllNotAnswered = ((resultPref.getInt("overAllNotAnswered"))+totalWrong);

                        resultPref.saveInt("totalExam",totalExam);
                        resultPref.saveInt("totalQuestions1",totalQuestion11);
                        resultPref.saveInt("overAllCorrectAnswer",overAllCorrectAnswer);
                        resultPref.saveInt("overAllWrongAnswer",overAllWrongAnswer);
                        resultPref.saveInt("overAllNotAnswered",overAllNotAnswered);

                    }else {
                        resultPref.saveInt("totalExam",1);
                        resultPref.saveInt("totalQuestions1",totalQuestion);
                        resultPref.saveInt("overAllCorrectAnswer",correctAnswer);
                        resultPref.saveInt("overAllWrongAnswer",totalWrong);
                        resultPref.saveInt("overAllNotAnswered",overallNotAnswered);
                    }
                    stopTimer();
                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();
                  preferencesManager.clear();
                    LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

                }
            }
        }
        else {
            if (questionLists != null) {
                int startIndex = 0;
                int[] sectionSizeArray = sectionSizeSelector(LOGIC_FOR_ALL_SUBJECT_EXAM);

                String totalIA  = Integer.toString(sectionSizeArray[0]);
                String totalBA  = Integer.toString(sectionSizeArray[1]);
                String totalB  = Integer.toString(sectionSizeArray[2]);
                String totalMAV  = Integer.toString(sectionSizeArray[3]);
                String totalG  = Integer.toString(sectionSizeArray[4]);
                String totalML  = Integer.toString(sectionSizeArray[5]);
                String totalEL  = Integer.toString(sectionSizeArray[6]);
                String totalMS  = Integer.toString(sectionSizeArray[7]);
                String totalGS  = Integer.toString(sectionSizeArray[8]);
                String totalICT  = Integer.toString(sectionSizeArray[9]);

                // Variables for overall exam result
                int totalAnswered = 0;
                int totalCorrect = 0;
                int totalWrong = 0;
                double totalMarks = 0;

                for (int i = 0; i < sectionSizeArray.length; i++) {
                    int endIndex = Math.min(startIndex + sectionSizeArray[i], questionLists.size());
                    List<QuestionList> sectionQuestions = questionLists.subList(startIndex, endIndex);

                    // Calculate marks for this section
                    int answeredQuestions = 0;
                    int correct = 0;
                    int wrong = 0;
                    for (QuestionList question : sectionQuestions) {
                        // Get the correct answer for the current question

                        int getQuestionAnswer = question.getAnswer();
                        // Get the answer selected by the user for the current question
                        int getUserSelectedOption = question.getUserSelecedAnswer();
                        // If the user has selected an answer, increment the answeredQuestions counter
                        if (getUserSelectedOption != 0) {
                            answeredQuestions++;
                            totalAnswered++;
                        }
                        // If the user's selected answer is the same as the correct answer, increment the correct counter
                        if (getQuestionAnswer == getUserSelectedOption) {
                            correct++;
                            totalCorrect++;
                        }
                        if (getUserSelectedOption != 0 && getQuestionAnswer != getUserSelectedOption) {
                            wrong++;
                            totalWrong++;
                        }
                    }
                    double cutMarks = (double) wrong / 2;
                    double mark = (double) correct - cutMarks;
                    totalMarks += mark;

                    String correctAnswer = String.valueOf(correct);
                    String wrongAnswer = String.valueOf(wrong);
                    String totalMark = String.valueOf(mark);

                    if (i==0){
                        setResultIntoTextView(totalTVIA,correctTvIA,wrongTvIA,marksTvIA,
                                totalIA,correctAnswer,wrongAnswer,totalMark);
                    }
                    else if (i==1) {
                        setResultIntoTextView(totalTVBA,correctTvBA,wrongTvBA,marksTvBA,totalBA,correctAnswer,wrongAnswer,totalMark);
                    }
                    else if (i==2) {
                        //Todo Continue from hare
                        setResultIntoTextView(totalTVB,correctTvB,wrongTvB,marksTvB,totalB,correctAnswer,wrongAnswer,totalMark);
                    }
                    else if (i==3) {
                        setResultIntoTextView(totalTVMAV,correctTvMAV,wrongTvMAV,marksTvMAV,totalMAV,correctAnswer,wrongAnswer,totalMark);
                    }
                    else if (i==4) {
                        setResultIntoTextView(totalTVG,correctTvG,wrongTvG,marksTvG,totalG,correctAnswer,wrongAnswer,totalMark);
                    }
                    else if (i==5) {
                        setResultIntoTextView(totalTVML,correctTvML,wrongTvML,marksTvML,totalML,correctAnswer,wrongAnswer,totalMark);
                    }
                    else if (i==6) {
                        setResultIntoTextView(totalTVEL,correctTvEL,wrongTvEL,marksTvEL,totalEL,correctAnswer,wrongAnswer,totalMark);

                    }
                    else if (i==7) {
                        setResultIntoTextView(totalTVMS,correctTvMS,wrongTvMS,marksTvMS,totalMS,correctAnswer,wrongAnswer,totalMark);
                    }
                    else if (i==8) {
                        setResultIntoTextView(totalTVGS,correctTvGS,wrongTvGS,marksTvGS,totalGS,correctAnswer,wrongAnswer,totalMark);

                    }
                    else if (i==9) {
                        setResultIntoTextView(totalTVICT,correctTvICT,wrongTvICT,marksTvICT,totalICT,correctAnswer,wrongAnswer,totalMark);

                    }
                    startIndex = endIndex;

                }

                // Calculate overall exam result
                double overallCutMarks = (double) totalWrong / 2;
                double overallMark = (double) totalCorrect - overallCutMarks;
                String overallAnswered = String.valueOf(totalAnswered);
                String overallCorrectAnswer = String.valueOf(totalCorrect);
                String overallWrongAnswer = String.valueOf(totalWrong);
                String overallTotalMark = String.valueOf(overallMark);

                int notAnswred = LOGIC_FOR_ALL_SUBJECT_EXAM-Integer.parseInt(overallAnswered);

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                setResultIntoTextView(totalTV,correctTv,wrongTv,marksTv,String.valueOf(LOGIC_FOR_ALL_SUBJECT_EXAM)
                        ,overallCorrectAnswer,overallWrongAnswer, overallTotalMark);

                preferencesManager.saveString("totalQuestions",String.valueOf(LOGIC_FOR_ALL_SUBJECT_EXAM));


                if (resultPref.getInt("totalQuestions1") > 2 ){
                    int totalExam = ((resultPref.getInt("totalExam"))+1);
                    int totalQuestion = ((resultPref.getInt("totalQuestions1"))+LOGIC_FOR_ALL_SUBJECT_EXAM);
                    int overAllCorrectAnswer = ((resultPref.getInt("overAllCorrectAnswer"))+totalCorrect);
                    int overAllWrongAnswer = ((resultPref.getInt("overAllWrongAnswer"))+totalWrong);
                    int overAllNotAnswered = ((resultPref.getInt("overAllNotAnswered"))+totalWrong);

                    resultPref.saveInt("totalExam",totalExam);
                    resultPref.saveInt("totalQuestions1",totalQuestion);
                    resultPref.saveInt("overAllCorrectAnswer",overAllCorrectAnswer);
                    resultPref.saveInt("overAllWrongAnswer",overAllWrongAnswer);
                    resultPref.saveInt("overAllNotAnswered",overAllNotAnswered);


                }else {
                    resultPref.saveInt("totalExam",1);
                    resultPref.saveInt("totalQuestions1",LOGIC_FOR_ALL_SUBJECT_EXAM);
                    resultPref.saveInt("overAllCorrectAnswer",totalCorrect);
                    resultPref.saveInt("overAllWrongAnswer",totalWrong);
                    resultPref.saveInt("overAllNotAnswered",notAnswred);
                }

//                saveResult.setTotal(String.valueOf(LOGIC_FOR_ALL_SUBJECT_EXAM));
//                saveResult.setCorrect(overallCorrectAnswer);
//                saveResult.setWrong(overallWrongAnswer);
//                saveResult.setMark(overallTotalMark);
//                saveResult.setUserId(userId);
//                saveResult.setDate(examDateTime);
//                saveResult.setNotAnswred(String.valueOf(notAnswred));
//
//                resultSaver.saveResult();
                stopTimer();
                LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
                preferencesManager.clear();
            }
        }
//        resultSaver.saveResult();

        preferencesManager.clear();
    }

    public void showSubmissionOption (String answered,SubmissionCallback submissionCallback){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ActivityExam.this, R.style.BottomSheetDailogTheme);
        View bottomSheetView = LayoutInflater.from(ActivityExam.this)
                .inflate(R.layout.submit_answer, bottomSheetDialog.findViewById(R.id.bottomSheetContainer));

        TextView textView = bottomSheetView.findViewById(R.id.tvDis);
        textView.setText("আপনি (" + NUM_OF_QUESTION + ") প্রশ্নের মধ্যে  ("+answered+") টি প্রশ্নের উত্তর দিয়েছেন");

//        SharedViewModel viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        bottomSheetView.findViewById(R.id.btnSubmit).setOnClickListener(submitView -> {

//            viewModel.setSubCode(2);
            submissionCallback.onSubmitClicked();
            recView.setVisibility(View.VISIBLE);
            REQ_CODE2 = 2;
            finishExam();
            bottomSheetDialog.dismiss();
        });
        bottomSheetView.findViewById(R.id.btnCancal).setOnClickListener(v -> bottomSheetDialog.dismiss());

    }

    public interface SubmissionCallback {
        void onSubmitClicked();
    }
    @Override
    public void onBackPressed() {
        if (REQ_CODE2 == 0) {
            Log.d("REQ_CODE2","REQ_CODE2 =0");

            int answeredQuestions = 0;
            for (QuestionList question : questionLists) {
                int getUserSelectedOption = question.getUserSelecedAnswer();
                if (getUserSelectedOption != 0) {
                    answeredQuestions++;
                }
            }
            showSubmissionOption(String.valueOf(answeredQuestions),() -> {

            });
        } else if (REQ_CODE2 == 2) {
            preferencesManager.clear();
            super.onBackPressed();
            finish();
        }
    }
    public int[] sectionSizeSelector(int LOGIC_FOR_ALL_SUBJECT_EXAM) {
        int[] sectionSize = null;

        if (LOGIC_FOR_ALL_SUBJECT_EXAM != 0) {
            if (LOGIC_FOR_ALL_SUBJECT_EXAM == 200) {
                sectionSize = new int[]{20, 30, 35, 10, 10, 15, 35, 15, 15, 15};
            } else if (LOGIC_FOR_ALL_SUBJECT_EXAM == 100) {
                sectionSize = new int[]{10, 15, 18, 5, 5, 7, 17, 8, 7, 8};
            } else if (LOGIC_FOR_ALL_SUBJECT_EXAM == 50) {
                sectionSize = new int[]{5, 7, 9, 3, 3, 4, 8, 4, 3, 4};
            }
        }
        return sectionSize;
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        REQ_CODE2 = 0;
        preferencesManager.clear();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }


}

