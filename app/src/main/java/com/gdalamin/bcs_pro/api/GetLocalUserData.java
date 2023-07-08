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
//    PreferencesUserInfo preferencesUserInfo = new PreferencesUserInfo(context);

    public void fetchDataFromAPI(String userId, final APICallback callback) {
        // API endpoint URL
        String apiUrl = "https://emon.searchwizy.com/test2/testUserResult.php?apiKey=abc123&userId=" + userId;

        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Create a JSON request to fetch the data
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the JSON response
                            int totalCorrect = response.getInt("totalCorrect");
                            int totalQuestions = response.getInt("totalQuestions");
                            int totalWrong = response.getInt("totalWrong");
                            int totalNotAnswered = response.getInt("totalNotAnswered");
                            String userName = response.getString("userName");
                            String totalExamCount = response.getString("examCount");
                            String localUserRank = response.getString("rank");
                            double mark = response.getDouble("averageMark") * 10;
                            int localUserMark = (int) Math.floor(mark);
                            String userImgString = response.getString("userImage");



                            // Invoke the callback with the fetched values
                            callback.onFetchSuccess(totalCorrect, totalQuestions, totalWrong, totalNotAnswered,
                                    userName, totalExamCount,localUserRank,localUserMark,userImgString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Invoke the callback with an error message
                            callback.onFetchFailure("Error parsing JSON response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        error.printStackTrace();
                        // Invoke the callback with an error message
                        callback.onFetchFailure("API request failed");
                    }
                });

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

    // Callback interface to handle API fetch results
    public interface APICallback {
        void onFetchSuccess(int totalCorrect, int totalQuestions, int totalWrong,
                            int totalNotAnswered, String userName, String totalExamCount,String localUserRank,
                            int localUserMark,String userImageString);

        void onFetchFailure(String errorMessage);
    }
}