package com.gdalamin.bcs_pro.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.Activity.ActivityAllCourse;
import com.gdalamin.bcs_pro.Activity.ActivityExam;
import com.gdalamin.bcs_pro.Activity.ActivityLectureAndNote;
import com.gdalamin.bcs_pro.Activity.AllBcsQuestionActivity;
import com.gdalamin.bcs_pro.Activity.McqTestActivity;
import com.gdalamin.bcs_pro.Activity.QuestionListActivity;
import com.gdalamin.bcs_pro.R;

import com.gdalamin.bcs_pro.modelClass.modelForExam;
import com.gdalamin.bcs_pro.adapter.myadapter2;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

    private  static final String url="http://192.168.0.104/api2/getData.php?apiKey=abc123&apiNum=2";
    RecyclerView recyclerView;
    CardView CvQuizLayout,CvQuestionBank,CvImportantQuestion;
    TextView tvPractice,showAllCourse;

    CardView letcureLayout;
    TextView tvAllExam;
    int tolatExamQuestion = 0;

    ShimmerFrameLayout shimmerFrameLayout;
    ScrollView scrollView;


    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;




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

        sharedPreferences= getActivity().getSharedPreferences("totalQuestion", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();




        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(view.getContext(), gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(view.getContext());
        if (account !=null){
            String name = account.getDisplayName();
            String email = account.getEmail();
            SharedPreferences sharedPreferences1= getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putString("key_phone", email);
            editor.commit();

            Log.d("emailad",email);
        }



        shimmerFrameLayout = view.findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();
        scrollView = view.findViewById(R.id.homeLayout);

        showAllCourse = view.findViewById(R.id.showAllCourse);
        showAllCourse.setOnClickListener(view1 -> {

            startActivity(new Intent(view.getContext(), ActivityAllCourse.class));
        });



        ///Swipe to refress layout
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Perform your refresh logic here

                processdata();

                swipeRefreshLayout.setRefreshing(false);


            }
        });

        //For quiz activity
        recyclerView = view.findViewById(R.id.recview2);

        CvQuizLayout = view.findViewById(R.id.l7);
        CvQuizLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(view.getContext(), McqTestActivity.class);
                intent.putExtra("selectedOption",10);
                view.getContext().startActivity(intent);
            }
        });




        tvPractice = view.findViewById(R.id.tvPractice);
        tvPractice.setOnClickListener(view13 -> {

            Intent intent = new Intent(view13.getContext(), QuestionListActivity.class);
            intent.putExtra("allQuestion","http://192.168.0.104/api2/allQuestion.php");
            view13.getContext().startActivity(intent);

        });
        CvImportantQuestion = view.findViewById(R.id.CvImportantQuestion);

        CvImportantQuestion.setOnClickListener(view12 -> {
            Intent intent = new Intent(view12.getContext(), QuestionListActivity.class);
            //need to add important question php api to this link

            intent.putExtra("allQuestion","http://192.168.0.104/api2/allQuestion.php");
            view12.getContext().startActivity(intent);
        });


        letcureLayout = view.findViewById(R.id.l4);
        letcureLayout.setOnClickListener(view1 -> {

//                SharedPreferences prefs = getContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
            startActivity(new Intent(getContext(), ActivityLectureAndNote.class));
        });




        tvAllExam = view.findViewById(R.id.tvAllExam);
        tvAllExam.setOnClickListener(view14 -> {

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext()
                    ,R.style.BottomSheetDailogTheme);
            View bottomSheetView = LayoutInflater.from(getContext())
                    .inflate(R.layout.layout_bottom_sheet,(LinearLayout)
                            view14.findViewById(R.id.bottomSheetContainer));


            RelativeLayout option1Layout ,option2Layout,option3Layout;

            option1Layout= bottomSheetView.findViewById(R.id.layout25Min);
            option2Layout = bottomSheetView.findViewById(R.id.layout50Min);
            option3Layout = bottomSheetView.findViewById(R.id.layout100Min);


            ImageView icon1,icon2,icon3;
             icon1= bottomSheetView.findViewById(R.id.option25Icon);
             icon2= bottomSheetView.findViewById(R.id.option50Icon);
             icon3 = bottomSheetView.findViewById(R.id.option100Icon);


            option1Layout.setOnClickListener(view1413 -> {

                selectedOption(option1Layout,icon1);

                //gatting focus on this layout

                option2Layout.setBackgroundResource(R.drawable.round_back_white50_10);
                option3Layout.setBackgroundResource(R.drawable.round_back_white50_10);

                icon2.setImageResource(R.drawable.round_back_white50_100);
                icon3.setImageResource(R.drawable.round_back_white50_100);



                tolatExamQuestion =25;
            });

            option2Layout.setOnClickListener(view1412 -> {

                selectedOption(option2Layout,icon2);


                option1Layout.setBackgroundResource(R.drawable.round_back_white50_10);
                option3Layout.setBackgroundResource(R.drawable.round_back_white50_10);

                icon1.setImageResource(R.drawable.round_back_white50_100);
                icon3.setImageResource(R.drawable.round_back_white50_100);


                tolatExamQuestion= 50;

            });

            option3Layout.setOnClickListener(view141 -> {

                //select that  option layout
                selectedOption(option3Layout,icon3);
                option1Layout.setBackgroundResource(R.drawable.round_back_white50_10);
                option2Layout.setBackgroundResource(R.drawable.round_back_white50_10);

                icon1.setImageResource(R.drawable.round_back_white50_100);
                icon2.setImageResource(R.drawable.round_back_white50_100);


                tolatExamQuestion= 100 ;
            });

            bottomSheetView.findViewById(R.id.btnExamStart).setOnClickListener(view1414 -> {

                if (tolatExamQuestion !=0 ){

                    Intent intent = new Intent(view1414.getContext(), ActivityExam.class);
                    intent.putExtra("UserSelectedOption","Overall exam");
                    view1414.getContext().startActivity(intent);


                    editor.putInt("examQuestionNum",tolatExamQuestion );
                    editor.commit();
                    bottomSheetDialog.dismiss();


                }else {

                    Toast.makeText(getContext(), "Plz select a Option", Toast.LENGTH_SHORT).show();

                }
            });

              bottomSheetView.findViewById(R.id.btnCancal).setOnClickListener(view1415 -> bottomSheetDialog.dismiss());

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });


        CvQuestionBank = view.findViewById(R.id.CvQuestionBank);
        CvQuestionBank.setOnClickListener(view15 ->
                startActivity(new Intent(getContext(), AllBcsQuestionActivity.class)));
//                startActivity(new Intent(getContext(), ActivityTestResult.class)));

        if (isInternetAvailable()) {
            // Internet is available
            Log.d("intentt","yes");
        } else {
            // Internet is not available
            Log.d("intentt","no");

        }






        processdata();

        return view;

    }






    public void processdata()
    {

        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

                scrollView.setVisibility(View.VISIBLE);
                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                modelForExam data2[]=gson.fromJson(response,modelForExam[].class);

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
                Log.d("recyclerViewError",error.toString());
            }
        }
        );


        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);

    }


    private void selectedOption(RelativeLayout selectedOptionLayout , ImageView selectedOptionIcon) {

        selectedOptionIcon.setImageResource(R.drawable.chack);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_option);

    }
    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }





}