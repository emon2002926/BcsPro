package com.gdalamin.bcs_pro.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
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
import com.gdalamin.bcs_pro.Activity.TestQuestionBank;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.myadapter2;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.api.GetLocalUserData;
import com.gdalamin.bcs_pro.api.PreferencesUserInfo;
import com.gdalamin.bcs_pro.api.SharedPreferencesManagerAppLogic;
import com.gdalamin.bcs_pro.modelClass.modelForExam;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onesignal.OneSignal;

public class HomeFragment extends Fragment {



    RecyclerView recyclerView;
    TextView tvPractice,showAllCourse,CvQuestionBank,CvImportantQuestion;
    LinearLayout subjectBasedExam,letcureLayout,CvQuizLayout;
    TextView tvAllExam;
    int tolatExamQuestion = 0;
    int LOGIC_FOR_ALL_SUBJECT_EXAM = 0;
    ShimmerFrameLayout shimmerFrameLayout;
    ScrollView scrollView;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView imageView1,imageView2,imageView3;
    String titleText;
    private boolean isConnected;
    private BroadcastReceiver networkReceiver;
    private boolean isDataProcessed = false; // Declare a boolean flag

    PreferencesUserInfo preferencesUserInfo;
    SharedPreferencesManagerAppLogic preferencesManager;
    Context context;

    String[] permissions = new String[]{
            Manifest.permission.POST_NOTIFICATIONS
    };
    boolean permissions_post_notification = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();
        preferencesManager = new SharedPreferencesManagerAppLogic(context);
        preferencesManager.clear();
        preferencesUserInfo = new PreferencesUserInfo(context);


