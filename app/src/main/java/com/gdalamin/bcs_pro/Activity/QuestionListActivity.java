package com.gdalamin.bcs_pro.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.myadapter;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.api.SharedPreferencesManagerAppLogic;
import com.gdalamin.bcs_pro.dataClass.AppDatabase;
import com.gdalamin.bcs_pro.modelClass.model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

public class QuestionListActivity extends AppCompatActivity {
    RecyclerView recview;
    TextView textView;
    ImageView imageBackButton;
    FloatingActionButton showAnswer;
      String  API_URL ="";

    ShimmerFrameLayout shimmerFrameLayout;
    private boolean mBooleanValue = false;
    SharedPreferencesManagerAppLogic preferencesManager;

    model data[];
    int subCode;
    String Older_Bcs_Question = "";
    AppDatabase db;
    String subjectName;
    String apiWithSql = ApiKeys.API_WITH_SQL;
    LinearLayout tryAgainLayout;
    TextView retryBtn;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        recview=findViewById(R.id.recview);
        shimmerFrameLayout = findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();

        tryAgainLayout = findViewById(R.id.tryAgainLayout);
        retryBtn = findViewById(R.id.retryBtn);
        progressBar = findViewById(R.id.progressBar);
        retryBtn.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.tangerine), PorterDuff.Mode.SRC_IN);
            new bgThreat().start();
        });

        textView = findViewById(R.id.topTv);
        showAnswer = findViewById(R.id.btnShowAnswer);


        imageBackButton = findViewById(R.id.backButton);
        imageBackButton.setOnClickListener(view -> {
            onBackPressed();
        });

        Intent intent = getIntent();
        String titleText=intent.getStringExtra("titleText");

        if (!titleText.isEmpty()){
            textView.setText(titleText);

        }else {
            textView.setText("Important Question");
        }

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class, "questionTable"
        ).build();

        preferencesManager = new SharedPreferencesManagerAppLogic(QuestionListActivity.this);
        subCode = preferencesManager.getInt("subCode");
        Older_Bcs_Question =preferencesManager.getString("oldBcs");
        subjectName = preferencesManager.getString("bcsYearName");


        /*   version 1

        String apiWithSql = ApiKeys.API_WITH_SQL;
        if (subCode == 3){

            String SUBJECT_CODE= preferencesManager.getString("subjectCode");
           // SELECT * FROM `question` WHERE subjects LIKE 'ML' ORDER BY id DESC LIMIT 200
//            String url = ApiKeys.API_URL+"api/getSubjectBasedExam.php?apiKey=abc123&apiNum="+SUBJECT_CODE+"&IA=200";

            String url2 = apiWithSql+"&query=SELECT * FROM `question` WHERE subjects LIKE '"+SUBJECT_CODE+"' ORDER BY id DESC LIMIT 200 ";
            processdata(url2);

        } else if (subCode == 4){

            String subjectName = preferencesManager.getString("bcsYearName");

            String url2 = apiWithSql+"&query=SELECT * FROM question WHERE batch LIKE '"+subjectName+"' ORDER BY id DESC LIMIT 200";
            processdata(url2);

        }else if (subCode == 5){
            String url2 =ApiKeys.API_WITH_SQL+"&query=SELECT * FROM question ORDER BY RAND() LIMIT 200;";

            API_URL = ApiKeys.API_URL+"api/getData.php?apiKey=abc123&apiNum=1";
            processdata( url2);

        } else if (subCode == 6) {

        }
         */

        new bgThreat().start();


    }
    class bgThreat extends Thread {
        public void run() {
            super.run();
            // Initialize the database instance
            List<model> dataList = db.modelDao().getModelsByBatch(subjectName);
            int lenth = dataList.size();
//            Log.d("jrusdfskj","array Lenth is"+String.valueOf(lenth));
            if (Older_Bcs_Question.equals("Older_Bcs_Question")){

                if (dataList != null && dataList.size() > 49) {

//                    Log.d("jrusdfskj"," have data");

                    // Convert the list to an array
                    model[] data = dataList.toArray(new model[0]);
                    // Update UI on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateUi(data);
                        }
                    });
                }
                else {
//                    Log.d("jrusdfskj","dont have data");
                    String url2 = apiWithSql+"&query=SELECT * FROM question WHERE batch LIKE '"+subjectName+"' ORDER BY id DESC LIMIT 200";
                    processdata(url2);
                }
            }else if (Older_Bcs_Question.equals("")){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String apiWithSql = ApiKeys.API_WITH_SQL;
                        if (subCode == 3){
//                            Log.d("jrusdfskj"," subCode 3 excuted");
                            String SUBJECT_CODE= preferencesManager.getString("subjectCode");

                            String url2 = apiWithSql+"&query=SELECT * FROM `question` WHERE subjects LIKE '"+SUBJECT_CODE+"' ORDER BY id DESC LIMIT 200 ";
                            processdata(url2);
                        } else if (subCode == 5){

                            String url2 =ApiKeys.API_WITH_SQL+"&query=SELECT * FROM question ORDER BY RAND() LIMIT 200;";
                            processdata(url2);

                        } else if (subCode == 6) {

                        }

                    }
                });
            }
        }
    }
    private void processdata(String API_URL) {
//        Log.d("examQuestionNum", String.valueOf(API_URL));
        StringRequest request = new StringRequest(API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder().setLenient();
                Gson gson = builder.create();

                JsonReader reader = new JsonReader(new StringReader(response));
                reader.setLenient(true);
                model[] data = gson.fromJson(reader, model[].class);

                updateUi(data);
                class bgThreat1 extends Thread {
                    public void run() {
                        super.run();
                        db.modelDao().insertModels(Arrays.asList(data));
                    }
                }
                new bgThreat1().start();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e("VolleyError", String.valueOf(error));
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                tryAgainLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
//                Toast.makeText(QuestionListActivity.this, "Please check your internet connection and try again", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void updateUi(model[] model1){
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        retryBtn.setEnabled(true);
        recview.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext()
                ,LinearLayoutManager.VERTICAL,false);

        recview.setLayoutManager(linearLayoutManager);
        myadapter adapter = new myadapter(model1);
        recview.setAdapter(adapter);

        showAnswer.setOnClickListener(view -> {
            mBooleanValue = !mBooleanValue;
            adapter.setBooleanValue(mBooleanValue);

            if (mBooleanValue == false){

                showAnswer.setImageResource(R.drawable.hidden);
            }else if (mBooleanValue == true){
                showAnswer.setImageResource(R.drawable.view);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        preferencesManager.clear();
    }


    @Override
    public void onBackPressed() {

        preferencesManager.saveInt("logic",1);
        super.onBackPressed();
    }
}