package com.example.recreatequize.Activity;

import com.example.recreatequize.R;
import com.example.recreatequize.modelClass.QuestionList;
import com.example.recreatequize.modelClass.model;
import com.example.recreatequize.adapter.myadapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionListActivity extends AppCompatActivity {

    private  static final String url="http://192.168.0.103/api2/allQuestion.php";
    RecyclerView recview;

    private static  String url2;
    TextView textView;

    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        recview=(RecyclerView)findViewById(R.id.recview);

        processdata();
        url2 = getIntent().getExtras().getString("allQuestion1");

        textView = findViewById(R.id.topTv);
        textView.setText(url2);

        btnSubmit = findViewById(R.id.btnSubmit);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("my_list_action"));


    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

//            String qty = intent.getStringExtra("quantity");

            if (intent.getAction().equals("my_list_action")) {
                ArrayList<QuestionList> questionLists = (ArrayList<QuestionList>) intent.getSerializableExtra("my_list_key");
                // Do something with the list here


                btnSubmit.setOnClickListener(view -> {

                    Intent intent1 = new Intent(QuestionListActivity.this, QuizResult.class);
                    //Creating Bundle To pass QuestionList
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("qutions",(Serializable) questionLists);

                    intent1.putExtras(bundle);
                    startActivity(intent1);
                });

            }

        }
    };


    public void processdata()
    {


        // Todo got the api url


        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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