package com.gdalamin.bcs_pro.Activity;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.QuestionList;
import com.gdalamin.bcs_pro.modelClass.model;
import com.gdalamin.bcs_pro.adapter.myadapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class QuestionListActivity extends AppCompatActivity {

    private  static final String url="http://192.168.0.104/api2/allQuestion.php";
    RecyclerView recview;

    TextView textView;
    ImageView imageBackButton;

    ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        recview=(RecyclerView)findViewById(R.id.recview);
        shimmerFrameLayout = findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();


        processdata();


        textView = findViewById(R.id.topTv);
        textView.setText("Important Question");

        imageBackButton = findViewById(R.id.backButton);
        imageBackButton.setOnClickListener(view -> {
            onBackPressed();
        });

    }

    public void processdata()
    {


        // Todo got the api url

        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

                recview.setVisibility(View.VISIBLE);

                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                model data[]=gson.fromJson(response,model[].class);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext()
                        ,LinearLayoutManager.VERTICAL,false);

                recview.setLayoutManager(linearLayoutManager);
                myadapter adapter=new myadapter(data);
                recview.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }
        );


        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

}