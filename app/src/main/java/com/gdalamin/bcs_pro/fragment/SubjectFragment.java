package com.gdalamin.bcs_pro.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.ViewModel.SharedViewModel;
import com.gdalamin.bcs_pro.adapter.adapterForAllSubject;
import com.gdalamin.bcs_pro.adapter.adapterForAllSubjectExam;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.api.CacheManager;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;


public class SubjectFragment extends Fragment {

    RecyclerView recyclerView;
    ImageView imageBackButton;
    ShimmerFrameLayout shimmerFrameLayout;

    TextView titleTv;
    SharedViewModel viewModel;
    CacheManager cacheManager;
    private TextView retryButton;
    private LinearLayout tryAgainLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_subject, container, false);

        recyclerView = view.findViewById(R.id.recview3);
        shimmerFrameLayout = view.findViewById(R.id.shimer);
        imageBackButton = view.findViewById(R.id.backButton);
        retryButton = view.findViewById(R.id.retryBtn);
        tryAgainLayout = view.findViewById(R.id.tryAgainLayout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);


        tryAgainLayout.setVisibility(View.GONE);

        imageBackButton.setContentDescription("Navigate back");
        imageBackButton.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });
        titleTv = view.findViewById(R.id.titleTv);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getTitleText().observe(getViewLifecycleOwner(), titleText -> titleTv.setText(titleText));


        String API_URL = ApiKeys.API_WITH_SQL+"&query=SELECT * FROM other WHERE subjects <> '' ORDER BY id DESC LIMIT 10  ;";
        cacheManager = new CacheManager("CACHE_KEY_FOR_SHOW_SUBJECT_LIST");
        String response = cacheManager.getFromCache(view.getContext());

        if (response != null && !response.isEmpty()){
            updateUI(response);
        }else {
            getData(API_URL);
        }
        retryButton.setOnClickListener(v -> retryToGetData(API_URL));

        return view;
    }

    private void retryToGetData(String API_URL){
        tryAgainLayout.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        getData(API_URL);
    }

    private void getData(String API_URL){
        StringRequest request=new StringRequest(API_URL, response ->  {
            cacheManager.saveToCache(recyclerView.getContext(),response);
            updateUI(response);
        },
                error -> {
            delayTryAgainLayout();
            Log.d("error", Objects.requireNonNull(error.getMessage()));
        }
        );
        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);
    }

    public void updateUI(String response){
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        ModelForLectureAndAllQuestion[] data =gson.fromJson(response, ModelForLectureAndAllQuestion[].class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext()
                ,LinearLayoutManager.VERTICAL,false);

        viewModel.getSubCode().observe(getViewLifecycleOwner(),subCode ->
        {
            if (subCode == 2){
                // Subject based exam
                recyclerView.setLayoutManager(linearLayoutManager);
                adapterForAllSubjectExam adapter=new adapterForAllSubjectExam(data);
                recyclerView.setAdapter(adapter);
            } else if (subCode == 3) {
                recyclerView.setLayoutManager(linearLayoutManager);
                adapterForAllSubject adapter=new adapterForAllSubject(data);
                recyclerView.setAdapter(adapter);
            }
        });
    }


    public void delayTryAgainLayout() {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        tryAgainLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        new Handler().postDelayed(() -> {
            tryAgainLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }, 5000);
    }

    /*

    public void getSubjectName(String url) {
        StringRequest request=new StringRequest(url, response -> {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            GsonBuilder builder=new GsonBuilder();
            Gson gson=builder.create();
            ModelForLectureAndAllQuestion[] data =gson.fromJson(response, ModelForLectureAndAllQuestion[].class);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext()
                    ,LinearLayoutManager.VERTICAL,false);

            viewModel.getSubCode().observe(getViewLifecycleOwner(),subCode ->
            {
                if (subCode == 2){
                    // Subject based exam
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapterForAllSubjectExam adapter=new adapterForAllSubjectExam(data);
                    recyclerView.setAdapter(adapter);
                } else if (subCode == 3) {
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapterForAllSubject adapter=new adapterForAllSubject(data);
                    recyclerView.setAdapter(adapter);
                }
            });
            }
        , error -> {

        }
        );

        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);

    }

     */


}