package com.gdalamin.bcs_pro.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.LeaderbordAdapter;
import com.gdalamin.bcs_pro.modelClass.LeaderbordModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardFragment extends Fragment {


    RecyclerView recyclerView;

    private LeaderbordAdapter adapter;
    private List<LeaderbordModel> userMarksList;
    private  LottieAnimationView animationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        recyclerView = view.findViewById(R.id.recview);
         animationView = view.findViewById(R.id.animationView);
        animationView.setRepeatCount(LottieDrawable.INFINITE);
        animationView.playAnimation();





        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userMarksList = new ArrayList<>();
        adapter = new LeaderbordAdapter(userMarksList, getContext());
        recyclerView.setAdapter(adapter);

        fetchDataFromServer();

        return view;

    }

    private void fetchDataFromServer() {
        String url = "https://emon.searchwizy.com/getLeaderbord.php"; // Replace with your API endpoint URL

        animationView.playAnimation();
        // Create a cache directory for caching the response
        File cacheDir = new File(requireContext().getCacheDir(), "volley_cache");
        Cache cache = new DiskBasedCache(cacheDir, 10 * 1024 * 1024); // 10MB cache size

        // Create a network stack using the cache
        Network network = new BasicNetwork(new HurlStack());

        // Create a request queue with the cache and network stack
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");
                            if (status == 0) {
                                JSONArray marksArray = response.getJSONArray("marks");
                                for (int i = 0; i < marksArray.length(); i++) {
                                    JSONObject marksObj = marksArray.getJSONObject(i);
                                    String userId = marksObj.getString("userId");
                                    double averageMark = marksObj.getDouble("averageMark");
                                    int totalCorrect = marksObj.getInt("totalCorrect");
                                    int totalQuestions = marksObj.getInt("totalQuestions");
                                    int totalWrong = marksObj.getInt("totalWrong");
                                    int totalNotAnswered = marksObj.getInt("totalNotAnswered");
                                    int totalExamsTaken = marksObj.getInt("totalExamsTaken");
                                    String userName = marksObj.getString("userName");
                                    String userBase64ImageString = marksObj.getString("userImage");


                                    animationView.cancelAnimation();
                                    animationView.setVisibility(View.GONE);

                                    LeaderbordModel userMarks = new LeaderbordModel(userId, averageMark, totalCorrect, totalWrong, totalNotAnswered, totalExamsTaken,
                                            userName, userBase64ImageString,totalQuestions);
                                    userMarksList.add(userMarks);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                String message = response.getString("message");

                                // Create a Handler object
                                Handler handler = new Handler();
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
//                                        animationView.setVisibility(View.GONE);
                                    }
                                };
                                long delayMillis = 5000; // 2 seconds
                                handler.postDelayed(runnable, delayMillis);



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(requireContext(), "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();

                        Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                animationView.cancelAnimation();
                                animationView.setVisibility(View.GONE);
                            }
                        };
                        long delayMillis = 5000;
                        handler.postDelayed(runnable, delayMillis);


                    }
                });

        // Set the cache policy for the request
        request.setShouldCache(true);

        // Add the request to the request queue
        requestQueue.add(request);
    }
}