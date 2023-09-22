package com.gdalamin.bcs_pro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.ViewModel.SharedViewModel;
import com.gdalamin.bcs_pro.adapter.AdapterForCourse;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.modelClass.ModelForLectureAndAllQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.NumberFormat;
import java.util.Locale;


public class CoursesFragment extends Fragment {

    RecyclerView recyclerView;
    CardView backBtn;
    TextView titleTV;


    private  static final String API_URL = ApiKeys.API_URL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses, container, false);


        recyclerView = view.findViewById(R.id.recviewLecture);
        titleTV = view.findViewById(R.id.title);


        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getTitleText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String titleText) {
                // Now, you have the data in the titleText variable

                titleTV.setText(titleText);
            }
        });
        backBtn = view.findViewById(R.id.card);
        backBtn.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });


        return view;
    }

    public void processdataForCource() {

        StringRequest request=new StringRequest(API_URL+"api/getData.php?apiKey=abc123&apiNum=4", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                ModelForLectureAndAllQuestion data[]=gson.fromJson(response, ModelForLectureAndAllQuestion[].class);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext()
                        ,2);

                recyclerView.setLayoutManager(gridLayoutManager);
                AdapterForCourse adapter=new AdapterForCourse(data);
                recyclerView.setAdapter(adapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),"Please check your internet connection and try again",Toast.LENGTH_LONG).show();

            }
        }
        );

        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);

    }

    public String convertToBengaliString(String numberStr) {
        try {
            double number = Double.parseDouble(numberStr);
            Locale bengaliLocale = new Locale("bn", "BD");
            NumberFormat bengaliNumberFormat = NumberFormat.getNumberInstance(bengaliLocale);
            return bengaliNumberFormat.format(number);
        } catch (NumberFormatException e) {
            e.printStackTrace(); // You can log or handle the error here
            return numberStr; // Return the original string as-is
        }
    }


    /*
    public void processdataForLecture()
    {
        StringRequest request=new StringRequest(ApiKeys.API_URL+"api/getData.php?apiKey=abc123&apiNum=3", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);

                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                ModelForLectureAndAllQuestion data[]=gson.fromJson(response, ModelForLectureAndAllQuestion[].class);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext()
                        ,LinearLayoutManager.VERTICAL,false);

                recyclerView.setLayoutManager(linearLayoutManager);
                LectureAndNotesAdapter adapter=new LectureAndNotesAdapter(data);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ActivityLectureAndNote.this,"Please check your internet connection and try again",Toast.LENGTH_LONG).show();
            }
        }
        );

        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);

    }

     */

}