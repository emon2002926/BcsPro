package com.gdalamin.bcs_pro.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.adapterForShowAllBcsQuestion;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.api.CacheManager;
import com.gdalamin.bcs_pro.api.GetNetworkData;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class QuestionBankFragment extends Fragment {

    // UI Components
    RecyclerView recyclerView;
    ImageView imageBackButton;
    ShimmerFrameLayout shimmerFrameLayout;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_bank, container, false);

        // Initialize UI components
        recyclerView = view.findViewById(R.id.recview33);
        shimmerFrameLayout = view.findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        imageBackButton = view.findViewById(R.id.backButton);
        imageBackButton.setContentDescription("Navigate back");
        context = view.getContext();

        // Handle back button click
        imageBackButton.setOnClickListener(view1 -> getActivity().onBackPressed());


        String API_URL = ApiKeys.API_WITH_SQL+"&query=SELECT * FROM other WHERE text <> '' ORDER BY id ;";

        GetNetworkData networkData = new GetNetworkData(API_URL);
        CacheManager cacheManager = new CacheManager("CACHE_KEY_FOR_OLDER_BCS_EXAM");
        String response2 = cacheManager.getFromCache(context);
        if (response2 != null && !response2.isEmpty()){
            updateUI(response2);
        }else {
            networkData.getLiveExamDetails(context,((response, error) -> {
                cacheManager.saveToCache(context,response);
                updateUI(response);
            }));
        }

        return view;
    }


    private void updateUI(String response){

        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        ModelForLectureAndAllQuestion[] data =gson.fromJson(response, ModelForLectureAndAllQuestion[].class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context
                ,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);
        adapterForShowAllBcsQuestion adapter=new adapterForShowAllBcsQuestion(data);
        recyclerView.setAdapter(adapter);
        recyclerView.setVerticalScrollBarEnabled(true); // Show the scrollbar
    }


    /*
    public void getQuestionList(String url)
    {

        StringRequest request=new StringRequest(url, response -> {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            GsonBuilder builder=new GsonBuilder();
            Gson gson=builder.create();
            ModelForLectureAndAllQuestion[] data =gson.fromJson(response, ModelForLectureAndAllQuestion[].class);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext()
                    ,LinearLayoutManager.VERTICAL,false);

            recyclerView.setLayoutManager(linearLayoutManager);
            adapterForShowAllBcsQuestion adapter=new adapterForShowAllBcsQuestion(data);
            recyclerView.setAdapter(adapter);
            recyclerView.setVerticalScrollBarEnabled(true); // Show the scrollbar
        }, error -> {

        });

        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);

    }


     */

}