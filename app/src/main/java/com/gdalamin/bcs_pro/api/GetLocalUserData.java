package com.gdalamin.bcs_pro.api;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GetLocalUserData {
    private Context context;

    public GetLocalUserData(Context context) {
        this.context = context;
    }

    public void fetchDataFromAPI(String userId, final APICallback callback) {
        String apiUrl = "https://emon.searchwizy.com/test2/testUserResult.php?apiKey=abc123&userId=" + userId;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Handle null values for integers
                            int totalCorrect = response.isNull("totalCorrect") ? 0 : response.getInt("totalCorrect");
                            int totalQuestions = response.isNull("totalQuestions") ? 0 : response.getInt("totalQuestions");
                            int totalWrong = response.isNull("totalWrong") ? 0 : response.getInt("totalWrong");
                            int totalNotAnswered = response.isNull("totalNotAnswered") ? 0 : response.getInt("totalNotAnswered");
                            int examCount = response.isNull("examCount") ? 0 : response.getInt("examCount");
                            int rank = response.isNull("rank") ? 0 : response.getInt("rank");

                            // Handle null value for double (averageMark)
                            double averageMark = response.isNull("averageMark") ? 0.0 : response.getDouble("averageMark") * 10;
                            int localUserMark = (int) Math.floor(averageMark);

                            // Handle null values for strings
                            String userName = response.isNull("userName") ? "" : response.getString("userName");
                            String userImgString = response.isNull("userImage") ? "" : response.getString("userImage");

                            callback.onFetchSuccess(totalCorrect, totalQuestions, totalWrong, totalNotAnswered,
                                    userName, examCount, rank, localUserMark, userImgString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onFetchFailure("Error parsing JSON response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        callback.onFetchFailure("API request failed");
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public interface APICallback {
        void onFetchSuccess(int totalCorrect, int totalQuestions, int totalWrong,
                            int totalNotAnswered, String userName, int examCount, int rank,
                            int localUserMark, String userImageString);

        void onFetchFailure(String errorMessage);
    }
}
