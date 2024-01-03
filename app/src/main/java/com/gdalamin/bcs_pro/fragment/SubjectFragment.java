package com.gdalamin.bcs_pro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class SubjectFragment extends Fragment {

    RecyclerView recyclerView;
    ImageView imageBackButton;
    ShimmerFrameLayout shimmerFrameLayout;

    TextView titleTv;
    SharedViewModel viewModel;
    SharedViewModel viewModel1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_subject, container, false);

        recyclerView = view.findViewById(R.id.recview3);
        shimmerFrameLayout = view.findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);


        imageBackButton = view.findViewById(R.id.backButton);
        imageBackButton.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });
        titleTv = view.findViewById(R.id.titleTv);

        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getTitleText().observe(getViewLifecycleOwner(), titleText -> titleTv.setText(titleText));

        String url2 = ApiKeys.API_WITH_SQL+"&query=SELECT * FROM other WHERE subjects <> '' ORDER BY id DESC LIMIT 10  ;";
        getSubjectName(url2);

        return view;
    }
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
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapterForAllSubjectExam adapter=new adapterForAllSubjectExam(data);
                    recyclerView.setAdapter(adapter);
                } else if (subCode == 3) {
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapterForAllSubject adapter=new adapterForAllSubject(data,viewModel);
                    recyclerView.setAdapter(adapter);
                }
            });}
        , error -> {

        }
        );

        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);

    }


}