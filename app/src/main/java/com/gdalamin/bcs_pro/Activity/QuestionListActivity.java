package com.gdalamin.bcs_pro.Activity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.ApiKeys;
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
import com.google.gson.stream.JsonReader;

import java.io.Serializable;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
public class QuestionListActivity extends AppCompatActivity {

    RecyclerView recview;

    TextView textView;
    ImageView imageBackButton;
      String  API_URL ="";

    ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        recview=findViewById(R.id.recview);
        shimmerFrameLayout = findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();


        textView = findViewById(R.id.topTv);
        textView.setText("Important Question");

        imageBackButton = findViewById(R.id.backButton);
        imageBackButton.setOnClickListener(view -> {
            onBackPressed();
        });


        SharedPreferences sharedPreferences = getSharedPreferences("totalQuestion", MODE_PRIVATE);
        int subCode = sharedPreferences.getInt("subCode",0);
        Log.d("subCode100",String.valueOf(subCode));
//        sharedPreferences.edit().clear().apply();



        if (subCode == 3){


            String SUBJECT_CODE= sharedPreferences.getString("subjectPosition","");


            String url = "http://emon.searchwizy.com/api2/getSubjectBasedExam.php?apiKey=abc123&apiNum="+SUBJECT_CODE+"&IA=200";

            Log.d("eee3",url);
            processdata(url);

        } else if (subCode == 4){

            String subjectName = sharedPreferences.getString("bcsYearName","");
            String apiWithSql = ApiKeys.API_WITH_SQL;

            String url2 = apiWithSql+"&query=SELECT * FROM question WHERE batch LIKE '"+subjectName+"' ORDER BY id DESC LIMIT 200";

            processdata(url2);
            Log.d("eee2",subjectName);

        }else if (subCode == 5){

            API_URL = ApiKeys.API_URL_GENERAL+"apiNum=1";
            Log.d("eee1",API_URL);
            processdata(API_URL);
        } else if (subCode == 6) {
            Log.d("eee4","this one");

        }


    }

    public void processdata(String API_URL)
    {


        // Todo got the api url


        StringRequest request=new StringRequest(API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                SharedPreferences sharedPreferences = getSharedPreferences("totalQuestion", MODE_PRIVATE);
//                sharedPreferences.edit().clear().apply();
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