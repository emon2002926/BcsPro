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
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.resultAdapter;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.modelClass.ExamResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


public class DashBordFragment extends Fragment {


    private int totalQuestions;


    RecyclerView recview;

    ShimmerFrameLayout shimmerFrameLayout;

    SharedPreferences sharedPreferences;
    TextView textViewDitels,totalExamTextView,totalQuestionTextView;


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


        processData();


        return view;

    }

    private BroadcastReceiver totalQuestionsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(resultAdapter.ACTION_TOTAL_QUESTIONS_CHANGED)) {
                int totalQuestions = intent.getIntExtra("totalQuestions", 0);
                Log.d("djfkgkf",String.valueOf(totalQuestions));
                totalQuestionTextView.setText(String.valueOf(totalQuestions));
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



                        int totalQuestion = adapter.getItemCount();
                        totalQuestionTextView.setText(String.valueOf(totalQuestion));





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

}