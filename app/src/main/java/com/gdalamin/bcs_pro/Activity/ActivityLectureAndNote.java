package com.gdalamin.bcs_pro.Activity;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.modelClass.modelForLecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdalamin.bcs_pro.adapter.LectureAndNotesAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ActivityLectureAndNote extends AppCompatActivity {

    RecyclerView recyclerView;
    private  static final String url="http://192.168.0.104/api2/lectureAndNotes.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_and_note);

        recyclerView = findViewById(R.id.recviewLecture);

        processdata();

    }

    public void processdata()
    {

        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                modelForLecture data[]=gson.fromJson(response,modelForLecture[].class);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext()
                        ,LinearLayoutManager.VERTICAL,false);

                recyclerView.setLayoutManager(linearLayoutManager);
                LectureAndNotesAdapter adapter=new LectureAndNotesAdapter(data);
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