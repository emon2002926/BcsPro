package com.gdalamin.bcs_pro.Activity;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.model;
import  com.gdalamin.bcs_pro.adapter.myadapterForAllbcs;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

    Button buttonShare;

    private  static final String url="http://emon.searchwizy.com/api2/getData.php?apiKey=abc123&apiNum=5";
    RecyclerView recyclerView;
    LinearLayout quizLayout,letcureLayout;
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
        processdata();


    }

    public void processdata()
    {

        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                model data[]=gson.fromJson(response,model[].class);

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