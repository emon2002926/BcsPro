package com.gdalamin.bcs_pro.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.resultAdapter;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.modelClass.resultModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashBordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBordFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    RecyclerView recview;

    ShimmerFrameLayout shimmerFrameLayout;

    SharedPreferences sharedPreferences;

    public DashBordFragment() {
        // Required empty public constructor
    }


    public static DashBordFragment newInstance(String param1, String param2) {
        DashBordFragment fragment = new DashBordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_bord, container, false);



        recview=view.findViewById(R.id.recview);
        shimmerFrameLayout = view.findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();




        processData();


        return view;

    }

    public void processData() {
        // Create a new StringRequest to retrieve data from the API
        sharedPreferences = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("key_phone", "");

        String API_URL = ApiKeys.API_URL_GENERAL+"apiNum=6&userId=";

        StringRequest request = new StringRequest(API_URL+userId,
                // On successful response, parse the JSON data into resultModel objects using Gson
                response -> {

                    Gson gson = new Gson();
                    resultModel[] data = gson.fromJson(response, resultModel[].class);

                    // Set the layout manager for the RecyclerView
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recview.setLayoutManager(linearLayoutManager);


                    // Set the adapter for the RecyclerView using the parsed data
                    resultAdapter adapter = new resultAdapter(data);
                    recview.setAdapter(adapter);

                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);

                    recview.setVisibility(View.VISIBLE);

                },

                // On error, display an error message using a Toast
                error -> Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show());

        // Add the request to a RequestQueue for execution
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}