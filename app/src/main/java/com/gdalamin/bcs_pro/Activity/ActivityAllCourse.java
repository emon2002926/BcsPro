package com.gdalamin.bcs_pro.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.AdapterForCourse;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ActivityAllCourse extends AppCompatActivity {

    RecyclerView recyclerView;
    CardView backBtn;


    private  static final String API_URL = ApiKeys.API_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_course);

        recyclerView = findViewById(R.id.recviewLecture);
        backBtn = findViewById(R.id.card);
        backBtn.setOnClickListener(view -> {
            onBackPressed();
        });


        processdata();

    }

    public void processdata()
    {

        StringRequest request=new StringRequest(API_URL+"api/getData.php?apiKey=abc123&apiNum=4", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                ModelForLectureAndAllQuestion data[]=gson.fromJson(response, ModelForLectureAndAllQuestion[].class);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext()
                        ,2);

                recyclerView.setLayoutManager(gridLayoutManager);
                AdapterForCourse adapter=new AdapterForCourse(data);
                recyclerView.setAdapter(adapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ActivityAllCourse.this,"Please check your internet connection and try again",Toast.LENGTH_LONG).show();

            }
        }
        );

        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);

    }
}