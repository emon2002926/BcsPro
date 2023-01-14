package com.example.recreatequize.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recreatequize.AllBcsQuestionActivity;
import com.example.recreatequize.McqTestActivity;
import com.example.recreatequize.QuestionListActivity;
import com.example.recreatequize.R;
import com.example.recreatequize.modelClass.model;
import com.example.recreatequize.adapter.myadapter2;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private  static final String url="http://192.168.0.103/api2/others.php";
    RecyclerView recyclerView;
    LinearLayout quizLayout,letcureLayout;
    TextView tvPractice;

    TextView tvAllExam;
    int selectedOption = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view =  inflater.inflate(R.layout.fragment_home, container, false);



        //For quiz activity
        recyclerView = view.findViewById(R.id.recview2);
        processdata();
        quizLayout = view.findViewById(R.id.l7);
        quizLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), McqTestActivity.class));
            }
        });




        tvPractice = view.findViewById(R.id.tvPractice);
        tvPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(view.getContext(), QuestionListActivity.class);
                intent.putExtra("image","http://192.168.0.103/api2/json_user_fetch.php");
                view.getContext().startActivity(intent);

            }
        });


        letcureLayout = view.findViewById(R.id.l4);
        letcureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                SharedPreferences prefs = getContext().getSharedPreferences("myPrefs", MODE_PRIVATE);

                startActivity(new Intent(getContext(), AllBcsQuestionActivity.class));
            }
        });




        tvAllExam = view.findViewById(R.id.tvAllExam);
        tvAllExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext()
                        ,R.style.BottomSheetDailogTheme);
                View bottomSheetView = LayoutInflater.from(getContext())
                        .inflate(R.layout.layout_bottom_sheet,(LinearLayout)
                                view.findViewById(R.id.bottomSheetContainer));
//                bottomSheetView.findViewById(R.id.buttonShare).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(getContext(), "Share....", Toast.LENGTH_SHORT).show();
//                        bottomSheetDialog.dismiss();
//                    }
//                });

                RelativeLayout option1Layout ,option2Layout,option3Layout;


                option1Layout= bottomSheetView.findViewById(R.id.layout25Min);
                option2Layout = bottomSheetView.findViewById(R.id.layout50Min);
                option3Layout = bottomSheetView.findViewById(R.id.layout100Min);


                ImageView icon1,icon2,icon3;
                 icon1= bottomSheetView.findViewById(R.id.option25Icon);
                 icon2= bottomSheetView.findViewById(R.id.option50Icon);
                 icon3 = bottomSheetView.findViewById(R.id.option100Icon);

                option1Layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(getContext(), "25MIn", Toast.LENGTH_SHORT).show();

                        selectedOption(option1Layout,icon1);

                        //gatting focus on this layout

                        option2Layout.setBackgroundResource(R.drawable.round_back_white50_10);
                        option3Layout.setBackgroundResource(R.drawable.round_back_white50_10);

                        icon2.setImageResource(R.drawable.round_back_white50_100);
                        icon3.setImageResource(R.drawable.round_back_white50_100);

                        selectedOption =25;


                    }
                });

                option2Layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(getContext(), "50MIn", Toast.LENGTH_SHORT).show();


                        selectedOption(option2Layout,icon2);


                        option1Layout.setBackgroundResource(R.drawable.round_back_white50_10);
                        option3Layout.setBackgroundResource(R.drawable.round_back_white50_10);

                        icon1.setImageResource(R.drawable.round_back_white50_100);
                        icon3.setImageResource(R.drawable.round_back_white50_100);

                        selectedOption= 50;

                    }
                });

                option3Layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "100MIn", Toast.LENGTH_SHORT).show();

                        selectedOption(option3Layout,icon3);
                        option1Layout.setBackgroundResource(R.drawable.round_back_white50_10);
                        option2Layout.setBackgroundResource(R.drawable.round_back_white50_10);

                        icon1.setImageResource(R.drawable.round_back_white50_100);
                        icon2.setImageResource(R.drawable.round_back_white50_100);
                        selectedOption= 100;
                    }
                });




                  bottomSheetView.findViewById(R.id.btnExamStart).setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {

                          if (selectedOption !=0){


                              Intent intent = new Intent(view.getContext(), McqTestActivity.class);
                              intent.putExtra("selectedOption",selectedOption);
                              view.getContext().startActivity(intent);
//                              Log.d("selectedOption",String.valueOf(selectedOption));


                          }else {

                              Toast.makeText(getContext(), "Plz select a Option", Toast.LENGTH_SHORT).show();

                          }
                      }
                  });




                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });






        return view;

    }




    private void selectedOption(RelativeLayout selectedOptionLayout , ImageView selectedOptionIcon) {



        selectedOptionIcon.setImageResource(R.drawable.chack);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_option);

    }


    public void processdata()
    {

        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                model data2[]=gson.fromJson(response,model[].class);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext()
                        ,LinearLayoutManager.HORIZONTAL,false);

                recyclerView.setLayoutManager(linearLayoutManager);
                myadapter2 adapter=new myadapter2(data2);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(recyclerView.getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }
        );


        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);

    }





}