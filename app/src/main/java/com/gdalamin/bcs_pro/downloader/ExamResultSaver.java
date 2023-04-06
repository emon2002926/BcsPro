package com.gdalamin.bcs_pro.downloader;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdalamin.bcs_pro.modelClass.ExamResult;

import java.util.HashMap;
import java.util.Map;

public class ExamResultSaver {
    private Context context;
    private String saveResultUrl;
    private ExamResult examResult;

    public ExamResultSaver(Context context, String saveResultUrl, ExamResult examResult) {
        this.context = context;
        this.saveResultUrl = saveResultUrl;
        this.examResult = examResult;
    }

    public void saveResult() {
        StringRequest request = new StringRequest(Request.Method.POST, saveResultUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Result saved", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("total", examResult.getTotal());
                param.put("correct", examResult.getCorrect());
                param.put("wrong", examResult.getWrong());
                param.put("mark", examResult.getMark());
                param.put("userId", examResult.getUserId());
                param.put("date", examResult.getDate());

                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
