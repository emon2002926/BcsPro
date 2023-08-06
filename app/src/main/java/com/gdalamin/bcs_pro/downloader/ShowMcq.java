package com.gdalamin.bcs_pro.downloader;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.Activity.ActivityExam;
import com.gdalamin.bcs_pro.adapter.myadapter;
import com.gdalamin.bcs_pro.modelClass.model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ShowMcq {

    private int NUM_OF_QUESTION;
    private ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView recview;
    private FloatingActionButton floatingActionButton;
    private TextView textViewTimer;
    private Context context;
    private TimerCallback timerCallback;
    private CountDownTimer countDownTimer;

    public ShowMcq(Context context, ShimmerFrameLayout shimmerFrameLayout,
                   RecyclerView recview, FloatingActionButton floatingActionButton,
                   TextView textViewTimer, int NUM_OF_QUESTION, TimerCallback timerCallback) {
        this.context = context;
        this.shimmerFrameLayout = shimmerFrameLayout;
        this.recview = recview;
        this.floatingActionButton = floatingActionButton;
        this.textViewTimer = textViewTimer;
        this.NUM_OF_QUESTION = NUM_OF_QUESTION;
        this.timerCallback = timerCallback;
    }

    public void processdata(String url) {

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recview.setVisibility(View.VISIBLE);

                        startTimer(NUM_OF_QUESTION * 30, textViewTimer);

                        floatingActionButton.setVisibility(View.VISIBLE);

                        GsonBuilder builder = new GsonBuilder().setLenient();
                        Gson gson = builder.create();

                        JsonReader reader = new JsonReader(new StringReader(response));
                        reader.setLenient(true);

                        model data[] = gson.fromJson(reader, model[].class);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,
                                LinearLayoutManager.VERTICAL, false);

                        recview.setLayoutManager(linearLayoutManager);
                        myadapter adapter = new myadapter(data);
                        ActivityExam activityExam = new ActivityExam();

                        recview.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context,"Please check your internet connection and try again",Toast.LENGTH_LONG).show();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

    }

    private void startTimer(int maxTimerSeconds, TextView textViewTimer) {
        countDownTimer = new CountDownTimer(maxTimerSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long getHour = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                long getMinutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long getSecond = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                String generateTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", getHour,
                        getMinutes - TimeUnit.HOURS.toMinutes(getHour),
                        getSecond - TimeUnit.MINUTES.toSeconds(getMinutes));

                textViewTimer.setText(generateTime);

            }

            @Override
            public void onFinish() {
                if (timerCallback != null) {
                    timerCallback.onTimerFinish();
                }
            }
        };
        countDownTimer.start();
    }

    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            textViewTimer.setVisibility(View.GONE);
        }
    }

    public interface TimerCallback {
        void onTimerFinish();
    }
}
