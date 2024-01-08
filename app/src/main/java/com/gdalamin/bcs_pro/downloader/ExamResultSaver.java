package com.gdalamin.bcs_pro.downloader;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdalamin.bcs_pro.modelClass.ExamResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class ExamResultSaver {
    private final Context context;
    private final String saveResultUrl;
    private final ExamResult examResult;

    public ExamResultSaver(Context context, String saveResultUrl, ExamResult examResult) {
        this.context = context;
        this.saveResultUrl = saveResultUrl;
        this.examResult = examResult;
    }

    public void saveResult() {
        // Create a RequestQueue using Volley
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Convert the examResult object to JSON format
        JSONObject jsonParams = new JSONObject();
        try {

            jsonParams.put("total", examResult.getTotal());
            jsonParams.put("correct", examResult.getCorrect());
            jsonParams.put("wrong", examResult.getWrong());
            jsonParams.put("mark", examResult.getMark());
            jsonParams.put("notAnswred", examResult.getNotAnswred());
            jsonParams.put("date", examResult.getDate());
            jsonParams.put("userId", examResult.getUserId().trim());

            jsonParams.put("totalIA", examResult.getTotalIA());
            jsonParams.put("correctIA", examResult.getCorrectIA());
            jsonParams.put("wrongIA", examResult.getWrongIA());
            jsonParams.put("marksIA", examResult.getMarksIA());

            jsonParams.put("totalBA", examResult.getTotalBA());
            jsonParams.put("correctBA", examResult.getCorrectBA());
            jsonParams.put("wrongBA", examResult.getWrongBA());
            jsonParams.put("marksBA", examResult.getMarksBA());

            jsonParams.put("totalB", examResult.getTotalB());
            jsonParams.put("correctB", examResult.getCorrectB());
            jsonParams.put("wrongB", examResult.getWrongB());
            jsonParams.put("marksB", examResult.getMarksB());

            jsonParams.put("totalMAV", examResult.getTotalMAV());
            jsonParams.put("correctMAV", examResult.getCorrectMAV());
            jsonParams.put("wrongMAV", examResult.getWrongMAV());
            jsonParams.put("marksMAV", examResult.getMarksMAV());

            jsonParams.put("totalML", examResult.getTotalML());
            jsonParams.put("correctML", examResult.getCorrectML());
            jsonParams.put("wrongML", examResult.getWrongML());
            jsonParams.put("marksML", examResult.getMarksML());

            jsonParams.put("totalEL", examResult.getTotalEL());
            jsonParams.put("correctEL", examResult.getCorrectEL());
            jsonParams.put("wrongEL", examResult.getWrongEL());
            jsonParams.put("marksEL", examResult.getMarksEL());

            jsonParams.put("totalG", examResult.getTotalG());
            jsonParams.put("correctG", examResult.getCorrectG());
            jsonParams.put("wrongG", examResult.getWrongG());
            jsonParams.put("marksG", examResult.getMarksG());

            jsonParams.put("totalMS", examResult.getTotalMS());
            jsonParams.put("correctMS", examResult.getCorrectMS());
            jsonParams.put("wrongMS", examResult.getWrongMS());
            jsonParams.put("marksMS", examResult.getMarksMS());

            jsonParams.put("totalGS", examResult.getTotalGS());
            jsonParams.put("correctGS", examResult.getCorrectGS());
            jsonParams.put("wrongGS", examResult.getWrongGS());
            jsonParams.put("marksGS", examResult.getMarksGS());

            jsonParams.put("totalICT", examResult.getTotalICT());
            jsonParams.put("correctICT", examResult.getCorrectICT());
            jsonParams.put("wrongICT", examResult.getWrongICT());
            jsonParams.put("marksICT", examResult.getMarksICT());


            Log.d("ExamResultDebug", "ExamResult data: " + jsonParams);
        } catch (JSONException e) {

            e.printStackTrace();
        }

        // Create a new StringRequest for the HTTP POST request
        StringRequest request = new StringRequest(Request.Method.POST, saveResultUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the server response if needed
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error responses
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return jsonParams.toString().getBytes();
            }
        };

        // Add the request to the RequestQueue to send the data to the server
        requestQueue.add(request);
    }
}