package com.gdalamin.bcs_pro.Activity;

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
import com.gdalamin.bcs_pro.adapter.TestmyadapterForAllbcs;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestQuestionBank extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imageBackButton;
    ShimmerFrameLayout shimmerFrameLayout;

    TextView titleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_question_bank);


        recyclerView = findViewById(R.id.recview33);
        shimmerFrameLayout = findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);


        imageBackButton = findViewById(R.id.backButton);
        imageBackButton.setOnClickListener(view -> {
            onBackPressed();
        });

        String url2 = ApiKeys.API_WITH_SQL+"&query=SELECT * FROM other WHERE text <> '' ORDER BY id ;";

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
                TestmyadapterForAllbcs adapter=new TestmyadapterForAllbcs(data);
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