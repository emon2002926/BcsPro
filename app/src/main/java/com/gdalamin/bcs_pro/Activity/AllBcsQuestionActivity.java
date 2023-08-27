package com.gdalamin.bcs_pro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.gdalamin.bcs_pro.adapter.myadapterForAllbcs;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.api.SharedPreferencesManagerAppLogic;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AllBcsQuestionActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ImageView imageBackButton;
    ShimmerFrameLayout shimmerFrameLayout;

    TextView titleTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bcs_question);


        recyclerView = findViewById(R.id.recview3);
        shimmerFrameLayout = findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);


        imageBackButton = findViewById(R.id.backButton);
        imageBackButton.setOnClickListener(view -> {
            onBackPressed();
        });

        titleTv = findViewById(R.id.titleTv);

        SharedPreferencesManagerAppLogic preferencesManager = new SharedPreferencesManagerAppLogic(imageBackButton.getContext());


        int OPENING_LOGIC = preferencesManager.getInt("logic");

        Intent intent = getIntent();
        String titleText=intent.getStringExtra("titleText");
        titleTv.setText(titleText);


        /*

        if (OPENING_LOGIC ==1){
            //thats will open  older BCS Question

            String url2 =ApiKeys.API_WITH_SQL+"&query=SELECT * FROM other WHERE text <> '' ORDER BY id ;";

            String API_URL = ApiKeys.API_URL+"api/getData.php?apiKey=abc123&apiNum=7";
            processdata(url2);
        } else if (OPENING_LOGIC ==2) {
            //thats will open  subject based Exam
            String url2 =ApiKeys.API_WITH_SQL+"&query=SELECT * FROM other WHERE subjects <> '' ORDER BY id DESC LIMIT 10  ;";
            String API_URL = ApiKeys.API_URL+"api/getData.php?apiKey=abc123&apiNum=8";

        }

         */

        String url2 =ApiKeys.API_WITH_SQL+"&query=SELECT * FROM other WHERE subjects <> '' ORDER BY id DESC LIMIT 10  ;";
        processdata(url2);

    }

    public void processdata(String url)
    {

        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                ModelForLectureAndAllQuestion[] data =gson.fromJson(response, ModelForLectureAndAllQuestion[].class);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext()
                        ,LinearLayoutManager.VERTICAL,false);

                recyclerView.setLayoutManager(linearLayoutManager);
                myadapterForAllbcs adapter=new myadapterForAllbcs(data);
                recyclerView.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(AllBcsQuestionActivity.this,"Please check ",Toast.LENGTH_LONG).show();
            }
        }
        );

        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);

    }



}