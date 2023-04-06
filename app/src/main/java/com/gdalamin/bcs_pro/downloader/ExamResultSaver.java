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

import java.util.HashMap;
import java.util.Map;

public class ExamResultSaver {

    private Context mContext;
    private String mSaveResultUrl;

    public ExamResultSaver(Context context, String saveResultUrl) {
        mContext = context;
        mSaveResultUrl = saveResultUrl;
    }

    public void saveResult(final String total, final String correct ,final String wrong
            ,final String mark,final String userId,String date) {
        StringRequest request=new StringRequest(Request.Method.POST, mSaveResultUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mContext,"Result saved",Toast.LENGTH_SHORT).show();

//                mContext.startActivity(new Intent(mContext,MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();
                param.put("total",total);
                param.put("correct",correct);
                param.put("wrong",wrong);
                param.put("mark",mark);
                param.put("userId",userId);
                param.put("date",date);

                return param;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(mContext);
        queue.add(request);
    }
}
