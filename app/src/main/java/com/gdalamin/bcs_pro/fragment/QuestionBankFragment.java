package com.gdalamin.bcs_pro.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.AdapterForShowAllBcsYearList;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
public class QuestionBankFragment extends Fragment {

    // UI Components
    RecyclerView recyclerView;
    ImageView imageBackButton;
    ShimmerFrameLayout shimmerFrameLayout;

    // Data Loading Parameters
    private int currentPage = 1;
    private boolean isLoading = false;
    private AdapterForShowAllBcsYearList adapter;

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

        // Handle back button click
        imageBackButton.setOnClickListener(view1 -> getActivity().onBackPressed());

        // Initialize RecyclerView and its adapter
        adapter = new AdapterForShowAllBcsYearList(new ModelForLectureAndAllQuestion[0]);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // Prefetch initial data
        prefetchInitialData();

        // Listen for scroll events to load more data
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Check if the user is close to the end of the list to prefetch more data
                handleScroll();
            }
        });


        return view;
    }

    // Method to prefetch initial data on app launch
    private void prefetchInitialData() {
        String apiKey = "abc123";
        String apiNum = "1";
        String url = "https://www.emon.pixatone.com/Test%20Api%27s/getYearName.php?apiKey=" +
                apiKey + "&apiNum=" + apiNum + "&page=" + currentPage;

        getQuestionList(url);
    }

    // Method to handle scroll events and load more data if needed
    private void handleScroll() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        int bufferItemCount = 20; // Adjust the buffer as needed

        if (!isLoading && (visibleItemCount + firstVisibleItemPosition + bufferItemCount) >= totalItemCount
                && firstVisibleItemPosition >= 0) {
            // User is very close to the end of the list, prefetch more data
            isLoading = true;
            currentPage++;

            String apiKey = "abc123";
            String apiNum = "1";
            String url = "https://www.emon.pixatone.com/Test%20Api%27s/getYearName.php?apiKey=" +
                    apiKey + "&apiNum=" + apiNum + "&page=" + currentPage;

            getQuestionList(url);
        }
    }

    // Method to initiate API call and get question list
    public void getQuestionList(String url) {
        // Process the API response
        StringRequest request = new StringRequest(url, this::processApiResponse, error -> {
            isLoading = false;
            Log.e("QuestionBankFragment", "Error fetching data: " + error.getMessage());
            // Handle the error appropriately, e.g., display an error message to the user
        });

        RequestQueue queue = Volley.newRequestQueue(requireContext().getApplicationContext());
        queue.add(request);
    }

    // Method to process the API response and update UI
    private void processApiResponse(String response) {
        // Process the API response as usual

        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        ModelForLectureAndAllQuestion[] newData = gson.fromJson(response, ModelForLectureAndAllQuestion[].class);

        if (currentPage == 1) {
            // Set data for the first page
            adapter.setData(Arrays.asList(newData));
        } else if (newData.length > 0) {
            // Add items for subsequent pages
            adapter.addItems(Arrays.asList(newData));
        } else {
            // Mark that there is no more data to load
            isLoading = false;
        }

        isLoading = false; // Reset the loading flag
    }
}