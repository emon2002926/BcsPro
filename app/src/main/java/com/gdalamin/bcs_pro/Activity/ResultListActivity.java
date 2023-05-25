package com.gdalamin.bcs_pro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.resultAdapter;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.modelClass.ExamResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ResultListActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    RecyclerView recview;
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        recview=findViewById(R.id.recview);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        processData();
    }

    public void processData() {
        // Create a new StringRequest to retrieve data from the API
        sharedPreferences = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("key_phone", "");

        String API_URL = ApiKeys.API_URL+"api/getData.php?apiKey=abc123&apiNum=6&userId=";

        StringRequest request = new StringRequest(API_URL+userId,
                response -> {
                    try {
                        Gson gson = new Gson();
                        ExamResult[] examResults = gson.fromJson(response, ExamResult[].class);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                        recview.setLayoutManager(linearLayoutManager);

                        resultAdapter adapter = new resultAdapter(examResults);

                        recview.setAdapter(adapter);

                        recview.setVisibility(View.VISIBLE);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();


                    }
                },
                error -> {

                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}