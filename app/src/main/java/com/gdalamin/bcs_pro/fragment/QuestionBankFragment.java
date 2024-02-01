package com.gdalamin.bcs_pro.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
import com.gdalamin.bcs_pro.adapter.adapterForShowAllBcsQuestion;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

public class QuestionBankFragment extends Fragment {
    RecyclerView recyclerView;
    ImageView imageBackButton;
    ShimmerFrameLayout shimmerFrameLayout;

    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private adapterForShowAllBcsQuestion adapter;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_question_bank, container, false);

        recyclerView = view.findViewById(R.id.recview33);
        shimmerFrameLayout = view.findViewById(R.id.shimer);
        progressBar = view.findViewById(R.id.progressBar);

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



        adapter = new adapterForShowAllBcsQuestion(new ModelForLectureAndAllQuestion[0]);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                int bufferItemCount = 10; // Adjust the buffer as needed

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition + bufferItemCount) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    // User is very close to the end of the list, prefetch more data
                    isLoading = true;
                    currentPage++;
                    String apiKey = "abc123";
                    String apiNum = "1";
                    String url = "https://www.emon.pixatone.com/Test%20Api%27s/testHolder.php?apiKey=" +
                            apiKey + "&apiNum=" + apiNum + "&page=" + currentPage;

                    getQuestionList(url);
                }
            }
        });

        // Prefetch initial data
        prefetchInitialData();


        return view;

    }

    private void prefetchInitialData() {
        // Load initial data on app launch
        String apiKey = "abc123";
        String apiNum = "1";
        String url = "https://www.emon.pixatone.com/Test%20Api%27s/testHolder.php?apiKey=" +
                apiKey + "&apiNum=" + apiNum + "&page=" + currentPage;

        getQuestionList(url);
    }



    public void getQuestionList(String url) {
        StringRequest request = new StringRequest(url, response -> {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            ModelForLectureAndAllQuestion[] newData = gson.fromJson(response, ModelForLectureAndAllQuestion[].class);

            if (currentPage == 1) {
                adapter.setData(Arrays.asList(newData)); // Set data for the first page
            } else if (newData.length > 0) {
                adapter.addItems(Arrays.asList(newData)); // Add items for subsequent pages
            } else {
                isLoading = false; // Mark that there is no more data to load
            }

            isLoading = false;

        }, error -> {
            isLoading = false;
        });

        RequestQueue queue = Volley.newRequestQueue(requireContext().getApplicationContext());
        queue.add(request);
    }


}