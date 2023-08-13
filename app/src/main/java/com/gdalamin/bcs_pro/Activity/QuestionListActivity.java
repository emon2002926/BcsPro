package com.gdalamin.bcs_pro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.gdalamin.bcs_pro.modelClass.model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
public class QuestionListActivity extends AppCompatActivity {

    RecyclerView recview;

    TextView textView;
    ImageView imageBackButton;
    FloatingActionButton showAnswer;
      String  API_URL ="";

    ShimmerFrameLayout shimmerFrameLayout;
    private boolean mBooleanValue = false;
    SharedPreferencesManagerAppLogic preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        recview=findViewById(R.id.recview);
        shimmerFrameLayout = findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();


        textView = findViewById(R.id.topTv);


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

        preferencesManager = new SharedPreferencesManagerAppLogic(QuestionListActivity.this);
        int subCode = preferencesManager.getInt("subCode");

        if (subCode == 3){
            String SUBJECT_CODE= preferencesManager.getString("subjectPosition");
            String url = ApiKeys.API_URL+"api/getSubjectBasedExam.php?apiKey=abc123&apiNum="+SUBJECT_CODE+"&IA=200";
            processdata(url);

        } else if (subCode == 4){

            String subjectName = preferencesManager.getString("bcsYearName");
            String apiWithSql = ApiKeys.API_WITH_SQL;
            String url2 = apiWithSql+"&query=SELECT * FROM question WHERE batch LIKE '"+subjectName+"' ORDER BY id DESC LIMIT 200";
            processdata(url2);

        }else if (subCode == 5){
            API_URL = ApiKeys.API_URL+"api/getData.php?apiKey=abc123&apiNum=1";
            processdata(API_URL);

        } else if (subCode == 6) {

        }
        showAnswer = findViewById(R.id.btnShowAnswer);

    }

    public void processdata(String API_URL)
    {
       StringRequest request=new StringRequest(API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

                recview.setVisibility(View.VISIBLE);

                GsonBuilder builder = new GsonBuilder().setLenient();
                Gson gson = builder.create();

                JsonReader reader = new JsonReader(new StringReader(response));
                reader.setLenient(true);


                model data[] = gson.fromJson(reader, model[].class);


                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext()
                        ,LinearLayoutManager.VERTICAL,false);

                recview.setLayoutManager(linearLayoutManager);
                myadapter adapter=new myadapter(data);


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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QuestionListActivity.this,"Please check your internet connection and try again",Toast.LENGTH_LONG).show();
            }
        }
        );
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preferencesManager.clear();
    }

}