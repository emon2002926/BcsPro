package com.gdalamin.bcs_pro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.gdalamin.bcs_pro.adapter.TestmyadapterForAllbcs;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
public class QuestionBankFragment extends Fragment {
    RecyclerView recyclerView;
    ImageView imageBackButton;
    ShimmerFrameLayout shimmerFrameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_question_bank, container, false);

        recyclerView = view.findViewById(R.id.recview33);
        shimmerFrameLayout = view.findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getTitleText().observe(getViewLifecycleOwner(), titleText -> {
            // Now, you have the data in the titleText variable
        });

        imageBackButton = view.findViewById(R.id.backButton);
        imageBackButton.setOnClickListener(view1 -> {
            getActivity().onBackPressed();

        });

        String url2 = ApiKeys.API_WITH_SQL+"&query=SELECT * FROM other WHERE text <> '' ORDER BY id ;";
        getQuestionList(url2);


        return view;

    }


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
            TestmyadapterForAllbcs adapter=new TestmyadapterForAllbcs(data);
            recyclerView.setAdapter(adapter);
            recyclerView.setVerticalScrollBarEnabled(true); // Show the scrollbar
        }, error -> {

        });

        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);

    }

}