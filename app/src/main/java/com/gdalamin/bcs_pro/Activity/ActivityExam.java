package com.gdalamin.bcs_pro.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.api.SharedPreferencesManager;
import com.gdalamin.bcs_pro.downloader.ExamResultSaver;
import com.gdalamin.bcs_pro.downloader.ShowMcq;
import com.gdalamin.bcs_pro.modelClass.ExamResult;
import com.gdalamin.bcs_pro.modelClass.QuestionList;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ActivityExam extends AppCompatActivity {


    private static String APIKEY ="api2/getExamMcq.php?apiKey=abc123&apiNum=1&";


    String API_URL= ApiKeys.API_URL;
    RecyclerView recview;

    TextView textView,textViewTimer,btnBackTohome;
    FloatingActionButton floatingActionButton;
    ArrayList<QuestionList> questionLists = new ArrayList<QuestionList>();
    SharedPreferences sharedPreferences;

    int NUM_OF_QUESTION =0;

    ImageView imageBackButton;

    ShimmerFrameLayout shimmerFrameLayout;
    SharedPreferencesManager preferencesManager;


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

        btnBackTohome = findViewById(R.id.btnBackToHome);
        btnBackTohome.setOnClickListener(view -> {
            startActivity(new Intent(ActivityExam.this,MainActivity.class));
        });

        String title = getIntent().getStringExtra("titleText");

        textView.setText(title);

        floatingActionButton = findViewById(R.id.btnSubmit);


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("my_list_action"));

        sharedPreferences = getSharedPreferences("totalQuestion", MODE_PRIVATE);

        preferencesManager = new SharedPreferencesManager(this);
        NUM_OF_QUESTION = preferencesManager.getInt("examQuestionNum");



        int LOGIC_FOR_ALL_SUBJECT_EXAM = preferencesManager.getInt("LogicForExam");



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
            }
            else if (LOGIC_FOR_ALL_SUBJECT_EXAM == 100) {
                time = 100;
                questionType = APIKEY + "numIA=10&numBA=15&numBLL=18&numMVG=5&numGEDM=5&numML=7&numELL=17&numMA=8&numGS=7&numICT=8";


            }
            else if (LOGIC_FOR_ALL_SUBJECT_EXAM == 50) {
                time = 50;
                questionType = APIKEY + "numIA=5&numBA=7&numBLL=9&numMVG=3&numGEDM=3&numML=4&numELL=8&numMA=4&numGS=3&numICT=4";
            }
            else if (LOGIC_FOR_ALL_SUBJECT_EXAM == 2) {

                time = (preferencesManager.getInt("time")*2);
                NUM_OF_QUESTION= preferencesManager.getInt("examQuestionNum");
                String SUBJECT_CODE = preferencesManager.getString("subjectPosition");

                questionType = "api2/getSubjectBasedExam.php?apiKey=abc123&apiNum="+SUBJECT_CODE+"&IA="+NUM_OF_QUESTION;

            }
            else {

                return;
           }

            ShowMcq showMcq = new ShowMcq(this, shimmerFrameLayout, recview, floatingActionButton, textViewTimer, time, timerCallback);
            showMcq.processdata( API_URL+questionType);
            Log.d("questionUrl",API_URL+questionType);

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
                    showSubmissionOption(answered);
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
        preferencesManager = new SharedPreferencesManager(this);
        int LOGIC_FOR_ALL_SUBJECT_EXAM = preferencesManager.getInt("LogicForExam");




        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String time = timeFormat.format(calendar.getTime());
        String examDateTime = String.valueOf(time+" of "+monthName+" "+day+" ");


        //gatting User Id
        SharedPreferences sharedPreferences1;
        sharedPreferences1 = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        String userId = sharedPreferences1.getString("key_phone", "");


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ActivityExam.this, R.style.BottomSheetDailogTheme);
        View bottomSheetView = LayoutInflater.from(ActivityExam.this).inflate(R.layout.bottom_sheet_result_view, (LinearLayout) bottomSheetDialog.findViewById(R.id.bottomSheetContainer));

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

        ExamResult saveResult = new ExamResult();
        ExamResultSaver resultSaver = new ExamResultSaver(this, API_URL+"api2/saveResult.php", saveResult);


        if (questionLists != null) {
            int totalQuestion = questionLists.size();
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

                String answered = String.valueOf(answeredQuestions);
                String correctAnswer = String.valueOf(correct);
                String wrongAnswer = String.valueOf(wrong);
                String totalMark = String.valueOf(mark);


                if (i==0){
                    setResultIntoTextView(totalTVIA,correctTvIA,wrongTvIA,marksTvIA,
                            totalIA,correctAnswer,wrongAnswer,totalMark);


                    saveResult.setTotalIA(totalIA.trim());
                    saveResult.setCorrectIA(correctAnswer.trim());
                    saveResult.setWrongIA(wrongAnswer.trim());
                    saveResult.setMarksIA(totalMark.trim());
                }
                else if (i==1) {
                    setResultIntoTextView(totalTVBA,correctTvBA,wrongTvBA,marksTvBA,totalBA,correctAnswer,wrongAnswer,totalMark);

                    saveResult.setTotalBA(totalBA.trim());
                    saveResult.setCorrectBA(correctAnswer.trim());
                    saveResult.setWrongBA(wrongAnswer.trim());
                    saveResult.setMarksBA(totalMark.trim());
                }
                else if (i==2) {
                    //Todo Continue from hare
                    setResultIntoTextView(totalTVB,correctTvB,wrongTvB,marksTvB,totalB,correctAnswer,wrongAnswer,totalMark);
                    saveResult.setTotalB(totalB);
                    saveResult.setCorrectB(correctAnswer);
                    saveResult.setWrongB(wrongAnswer);
                    saveResult.setMarksB(totalMark);
                }
                else if (i==3) {
                    setResultIntoTextView(totalTVMAV,correctTvMAV,wrongTvMAV,marksTvMAV,totalMAV,correctAnswer,wrongAnswer,totalMark);
                    saveResult.setTotalMAV(totalMAV);
                    saveResult.setCorrectMAV(correctAnswer);
                    saveResult.setWrongMAV(wrongAnswer);
                    saveResult.setMarksMAV(totalMark);
                }
                else if (i==4) {
                    setResultIntoTextView(totalTVG,correctTvG,wrongTvG,marksTvG,totalG,correctAnswer,wrongAnswer,totalMark);
                    saveResult.setTotalG(totalG);
                    saveResult.setCorrectG(correctAnswer);
                    saveResult.setWrongG(wrongAnswer);
                    saveResult.setMarksG(totalMark);
                }
                else if (i==5) {
                    ///Somthing wrong hare
                    setResultIntoTextView(totalTVML,correctTvML,wrongTvML,marksTvML,totalML,correctAnswer,wrongAnswer,totalMark);
                    saveResult.setTotalML(totalML);
                    saveResult.setCorrectML(correctAnswer);
                    saveResult.setWrongML(wrongAnswer);
                    saveResult.setMarksML(totalMark);
                }
                else if (i==6) {
                    setResultIntoTextView(totalTVEL,correctTvEL,wrongTvEL,marksTvEL,totalEL,correctAnswer,wrongAnswer,totalMark);

                    saveResult.setTotalEL(totalEL);
                    saveResult.setCorrectEL(correctAnswer);
                    saveResult.setWrongEL(wrongAnswer);
                    saveResult.setMarksEL(totalMark);
                }
                else if (i==7) {
                    setResultIntoTextView(totalTVMS,correctTvMS,wrongTvMS,marksTvMS,totalMS,correctAnswer,wrongAnswer,totalMark);

                    saveResult.setTotalMS(totalMS);
                    saveResult.setCorrectMS(correctAnswer);
                    saveResult.setWrongMS(wrongAnswer);
                    saveResult.setMarksMS(totalMark);
                }
                else if (i==8) {
                    setResultIntoTextView(totalTVGS,correctTvGS,wrongTvGS,marksTvGS,totalGS,correctAnswer,wrongAnswer,totalMark);
                    saveResult.setTotalGS(totalGS);
                    saveResult.setCorrectGS(correctAnswer);
                    saveResult.setWrongGS(wrongAnswer);
                    saveResult.setMarksGS(totalMark);
                }
                else if (i==9) {
                    setResultIntoTextView(totalTVICT,correctTvICT,wrongTvICT,marksTvICT,totalICT,correctAnswer,wrongAnswer,totalMark);
                    saveResult.setTotalICT(totalICT);
                    saveResult.setCorrectICT(correctAnswer);
                    saveResult.setWrongICT(wrongAnswer);
                    saveResult.setMarksICT(totalMark);
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


            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();

            setResultIntoTextView(totalTV,correctTv,wrongTv,marksTv,String.valueOf(LOGIC_FOR_ALL_SUBJECT_EXAM)
                    ,overallCorrectAnswer,overallWrongAnswer, overallTotalMark);



            saveResult.setTotal(String.valueOf(LOGIC_FOR_ALL_SUBJECT_EXAM));
            saveResult.setCorrect(overallCorrectAnswer);
            saveResult.setWrong(overallWrongAnswer);
            saveResult.setMark(overallTotalMark);
            saveResult.setUserId(userId);
            saveResult.setDate(examDateTime);


            resultSaver.saveResult();

            sharedPreferences.edit().clear().apply();

        }


    }


    public void showSubmissionOption (String answered){

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ActivityExam.this, R.style.BottomSheetDailogTheme);
        View bottomSheetView = LayoutInflater.from(ActivityExam.this)
                .inflate(R.layout.submit_answer, (LinearLayout) bottomSheetDialog.findViewById(R.id.bottomSheetContainer));

        TextView textView = bottomSheetView.findViewById(R.id.tvDis);
        textView.setText("You have answered " + answered + " Question out of "+NUM_OF_QUESTION);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        bottomSheetView.findViewById(R.id.btnSubmit).setOnClickListener(submitView -> {


            recview.setVisibility(View.GONE);
            btnBackTohome.setVisibility(View.VISIBLE);
            finishExam();
            bottomSheetDialog.dismiss();
            
        });

        bottomSheetView.findViewById(R.id.btnCancal).setOnClickListener(cancelView -> {
            //added for testing

            startActivity(new Intent(ActivityExam.this,MainActivity.class));
            bottomSheetDialog.dismiss();
        });
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
        totalTV.setText(total);
        correctTV.setText(correct);
        wrongTV.setText(wrong);
        marksTV.setText(marks);

    }



}

