package com.gdalamin.bcs_pro.Activity;

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
    ShimmerFrameLayout shimmerFrameLayout;
    private boolean mBooleanValue = false;
    SharedPreferencesManagerAppLogic preferencesManager;
    int subCode;
    String Older_Bcs_Question = "";
    AppDatabase db;
    String subjectName;
    String apiWithSql = ApiKeys.API_WITH_SQL;
    LinearLayout tryAgainLayout;
    TextView retryBtn;
    ProgressBar progressBar;
//    SharedViewModel viewModel;
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


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titleText = extras.getString("titleText");
            textView.setText(titleText);
            //The key argument here must match that used in the other activity
        }





        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class, "questionTable"
        ).build();

        preferencesManager = new SharedPreferencesManagerAppLogic(QuestionListActivity.this);
        subCode = preferencesManager.getInt("subCode");
        Older_Bcs_Question =preferencesManager.getString("oldBcs");
        subjectName = preferencesManager.getString("bcsYearName");

        new bgThreat().start();


    }
    class bgThreat extends Thread {
        public void run() {
            super.run();
            // Initialize the database instance
            List<model> dataList = db.modelDao().getModelsByBatch(subjectName);
            if (Older_Bcs_Question.equals("Older_Bcs_Question")){

                if (dataList != null && dataList.size() > 49) {
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
                    String url2 = apiWithSql+"&query=SELECT * FROM question WHERE batch LIKE '"+subjectName+"' ORDER BY id DESC LIMIT 200";
                    getMCQuestions(url2);
                }
            }else if (Older_Bcs_Question.equals("")){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String apiWithSql = ApiKeys.API_WITH_SQL;
                        if (subCode == 3){
                            String SUBJECT_CODE= preferencesManager.getString("subjectCode");

                            String url2 = apiWithSql+"&query=SELECT * FROM `question` WHERE subjects LIKE '"+SUBJECT_CODE+"' ORDER BY id DESC LIMIT 200 ";
                            getMCQuestions(url2);
                        } else if (subCode == 5){

                            String url2 =ApiKeys.API_WITH_SQL+"&query=SELECT * FROM question ORDER BY RAND() LIMIT 200;";
                            getMCQuestions(url2);

                        } else if (subCode == 6) {

                        }

                    }
                });
            }
        }
    }
    private void getMCQuestions(String API_URL) {
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

        }, error -> {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            tryAgainLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
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

            if (!mBooleanValue){

                showAnswer.setImageResource(R.drawable.hidden);
            }else if (mBooleanValue){
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