        OneSignal.promptForPushNotifications();








        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                processdata();
            }
        });


        shimmerFrameLayout = view.findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();
        scrollView = view.findViewById(R.id.homeLayout);

        imageView3 = view.findViewById(R.id.imageView3);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView1 = view.findViewById(R.id.imageView1);
        showAllCourse = view.findViewById(R.id.showAllCourse);
        recyclerView = view.findViewById(R.id.recview2);
        CvQuizLayout = view.findViewById(R.id.CvQuizLayout);
        CvImportantQuestion = view.findViewById(R.id.CvImportantQuestion);
        letcureLayout = view.findViewById(R.id.lectureAndNots);
        tvAllExam = view.findViewById(R.id.tvAllExam);
        subjectBasedExam =view.findViewById(R.id.subject_based_exam);
        tvPractice = view.findViewById(R.id.tvPractice);
        CvQuestionBank = view.findViewById(R.id.CvQuestionBank);


        View.OnClickListener buttonClickListener = v -> {
            Intent intent;
            int subCode =0;
            int LOGIC =0;
            switch (v.getId()) {
                case R.id.CvQuizLayout:
                    // Handle button1 click
                    intent = new Intent(context, McqTestActivity.class);
                    intent.putExtra("selectedOption",10);
                    view.getContext().startActivity(intent);
                    break;
                case R.id.CvImportantQuestion:
                    // This gos  ImportantQuestion (Activity)
                     subCode = 5;
                    int LOGIC_FOR_ALL_SUBJECT_EXAM =0;
                    titleText = getResources().getString(R.string.importantQuestion);
                    preferencesManager.saveInt("LogicForExam",LOGIC_FOR_ALL_SUBJECT_EXAM);
                    preferencesManager.saveInt("subCode",subCode);

                    intent = new Intent(context, QuestionListActivity.class);
                    intent.putExtra("titleText",titleText);
                    v.getContext().startActivity(intent);
                    break;
                case R.id.lectureAndNots:
                    startActivity(new Intent(context, ActivityLectureAndNote.class));
                    break;
                case R.id.tvAllExam:
                    // this code sets up a BottomSheetDialog to display options for an exam
                    showExamChoser();
                    break;
                case R.id.subject_based_exam:
                    subCode = 2;
                    titleText = getResources().getString(R.string.subjectBasedExam) ;
                    preferencesManager.saveInt("subCode",subCode);
                    intent = new Intent(getContext(),AllBcsQuestionActivity.class);
                    intent.putExtra("titleText",titleText);
                    startActivity(intent);
                    break;
                case R.id.tvPractice:
                    subCode = 3;
                    titleText = getResources().getString(R.string.practice);
                    preferencesManager.saveInt("subCode",subCode);
                    intent = new Intent(v.getContext(), AllBcsQuestionActivity.class);
                    intent.putExtra("titleText",titleText);
                    v.getContext().startActivity(intent);
                    break;
                case R.id.CvQuestionBank:

                    titleText = getResources().getString(R.string.questionBank);
                    intent = new Intent(getContext(), TestQuestionBank.class);
                    intent.putExtra("titleText",titleText);
                    startActivity(intent);
                    break;
                case R.id.imageView1:
                case R.id.imageView2:
                case R.id.imageView3:
                case R.id.showAllCourse:
                    startActivity(new Intent(view.getContext(),ActivityAllCourse.class));

                    /*

                      This code for gatting notification permission

                    if (!permissions_post_notification){

                        requestPermissionsNotification();
                    }
                    else {
                        Toast.makeText(context,"Granted ",Toast.LENGTH_SHORT).show();
                    }

                 */
            }
        };

        CvQuizLayout.setOnClickListener(buttonClickListener);
        CvImportantQuestion.setOnClickListener(buttonClickListener);
        letcureLayout.setOnClickListener(buttonClickListener);
        tvAllExam.setOnClickListener(buttonClickListener);
        subjectBasedExam.setOnClickListener(buttonClickListener);
        tvPractice.setOnClickListener(buttonClickListener);
        CvQuestionBank.setOnClickListener(buttonClickListener);
        showAllCourse.setOnClickListener(buttonClickListener);
        imageView1.setOnClickListener(buttonClickListener);
        imageView2.setOnClickListener(buttonClickListener);
        imageView3.setOnClickListener(buttonClickListener);


        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

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
            Toast.makeText(context,"Please check your internet connection and try again",Toast.LENGTH_LONG).show();


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
                    Toast.makeText(context, "Internet connection is gone", Toast.LENGTH_SHORT).show();
                }
                isConnected = newIsConnected;
            }
        };
        context.registerReceiver(networkReceiver, intentFilter);
        // Check the initial network connectivity status
        isConnected = isConnected();


        String userId = preferencesUserInfo.getString("key_phone").trim();
        getUserProfileData(userId);


        return view;

    }

public void requestPermissionsNotification(){

        if (ContextCompat.checkSelfPermission(context,permissions[0]) == PackageManager.PERMISSION_GRANTED){
            permissions_post_notification = true;
        }else {
//            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)){
//                Log.d("Permission","inside the 1st don't allow");
//            }else {
//                Log.d("Permission","inside the 1st don't allow");
//
//            }
            requestPermissionLauncherNotification.launch(permissions[0]);
        }

}

private ActivityResultLauncher<String> requestPermissionLauncherNotification = 
        registerForActivityResult(new ActivityResultContracts.RequestPermission(),isGranted ->  {

            if (isGranted){
                permissions_post_notification = true;
            }else {
                permissions_post_notification = false;
                showPermissionDailog("Notification Permission");
            }

        });

