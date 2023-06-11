package com.gdalamin.bcs_pro.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.gdalamin.bcs_pro.adapter.myadapter2;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.api.SharedPreferencesManager;
import com.gdalamin.bcs_pro.modelClass.modelForExam;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class HomeFragment extends Fragment {


    private  final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = database.getReference();

    RecyclerView recyclerView;
    TextView tvPractice,showAllCourse,CvQuestionBank,CvImportantQuestion;
    LinearLayout subjectBasedExam,letcureLayout,CvQuizLayout;
    TextView tvAllExam;
    int tolatExamQuestion = 0;
    int LOGIC_FOR_ALL_SUBJECT_EXAM = 0;

    ShimmerFrameLayout shimmerFrameLayout;
    ScrollView scrollView;


    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    SwipeRefreshLayout swipeRefreshLayout;
    ImageView imageView1,imageView2,imageView3;

    String titleText;

    private boolean isConnected;
    private BroadcastReceiver networkReceiver;
    private boolean isDataProcessed = false; // Declare a boolean flag




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);


        SharedPreferencesManager preferencesManager = new SharedPreferencesManager(getActivity());
        preferencesManager.remove("examQuestionNum");



        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(view.getContext(), googleSignInOptions);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(view.getContext());
        if (account !=null){
            String email = account.getEmail();
            SharedPreferences sharedPreferences1= getActivity().getSharedPreferences("LoginInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putString("key_phone", email);
            editor.commit();
        }



//        getDataFromFirebase();



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

            int subCode = 5;
            int LOGIC_FOR_ALL_SUBJECT_EXAM =0;
            preferencesManager.saveInt("LogicForExam",LOGIC_FOR_ALL_SUBJECT_EXAM);
            preferencesManager.saveInt("subCode",subCode);

            Intent intent = new Intent(getActivity(), QuestionListActivity.class);

            titleText = "Important Question";
            intent.putExtra("titleText",titleText);

            view12.getContext().startActivity(intent);
        });


        letcureLayout = view.findViewById(R.id.l4);
        letcureLayout.setOnClickListener(view1 -> {

            startActivity(new Intent(getContext(), ActivityLectureAndNote.class));
        });

      /* his code sets up a BottomSheetDialog to display options for an exam, with three time options: 25 minutes, 50 minutes, and 100 minutes.
         When an option is selected, the selectedOption method is called to update the UI, and the number of questions for the exam is set accordingly.
         When the user clicks the "Start Exam" button, an Intent is created to start the ActivityExam activity, with the number of questions passed as an extra.
         The selected number of questions is also saved in SharedPreferences, and the BottomSheetDialog is dismissed.
         If the user clicks the "Cancel" button, the BottomSheetDialog is dismissed without starting the exam.

      */

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
                    titleText = "Overall Exam";
                    intent.putExtra("titleText",titleText);
                    view1.getContext().startActivity(intent);

                    preferencesManager.saveInt("examQuestionNum",tolatExamQuestion);
                    preferencesManager.saveInt("LogicForExam",LOGIC_FOR_ALL_SUBJECT_EXAM);

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
            titleText = "Subject Based Exam";

            preferencesManager.saveInt("logic",LOGIC);
            preferencesManager.saveInt("subCode",subCode);


            Intent intent = new Intent(getContext(),AllBcsQuestionActivity.class);
            intent.putExtra("titleText",titleText);
            startActivity(intent);

        });
        tvPractice = view.findViewById(R.id.tvPractice);
        tvPractice.setOnClickListener(view13 -> {

            int subCode = 3;
            int LOGIC = 2;
            titleText = "Practise MCQ";

            preferencesManager.saveInt("subCode",subCode);
            preferencesManager.saveInt("logic",LOGIC);


            Intent intent = new Intent(view13.getContext(), AllBcsQuestionActivity.class);
            intent.putExtra("titleText",titleText);
            view13.getContext().startActivity(intent);

        });


        CvQuestionBank = view.findViewById(R.id.CvQuestionBank);
        CvQuestionBank.setOnClickListener(view15 -> {

            int LOGIC = 1;
            titleText = "Question Bank";

            preferencesManager.saveInt("logic",LOGIC);
            Intent intent = new Intent(getContext(), AllBcsQuestionActivity.class);
            intent.putExtra("titleText",titleText);
            startActivity(intent);

        });

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                processdata();
            }
        });




        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        ////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // The device is connected to the internet
            if (!isDataProcessed) {
                // Call the processData() method
                processdata();
                isDataProcessed = true; // Set the boolean flag to true after calling the method
            }
        } else {
            // The device is not connected to the internet
            // Show an error message or perform some other action
            Toast.makeText(recyclerView.getContext(),"Please check your internet connection and try again",Toast.LENGTH_LONG).show();


        }

        // Register a BroadcastReceiver to listen for network connectivity changes
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean newIsConnected = isConnected();
                if (!isConnected && newIsConnected) {
                    // Internet connection is restored
                    processdata();
//                    Toast.makeText(getActivity(), "Internet connection is restored", Toast.LENGTH_SHORT).show();
                } else if (isConnected && !newIsConnected) {
                    // Internet connection is gone
                    Toast.makeText(getActivity(), "Internet connection is gone", Toast.LENGTH_SHORT).show();
                }
                isConnected = newIsConnected;
            }
        };
        getActivity().registerReceiver(networkReceiver, intentFilter);
        // Check the initial network connectivity status
        isConnected = isConnected();

        return view;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister the BroadcastReceiver when the fragment is destroyed
        getActivity().unregisterReceiver(networkReceiver);
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }



    public void processdata()
    {

        Intent intent = new Intent("INTERNET_RESTORED");
        getActivity().sendBroadcast(intent);

        String API_URL =  ApiKeys.API_URL+"api/getData.php?apiKey=abc123&apiNum=2";


        StringRequest request=new StringRequest(API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                recyclerView.setVisibility(View.VISIBLE);
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
                swipeRefreshLayout.setRefreshing(false);


            }
        }
        );


        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);

    }


    private void selectedOption(RelativeLayout selectedOptionLayout , ImageView selectedOptionIcon) {

        selectedOptionIcon.setImageResource(R.drawable.baseline_check_24);
        selectedOptionLayout.setBackgroundResource(R.drawable
                .round_back_selected_option);

    }
}