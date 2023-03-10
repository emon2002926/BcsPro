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

import com.gdalamin.bcs_pro.api.ApiKeys;
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

    RecyclerView recyclerView;
    CardView CvQuizLayout,CvQuestionBank,CvImportantQuestion,subjectBasedExam;
    TextView tvPractice,showAllCourse;

    CardView letcureLayout;
    TextView tvAllExam;
    int tolatExamQuestion = 0;
    int LOGIC_FOR_ALL_SUBJECT_EXAM = 0;

    ShimmerFrameLayout shimmerFrameLayout;
    ScrollView scrollView;


    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView imageView1,imageView2,imageView3;


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


        }



        shimmerFrameLayout = view.findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();
        scrollView = view.findViewById(R.id.homeLayout);
        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);



        imageView1.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(),ActivityAllCourse.class));
        });
        imageView2.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(),ActivityAllCourse.class));
        });
        imageView3.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(),ActivityAllCourse.class));
        });


        showAllCourse = view.findViewById(R.id.showAllCourse);
        showAllCourse.setOnClickListener(view1 -> {

            startActivity(new Intent(view.getContext(), ActivityAllCourse.class));
        });



        ///Swipe to refress layout



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



        CvImportantQuestion = view.findViewById(R.id.CvImportantQuestion);
        CvImportantQuestion.setOnClickListener(view12 -> {

            int subCode = 4;
            editor.putInt("subCode", subCode);
            editor.commit();
            Intent intent = new Intent(view12.getContext(), QuestionListActivity.class);
            //need to add important question php api to this link

            view12.getContext().startActivity(intent);
        });


        letcureLayout = view.findViewById(R.id.l4);
        letcureLayout.setOnClickListener(view1 -> {

//                SharedPreferences prefs = getContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
            startActivity(new Intent(getContext(), ActivityLectureAndNote.class));
        });

      /* his code sets up a BottomSheetDialog to display options for an exam, with three time options: 25 minutes, 50 minutes, and 100 minutes.
         When an option is selected, the selectedOption method is called to update the UI, and the number of questions for the exam is set accordingly.
         When the user clicks the "Start Exam" button, an Intent is created to start the ActivityExam activity, with the number of questions passed as an extra.
         The selected number of questions is also saved in SharedPreferences, and the BottomSheetDialog is dismissed.
         If the user clicks the "Cancel" button, the BottomSheetDialog is dismissed without starting the exam. */
        tvAllExam = view.findViewById(R.id.tvAllExam);
        tvAllExam.setOnClickListener(view14 -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDailogTheme);
            View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet, null);

            final RelativeLayout option1Layout = bottomSheetView.findViewById(R.id.layout25Min);
            final RelativeLayout option2Layout = bottomSheetView.findViewById(R.id.layout50Min);
            final RelativeLayout option3Layout = bottomSheetView.findViewById(R.id.layout100Min);
            final ImageView icon1 = bottomSheetView.findViewById(R.id.option25Icon);
            final ImageView icon2 = bottomSheetView.findViewById(R.id.option50Icon);
            final ImageView icon3 = bottomSheetView.findViewById(R.id.option100Icon);

            option1Layout.setOnClickListener(view15 -> {
                selectedOption(option1Layout, icon1);
                option2Layout.setBackgroundResource(R.drawable.round_back_white50_10);
                option3Layout.setBackgroundResource(R.drawable.round_back_white50_10);
                icon2.setImageResource(R.drawable.round_back_white50_100);
                icon3.setImageResource(R.drawable.round_back_white50_100);
                tolatExamQuestion = 50;
                LOGIC_FOR_ALL_SUBJECT_EXAM =50;
            });

            option2Layout.setOnClickListener(view1 -> {
                selectedOption(option2Layout, icon2);
                option1Layout.setBackgroundResource(R.drawable.round_back_white50_10);
                option3Layout.setBackgroundResource(R.drawable.round_back_white50_10);
                icon1.setImageResource(R.drawable.round_back_white50_100);
                icon3.setImageResource(R.drawable.round_back_white50_100);
                tolatExamQuestion = 100;
                LOGIC_FOR_ALL_SUBJECT_EXAM = 100;
            });

            option3Layout.setOnClickListener(view1 -> {
                selectedOption(option3Layout, icon3);
                option1Layout.setBackgroundResource(R.drawable.round_back_white50_10);
                option2Layout.setBackgroundResource(R.drawable.round_back_white50_10);
                icon1.setImageResource(R.drawable.round_back_white50_100);
                icon2.setImageResource(R.drawable.round_back_white50_100);
                tolatExamQuestion = 200;
                LOGIC_FOR_ALL_SUBJECT_EXAM = 200;
            });

            bottomSheetView.findViewById(R.id.btnExamStart).setOnClickListener(view1 -> {
                if (tolatExamQuestion != 0) {


                    Intent intent = new Intent(view1.getContext(), ActivityExam.class);
                    intent.putExtra("UserSelectedOption", "Overall exam");
                    view1.getContext().startActivity(intent);
                    editor.putInt("examQuestionNum", tolatExamQuestion);
                    editor.putInt("LogicForExam",LOGIC_FOR_ALL_SUBJECT_EXAM);

                    editor.commit();
                    bottomSheetDialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Please select an option", Toast.LENGTH_SHORT).show();
                }
            });

            bottomSheetView.findViewById(R.id.btnCancal).setOnClickListener(view1 -> bottomSheetDialog.dismiss());

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });




        subjectBasedExam =view.findViewById(R.id.subject_based_exam);
        subjectBasedExam.setOnClickListener(view1 -> {

            int LOGIC = 2;
            int subCode = 2;

            editor.putInt("logic", LOGIC);
            editor.putInt("subCode", subCode);
            editor.commit();
            startActivity(new Intent(getContext(), AllBcsQuestionActivity.class));

        });
        tvPractice = view.findViewById(R.id.tvPractice);
        tvPractice.setOnClickListener(view13 -> {

            int subCode = 3;
            int LOGIC = 2;
            editor.putInt("subCode", subCode);
            editor.putInt("logic", LOGIC);

            editor.commit();
            Intent intent = new Intent(view13.getContext(), AllBcsQuestionActivity.class);
            view13.getContext().startActivity(intent);

        });



        CvQuestionBank = view.findViewById(R.id.CvQuestionBank);
        CvQuestionBank.setOnClickListener(view15 -> {

            int LOGIC = 1;

            editor.putInt("logic", LOGIC);
            editor.commit();
            startActivity(new Intent(getContext(), AllBcsQuestionActivity.class));


        });


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

        String API_URL =  ApiKeys.API_URL_GENERAL+"apiNum=2";

        StringRequest request=new StringRequest(API_URL, new Response.Listener<String>() {
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