public void  showPermissionDailog(String permission_dsc){

    new AlertDialog.Builder(getContext() ).setTitle("Alert for permission").setPositiveButton("Stting", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
            .show();

}
    public void showExamChoser(){

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDailogTheme);
        View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet, null);

        final LinearLayout option1Layout = bottomSheetView.findViewById(R.id.layout25Min);
        final LinearLayout option2Layout = bottomSheetView.findViewById(R.id.layout50Min);
        final LinearLayout option3Layout = bottomSheetView.findViewById(R.id.layout100Min);
        option1Layout.setBackgroundResource(R.drawable.round_back_selected_option);
        option2Layout.setBackgroundResource(R.drawable.round_back_selected_option);
        option3Layout.setBackgroundResource(R.drawable.round_back_selected_option);

        final ImageView icon1 = bottomSheetView.findViewById(R.id.option25Icon);
        final ImageView icon2 = bottomSheetView.findViewById(R.id.option50Icon);
        final ImageView icon3 = bottomSheetView.findViewById(R.id.option100Icon);
        final TextView startButton = bottomSheetView.findViewById(R.id.btnExamStart);
        final TextView closeButton = bottomSheetView.findViewById(R.id.btnCancal);


        View.OnClickListener onClickListener = v -> {
            switch (v.getId()){

                case R.id.layout25Min:
                    selectedOption(option1Layout, icon1);

                    icon2.setImageResource(R.drawable.round_back_white50_100);
                    icon3.setImageResource(R.drawable.round_back_white50_100);
                    tolatExamQuestion = 50;
                    LOGIC_FOR_ALL_SUBJECT_EXAM =50;
                    break;
                case R.id.layout50Min:
                    selectedOption(option2Layout, icon2);
                    icon1.setImageResource(R.drawable.round_back_white50_100);
                    icon3.setImageResource(R.drawable.round_back_white50_100);
                    tolatExamQuestion = 100;
                    LOGIC_FOR_ALL_SUBJECT_EXAM = 100;
                    break;
                case R.id.layout100Min:
                    selectedOption(option3Layout, icon3);
                    icon1.setImageResource(R.drawable.round_back_white50_100);
                    icon2.setImageResource(R.drawable.round_back_white50_100);
                    tolatExamQuestion = 200;
                    LOGIC_FOR_ALL_SUBJECT_EXAM = 200;
                    break;
                case R.id.btnExamStart:
                    if (tolatExamQuestion != 0) {
                        Intent intent = new Intent(context, ActivityExam.class);
                        titleText = "Overall Exam";
                        intent.putExtra("titleText",titleText);
                        v.getContext().startActivity(intent);
                        preferencesManager.saveInt("examQuestionNum",tolatExamQuestion);
                        preferencesManager.saveInt("LogicForExam",LOGIC_FOR_ALL_SUBJECT_EXAM);
                        bottomSheetDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Please select an option", Toast.LENGTH_SHORT).show();
                    }
                case R.id.btnCancal:
                    bottomSheetDialog.dismiss();
                    break;
            }
        };

        option1Layout.setOnClickListener(onClickListener);
        option2Layout.setOnClickListener(onClickListener);
        option3Layout.setOnClickListener(onClickListener);
        startButton.setOnClickListener(onClickListener);
        closeButton.setOnClickListener(onClickListener);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    public void getUserProfileData(String userId){
        GetLocalUserData apiFetcher = new GetLocalUserData(getContext());
        apiFetcher.fetchDataFromAPI(userId, new GetLocalUserData.APICallback() {

            @Override
            public void onFetchSuccess(int totalCorrect, int totalQuestions, int totalWrong, int totalNotAnswered, String userName, int examCount, int rank, int localUserMark, String userImageString) {


                preferencesUserInfo.saveString("name",userName);
                preferencesUserInfo.saveString("totalQuestions",String.valueOf(totalQuestions));
                preferencesUserInfo.saveString("wrongAnswer",String.valueOf(totalWrong));
                preferencesUserInfo.saveString("correctAnswer",String.valueOf(totalCorrect));
                preferencesUserInfo.saveString("notAnswred",String.valueOf(totalNotAnswered));
                preferencesUserInfo.saveString("totalExam",String.valueOf(examCount));
                preferencesUserInfo.saveString("localUserRank",String.valueOf(rank));
                preferencesUserInfo.saveInt("localUserPoint",localUserMark);
                preferencesUserInfo.saveString("userImage",String.valueOf(userImageString));

            }

            @Override
            public void onFetchFailure(String errorMessage) {
                // Handle the error message here

            }
        });
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

    private void selectedOption(View selectedOptionLayout , ImageView selectedOptionIcon) {

        selectedOptionIcon.setImageResource(R.drawable.black_dot);

    }
}