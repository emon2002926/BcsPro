package com.gdalamin.bcs_pro.api;

import android.content.Context;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;





public class GetNetworkData {
    private String API_URL;

    public GetNetworkData(String API_URL) {
        this.API_URL = API_URL;
    }

    public void getLiveExamDetails(Context context, NetworkCallback callback) {
        StringRequest request = new StringRequest(API_URL,
                response -> callback.onResponse(response, null),
                error -> callback.onResponse(null, error.getMessage())
        );

        Volley.newRequestQueue(context).add(request);
    }

    public  interface NetworkCallback {
        void onResponse(String response, String error);
    }
}
