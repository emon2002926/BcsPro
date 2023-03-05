package com.gdalamin.bcs_pro.Activity;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.gdalamin.bcs_pro.modelClass.model;
import  com.gdalamin.bcs_pro.adapter.myadapterForAllbcs;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AllBcsQuestionActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ImageView imageBackButton;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bcs_question);


        recyclerView = findViewById(R.id.recview3);
        shimmerFrameLayout = findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();

        imageBackButton = findViewById(R.id.backButton);
        imageBackButton.setOnClickListener(view -> {
            onBackPressed();
        });




        SharedPreferences sharedPreferences = getSharedPreferences("totalQuestion", MODE_PRIVATE);
        int NUM_OF_QUESTION = sharedPreferences.getInt("logic", 0);
        Log.d("logic",String.valueOf(NUM_OF_QUESTION));



        if (NUM_OF_QUESTION ==1){
           String url =  "http://emon.searchwizy.com/Test%20Api%27s/holder.php?apiKey=abc123&apiNum=1";
            processdata(url);
        } else if (NUM_OF_QUESTION ==2) {

           String url = "http://emon.searchwizy.com/Test%20Api%27s/holder.php?apiKey=abc123&apiNum=2";
            processdata(url);
        }


    }

    public void processdata(String url)
    {


//        String API_URL = ApiKeys.API_URL_GENERAL+"apiNum=5";

        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                ModelForLectureAndAllQuestion data[]=gson.fromJson(response,ModelForLectureAndAllQuestion[].class);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext()
                        ,LinearLayoutManager.VERTICAL,false);

                recyclerView.setLayoutManager(linearLayoutManager);
                myadapterForAllbcs adapter=new myadapterForAllbcs(data);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(recyclerView.getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }
        );

        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);

    }
}