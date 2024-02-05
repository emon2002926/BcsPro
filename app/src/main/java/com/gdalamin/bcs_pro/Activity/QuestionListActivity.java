package com.gdalamin.bcs_pro.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.AdapterForLoadMcqOther;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.api.SharedPreferencesManagerAppLogic;
import com.gdalamin.bcs_pro.dataClass.AppDatabase;
import com.gdalamin.bcs_pro.modelClass.model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuestionListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView;
    ImageView imageBackButton;
    FloatingActionButton showOrHideAnswerButton;
    ShimmerFrameLayout shimmerFrameLayout;
    private SharedPreferencesManagerAppLogic preferencesManager;
    int subCode;
    private String Older_Bcs_Question = "";
    private AppDatabase db;
    private String subjectName;
    private String apiWithSql = ApiKeys.API_WITH_SQL;
    private LinearLayout tryAgainLayout;
    private TextView retryBtn;
    private boolean mBooleanValue = false;
    private int currentPage = 1;
    private boolean isLoading = false;

    private AdapterForLoadMcqOther adapter2;

    int seed = new Random().nextInt(2000) + 1000;

    private String ACTION;
    private String encodedQuery = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        showOrHideAnswerButton = findViewById(R.id.btnShowAnswer);
        recyclerView = findViewById(R.id.recview);
        shimmerFrameLayout = findViewById(R.id.shimer);
        tryAgainLayout = findViewById(R.id.tryAgainLayout);
        retryBtn = findViewById(R.id.retryBtn);
