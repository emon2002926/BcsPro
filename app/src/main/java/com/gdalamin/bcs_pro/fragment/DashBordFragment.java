package com.gdalamin.bcs_pro.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.resultAdapter;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.modelClass.ExamResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DashBordFragment extends Fragment {


    private int totalQuestions;


    RecyclerView recview;

    ShimmerFrameLayout shimmerFrameLayout;

    SharedPreferences sharedPreferences;
    TextView textViewDitels,
            userIdTv,totalExamTextView,totalQuestionTextView,wrongAnswerTextView,correctAnswerTextView,notAnswredTextView;

    ProgressBar progressBarCorrect,progressBarWrong,progressBarNotAnswered;

    int totalExam = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash_bord, container, false);



        recview=view.findViewById(R.id.recview);
        shimmerFrameLayout = view.findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();
        textViewDitels = view.findViewById(R.id.ditels);
        totalExamTextView = view.findViewById(R.id.totalExamTv);
        totalQuestionTextView = view.findViewById(R.id.totalQuestionTv);
        wrongAnswerTextView = view.findViewById(R.id.wrongAnswerTv);
        correctAnswerTextView = view.findViewById(R.id.correctAnswerTv);
        notAnswredTextView = view.findViewById(R.id.notAnswredTv);
        userIdTv = view.findViewById(R.id.userIdTv);

        progressBarCorrect = view.findViewById(R.id.percentageProgressBarCorrect);
        progressBarWrong = view.findViewById(R.id.percentageProgressBarWrong);
        progressBarNotAnswered = view.findViewById(R.id.percentageProgressBarNotAnswred);


        SharedPreferences sharedPreferences1;
        sharedPreferences1 = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        String userId = sharedPreferences1.getString("key_phone", "");

        if (userId !=null){
            userIdTv.setText("ID: "+userId);
        }
        Log.d("jjfdkgk",userId);
        processData();

 //https://learnbcs.xyz/Test%20Api's/holder.php?api_key=abc123&query=SELECT name FROM users WHERE phone = '1234'

        getUsernameFromAPI("abc123","1234");

        return view;

    }



    private void getUsernameFromAPI(String apiKey, String phoneNumber) {
        // API URL
        String apiUrl = "https://learnbcs.xyz/Test%20Api's/holder.php";

        // Create the full URL with query parameters
//        String url = apiUrl + "?api_key=" + apiKey + "&query=" + "SELECT%20name%20FROM%20users%20WHERE%20phone%20%3D%20%27" + phoneNumber + "%27";
        String url = "https://learnbcs.xyz/Test%20Api's/holder.php?api_key=abc123&query=SELECT name FROM users WHERE phone = "+"'"+phoneNumber+"'";

        // Create a new JSONArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Handle the JSON response
                            JSONObject jsonObject = response.getJSONObject(0);
                            String username = jsonObject.getString("name");
                            Log.d("usernamefh",username);
                            // TODO: Process the username as desired
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                    }
                });

        // Add the request to the Volley request queue
        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }


    public void processData() {
        // Create a new StringRequest to retrieve data from the API
        sharedPreferences = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("key_phone", "");

        String API_URL = ApiKeys.API_URL+"api/getData.php?apiKey=abc123&apiNum=6&userId=";

        StringRequest request = new StringRequest(API_URL+userId,
                response -> {
                    try {
                        Gson gson = new Gson();
                        ExamResult[] examResults = gson.fromJson(response, ExamResult[].class);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recview.setLayoutManager(linearLayoutManager);

                        resultAdapter adapter = new resultAdapter(examResults);

                        recview.setAdapter(adapter);



                        totalExam = adapter.getItemCount();


                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recview.setVisibility(View.INVISIBLE);
                        textViewDitels.setVisibility(View.GONE);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        textViewDitels.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);

                    }
                },
                error -> {

                });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }



    private BroadcastReceiver totalQuestionsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(resultAdapter.ACTION_TOTAL_QUESTIONS_CHANGED)) {
                int totalQuestions = intent.getIntExtra("totalQuestions", 0);
                int wrongAnswer = intent.getIntExtra("wrongAnswer", 0);
                int correctAnswer = intent.getIntExtra("correctAnswer",0);
                int notAnswered= intent.getIntExtra("notAnswred",0);


                float totalPercentageCorrect  =((float) correctAnswer/totalQuestions)*100;
                float totalPercentageWrong  =((float) wrongAnswer/totalQuestions)*100;
                float totalPercentageNotAnswred =((float) notAnswered/totalQuestions)*100;


                totalQuestionTextView.setText(String.valueOf(totalQuestions));
                wrongAnswerTextView.setText(String.valueOf(wrongAnswer)+" ("+String.valueOf(String.format("%.2f", totalPercentageWrong))+"%)");
                correctAnswerTextView.setText(String.valueOf(correctAnswer)+" ("+String.valueOf(String.format("%.2f", totalPercentageCorrect))+"%)");
                notAnswredTextView.setText(String.valueOf(notAnswered)+" ("+String.valueOf(String.format("%.2f", totalPercentageNotAnswred))+"%)");
                totalExamTextView.setText(String.valueOf(totalExam));


                progressBarCorrect.setProgress(Math.round(totalPercentageCorrect));
                progressBarWrong.setProgress(Math.round(totalPercentageWrong));
                progressBarNotAnswered.setProgress(Math.round(totalPercentageNotAnswred));
                // Handle the updated totalQuestions value here
            }
        }
    };



    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(resultAdapter.ACTION_TOTAL_QUESTIONS_CHANGED);
        requireContext().registerReceiver(totalQuestionsReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        requireContext().unregisterReceiver(totalQuestionsReceiver);
    }

}