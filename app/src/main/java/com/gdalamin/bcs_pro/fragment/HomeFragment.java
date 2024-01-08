package com.gdalamin.bcs_pro.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.Activity.ActivityExam;
import com.gdalamin.bcs_pro.Activity.McqTestActivity;
import com.gdalamin.bcs_pro.Activity.QuestionListActivity;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.ViewModel.SharedViewModel;
import com.gdalamin.bcs_pro.adapter.myadapter2;
import com.gdalamin.bcs_pro.api.ApiKeys;
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
        swipeRefreshLayout.setOnRefreshListener(this::getLiveExamDetails);

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
            int subCode;
            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
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
//                    viewModel.setTitleText(titleText);
//                    v.getContext().startActivity(new Intent(v.getContext(), QuestionListActivity.class));

                    preferencesManager.saveInt("LogicForExam",LOGIC_FOR_ALL_SUBJECT_EXAM);
                    preferencesManager.saveInt("subCode",subCode);
                    Intent intent22 = new Intent(view.getContext(), QuestionListActivity.class);
                    intent22.putExtra("titleText",titleText);
                    view.getContext().startActivity(intent22);

                    break;
                case R.id.lectureAndNots:

                    titleText = getResources().getString(R.string.lectureAndNots);
                    viewModel.setTitleText(titleText);
                    replaceFragment(new CoursesFragment());
                    break;
                case R.id.imageView2:
                case R.id.imageView3:
                case  R.id.imageView1:
                case R.id.showAllCourse:

//                    viewModel.setTitleText(titleText);
//                    replaceFragment(new FragmentLectureAndNote());
                    titleText = getResources().getString(R.string.corses);
                    viewModel.setTitleText(titleText);
                    replaceFragment(new CoursesFragment());
                    break;
                case R.id.tvAllExam:
                    // this code sets up a BottomSheetDialog to display options for an exam
                    showExamChoosers();
                    break;
                case R.id.subject_based_exam:
                    subCode = 2;
                    titleText = getResources().getString(R.string.subjectBasedExam) ;
                    viewModel.setSubCode(2);
                    preferencesManager.saveInt("subCode",subCode);
                    viewModel.setTitleText(titleText);
                    replaceFragment(new SubjectFragment());
                    break;
                case R.id.tvPractice:
                    subCode = 3;
                    titleText = getResources().getString(R.string.practice);
                    preferencesManager.saveInt("subCode",subCode);
                    viewModel.setSubCode(3);
                    viewModel.setTitleText(titleText);
                    replaceFragment(new  SubjectFragment());
                    break;
                case R.id.CvQuestionBank:
                    titleText = getResources().getString(R.string.questionBank);
                    viewModel.setTitleText(titleText);
                    replaceFragment(new QuestionBankFragment());
                    break;
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


        ConnectivityManager cm = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            // The device is connected to the internet
            if (!isDataProcessed) {
                // Call the processData() method
                getLiveExamDetails();
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
                    getLiveExamDetails();
                } else if (isConnected && !newIsConnected) {
                    // Internet connection is gone
//                    Toast.makeText(context, "Internet connection is gone", Toast.LENGTH_SHORT).show();
                }
                isConnected = newIsConnected;
            }
        };
        context.registerReceiver(networkReceiver, intentFilter);
        isConnected = isConnected();

        getLiveExamDetails();
        return view;
    }
    private void replaceFragment(Fragment fragment, int enterAnim, int exitAnim, int popEnterAnim, int popExitAnim) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Set custom animations for fragment transition
        transaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Overload the method with a version that uses default animations
    private void replaceFragment(Fragment fragment) {
        // Call the method with default animations (you can change these defaults)
        replaceFragment(fragment, R.anim.default_enter_anim, R.anim.default_exit_anim, R.anim.default_pop_enter_anim, R.anim.default_pop_exit_anim);
    }

    public void showExamChoosers(){

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
                    selectedOption(icon1);
                    icon2.setImageResource(R.drawable.round_back_white50_100);
                    icon3.setImageResource(R.drawable.round_back_white50_100);
                    tolatExamQuestion = 50;
                    LOGIC_FOR_ALL_SUBJECT_EXAM =50;
                    break;
                case R.id.layout50Min:
                    selectedOption(icon2);
                    icon1.setImageResource(R.drawable.round_back_white50_100);
                    icon3.setImageResource(R.drawable.round_back_white50_100);
                    tolatExamQuestion = 100;
                    LOGIC_FOR_ALL_SUBJECT_EXAM = 100;
                    break;
                case R.id.layout100Min:
                    selectedOption(icon3);
                    icon1.setImageResource(R.drawable.round_back_white50_100);
                    icon2.setImageResource(R.drawable.round_back_white50_100);
                    tolatExamQuestion = 200;
                    LOGIC_FOR_ALL_SUBJECT_EXAM = 200;
                    break;
                case R.id.btnExamStart:
                    if (tolatExamQuestion != 0) {
                        Intent intent = new Intent(context, ActivityExam.class);
                        titleText = getResources().getString(R.string.overAllExam);
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



    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister the BroadcastReceiver when the fragment is destroyed
        requireActivity().unregisterReceiver(networkReceiver);
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void getLiveExamDetails()
    {

        Intent intent = new Intent("INTERNET_RESTORED");
        requireActivity().sendBroadcast(intent);
        String API_URL =  ApiKeys.API_URL+"api/getData.php?apiKey=abc123&apiNum=2";
        StringRequest request=new StringRequest(API_URL, response ->  {

            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            recyclerView.setVisibility(View.VISIBLE);
            GsonBuilder builder=new GsonBuilder();
            Gson gson=builder.create();
            modelForExam[] data2 =gson.fromJson(response,modelForExam[].class);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext()
                    ,LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(linearLayoutManager);
            myadapter2 adapter=new myadapter2(data2);
            recyclerView.setAdapter(adapter);
        }, error -> swipeRefreshLayout.setRefreshing(false)
        );
        RequestQueue queue= Volley.newRequestQueue(recyclerView.getContext());
        queue.add(request);
    }

    private void selectedOption( ImageView selectedOptionIcon) {

        selectedOptionIcon.setImageResource(R.drawable.black_dot);

    }
}