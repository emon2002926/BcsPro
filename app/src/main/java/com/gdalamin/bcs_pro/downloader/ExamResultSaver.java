package com.gdalamin.bcs_pro.downloader;

import android.content.Context;
import android.util.Log;
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("total", examResult.getTotal());
                param.put("correct", examResult.getCorrect());
                param.put("wrong", examResult.getWrong());
                param.put("mark", examResult.getMark());
                param.put("notAnswred",examResult.getNotAnswred());
                param.put("date", examResult.getDate());
                param.put("userId", examResult.getUserId().trim());

                param.put("totalIA",examResult.getTotalIA());
                param.put("correctIA",examResult.getCorrectIA());
                param.put("wrongIA",examResult.getWrongIA());
                param.put("marksIA",examResult.getMarksIA());

                param.put("totalBA",examResult.getTotalBA());
                param.put("correctBA",examResult.getCorrectBA());
                param.put("wrongBA",examResult.getWrongBA());
                param.put("marksBA",examResult.getMarksBA());

                param.put("totalB",examResult.getTotalB());
                param.put("correctB",examResult.getCorrectB());
                param.put("wrongB",examResult.getWrongB());
                param.put("marksB",examResult.getMarksB());

                param.put("totalMAV",examResult.getTotalMAV());
                param.put("correctMAV",examResult.getCorrectMAV());
                param.put("wrongMAV",examResult.getWrongMAV());
                param.put("marksMAV",examResult.getMarksMAV());

                param.put("totalML",examResult.getTotalML());
                param.put("correctML",examResult.getCorrectML());
                param.put("wrongML",examResult.getWrongML());
                param.put("marksML",examResult.getTotalML());

                param.put("totalEL",examResult.getTotalEL());
                param.put("correctEL",examResult.getCorrectEL());
                param.put("wrongEL",examResult.getWrongEL());
                param.put("marksEL",examResult.getMarksEL());

                param.put("totalG",examResult.getTotalG());
                param.put("correctG",examResult.getCorrectG());
                param.put("wrongG",examResult.getWrongG());
                param.put("marksG",examResult.getMarksG());

                param.put("totalMS",examResult.getTotalMS());
                param.put("correctMS",examResult.getCorrectMS());
                param.put("wrongMS",examResult.getWrongMS());
                param.put("marksMS",examResult.getMarksMS());

                param.put("totalGS",examResult.getTotalGS());
                param.put("correctGS",examResult.getCorrectGS());
                param.put("wrongGS",examResult.getWrongGS());
                param.put("marksGS",examResult.getMarksMS());

                param.put("totalICT",examResult.getTotalICT());
                param.put("correctICT",examResult.getCorrectICT());
                param.put("wrongICT",examResult.getWrongICT());
                param.put("marksICT",examResult.getMarksICT());


                Log.d("notAnswred44",examResult.getNotAnswred());


                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