//        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.topTv);
        imageBackButton = findViewById(R.id.backButton);

        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "questionTable").build();
        preferencesManager = new SharedPreferencesManagerAppLogic(QuestionListActivity.this);
        subCode = preferencesManager.getInt("subCode");
        Older_Bcs_Question = preferencesManager.getString("oldBcs");
        subjectName = preferencesManager.getString("bcsYearName");
        ACTION = preferencesManager.getString("Type_Of_Question_To_Load");

        imageBackButton.setOnClickListener(view -> onBackPressed());
        imageBackButton.setContentDescription("Navigate back");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titleText = extras.getString("titleText");
            textView.setText(titleText);
            //The key argument here must match that used in the other activity
        }

        shimmerFrameLayout.startShimmer();
        retryBtn.setOnClickListener(view -> {
            retryToGetData();
            new bgThreat().start();
        });

        new bgThreat().start();

    }
    private void retryToGetData(){
        tryAgainLayout.setVisibility(View.GONE);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
    }

    class bgThreat extends Thread {
        public void run() {
            super.run();
            // Initialize the database instance
            List<model> dataList = db.modelDao().getModelsByBatch(subjectName);
            if (Older_Bcs_Question.equals("Older_Bcs_Question")) {
                if (dataList != null && dataList.size() > 49) {
                    // Convert the list to an array
                    model[] data = dataList.toArray(new model[0]);
                    // Update UI on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateUi(data);
                        }
                    });
                }
                else {
                    String url2 = apiWithSql + "&query=SELECT * FROM question WHERE batch LIKE '" + subjectName + "' ORDER BY id DESC LIMIT 200";
                    getMCQuestions(url2);
                }
            } else if (Older_Bcs_Question.equals("")) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ACTION.equals("subject_Specific_Question")) {
                            String SUBJECT_NAME_CODE = preferencesManager.getString("subjectCode");
                            encodedQuery = null;
                            try {
                                encodedQuery = URLEncoder.encode("SELECT * FROM question WHERE subjects LIKE '"+SUBJECT_NAME_CODE+"'", "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                throw new RuntimeException(e);
                            }
                            testNewPagination(encodedQuery);
                        } else if (ACTION.equals("important_Question")) {
                            encodedQuery = null;
                            try {
                                encodedQuery = URLEncoder.encode("SELECT * FROM question ", "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                throw new RuntimeException(e);
                            }
                            testNewPagination(encodedQuery);
                        }
                    }
                });
            }
        }
    }



    private void testNewPagination(String encodedQuery) {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter2 = new AdapterForLoadMcqOther(new model[0]);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter2); // Set the adapter on the RecyclerView
        showOrHideAnswerButton.setOnClickListener(view -> {
            mBooleanValue = !mBooleanValue;
            adapter2.setBooleanValue(mBooleanValue);

            if (!mBooleanValue) {

                showOrHideAnswerButton.setImageResource(R.drawable.hidden);
                showOrHideAnswerButton.setContentDescription("Hide Answer");
            } else if (mBooleanValue) {
                showOrHideAnswerButton.setImageResource(R.drawable.view);
                showOrHideAnswerButton.setContentDescription("Show Answer");
            }
        });
        String API_URL = "https://www.emon.pixatone.com/Test%20Api%27s/testCustomQueryWithPagination.php" +
                "?apiKey=abc123" +
                "&apiNum=1" +
                "&pageNo=" + currentPage +
                "&query=" + encodedQuery +
                "&seed=" + seed;

        getQuestionList(API_URL);
        // Listen for scroll events to load more data
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Check if the user is close to the end of the list to prefetch more data
                handleScroll(encodedQuery);
            }
        });
    }


    private void handleScroll(String encodedQuery) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        int bufferItemCount = 65; // Adjust the buffer as needed

        if (!isLoading && (visibleItemCount + firstVisibleItemPosition + bufferItemCount) >= totalItemCount
                && firstVisibleItemPosition >= 0) {
            // User is very close to the end of the list, prefetch more data
            isLoading = true;
            currentPage++;

            String apiUrl = "https://www.emon.pixatone.com/Test%20Api%27s/testCustomQueryWithPagination.php" +
                    "?apiKey=abc123" +
                    "&apiNum=1" +
                    "&pageNo=" + currentPage +
                    "&query=" + encodedQuery +
                    "&seed=" + seed;

            getQuestionList(apiUrl);

        }
    }

    public void getQuestionList(String url) {
        // Process the API response
        StringRequest request = new StringRequest(url, response -> {

            processApiResponse(response);

        }, error -> {
            showOrHideAnswerButton.setVisibility(View.GONE);
            delayTryAgainLayout();
            isLoading = false;
            Log.e("QuestionBankFragment", "Error fetching data: " + error.getMessage());
            // Handle the error appropriately, e.g., display an error message to the user
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


    private void processApiResponse(String response) {
        // Process the API response as usual
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        showOrHideAnswerButton.setVisibility(View.VISIBLE);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();


        model[] newData = gson.fromJson(response, model[].class);
        if (currentPage == 1) {
            // Set data for the first page
            adapter2.setData(Arrays.asList(newData));
        } else if (newData.length > 0) {
            // Add items for subsequent pages
            adapter2.addItems(Arrays.asList(newData));

        } else {
            // Mark that there is no more data to load
            isLoading = false;
        }
        isLoading = false; // Reset the loading flag
    }


    private void getMCQuestions(String API_URL) {
        StringRequest request = new StringRequest(API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder().setLenient();
                Gson gson = builder.create();

                JsonReader reader = new JsonReader(new StringReader(response));
                reader.setLenient(true);
                model[] data = gson.fromJson(reader, model[].class);
                updateUi(data);
                class bgThreat1 extends Thread {
                    public void run() {
                        super.run();
                        db.modelDao().insertModels(Arrays.asList(data));
                    }
                }
                new bgThreat1().start();
            }

        }, error -> {
            delayTryAgainLayout();
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    public void delayTryAgainLayout() {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        tryAgainLayout.setVisibility(View.GONE);
        showOrHideAnswerButton.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        new Handler().postDelayed(() -> {
            tryAgainLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }, 5000);
    }

    private void updateUi(model[] model1) {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext()
                , LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterForLoadMcqOther adapter = new AdapterForLoadMcqOther(model1);
        recyclerView.setAdapter(adapter);

        showOrHideAnswerButton.setOnClickListener(view -> {
            mBooleanValue = !mBooleanValue;
            adapter.setBooleanValue(mBooleanValue);

            if (!mBooleanValue) {
                showOrHideAnswerButton.setImageResource(R.drawable.hidden);
                showOrHideAnswerButton.setContentDescription("Hide Answer");
            } else if (mBooleanValue) {
                showOrHideAnswerButton.setImageResource(R.drawable.view);
                showOrHideAnswerButton.setContentDescription("Show Answer");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preferencesManager.clear();
    }

    @Override
    public void onBackPressed() {

        preferencesManager.saveInt("logic", 1);
        super.onBackPressed();
    }
}