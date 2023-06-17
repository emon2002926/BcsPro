package com.gdalamin.bcs_pro.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.Activity.MainActivity;
import com.gdalamin.bcs_pro.Activity.ResultListActivity;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.resultAdapter;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.downloader.ImageUploader;
import com.gdalamin.bcs_pro.modelClass.ExamResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.toolbox.StringRequest;






public class DashBordFragment extends Fragment {
    RecyclerView recview;
    ShimmerFrameLayout shimmerFrameLayout;
    SharedPreferences sharedPreferences;
    TextView textViewDitels,
            userIdTv,totalExamTextView,totalQuestionTextView,wrongAnswerTextView,correctAnswerTextView,notAnswredTextView,userNameTextView;
    ProgressBar progressBarCorrect,progressBarWrong,progressBarNotAnswered;
    SharedPreferences sharedPreferences1;
    SharedPreferences.Editor editor;
    LinearLayout showResultList;
    ImageView profileImage,profileImageUpdate;
    int totalExam = 0;
    private static final String UPLOAD_URL = "https://emon.searchwizy.com/saveImage2.php?apiKey=abc123";
    private static final int REQUEST_CODE = 1;
    public String base64Image = "";

    public String userId = "";
    public String userName = "";
    public String base64LocalImage = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_dash_bord, container, false);

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.update_profile_layout);
        profileImageUpdate = dialog.findViewById(R.id.profileImageID);

        profileImage = view.findViewById(R.id.profileImageID);
        recview=view.findViewById(R.id.recview);
        shimmerFrameLayout = view.findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();
        textViewDitels = view.findViewById(R.id.ditels);
        totalExamTextView = view.findViewById(R.id.totalExamTv);
        totalQuestionTextView = view.findViewById(R.id.totalQuestionTv);
        wrongAnswerTextView = view.findViewById(R.id.wrongAnswerTv);
        correctAnswerTextView = view.findViewById(R.id.correctAnswerTv);
        notAnswredTextView = view.findViewById(R.id.notAnswredTv);
        userIdTv = view.findViewById(R.id.userIdTv);
        userNameTextView = view.findViewById(R.id.userNameTv);
        progressBarCorrect = view.findViewById(R.id.percentageProgressBarCorrect);
        progressBarWrong = view.findViewById(R.id.percentageProgressBarWrong);
        progressBarNotAnswered = view.findViewById(R.id.percentageProgressBarNotAnswred);

        showResultList = view.findViewById(R.id.resultListLayout);
        showResultList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ResultListActivity.class));
            }
        });

        sharedPreferences1 = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        base64LocalImage = sharedPreferences1.getString("profileImage","");

        if (!base64LocalImage.isEmpty()){
            Bitmap bitmap = convertBase64ToBitmap(base64LocalImage);
            profileImage.setImageBitmap(bitmap);
            profileImageUpdate.setImageBitmap(bitmap);
        }else {
            profileImage.setImageResource(R.drawable.test_profile_image);
        }

        if (account != null) {
            userId = account.getEmail();
             userName = account.getDisplayName();

            userIdTv.setText("ID: "+userId);
            if (base64LocalImage.isEmpty()){
                getUserProfileImage(userId);
            }
            userNameTextView.setText(userName);

            // Use the email for further processing
        } else {
            // User is not signed in
            userId = sharedPreferences1.getString("key_phone", "");
             userName = sharedPreferences1.getString("name", "");
            userIdTv.setText("ID: "+userId);
            userNameTextView.setText(userName);
            getUserProfileImage(userId);

            if (base64LocalImage.isEmpty()){
                getUserProfileImage(userId);
            }
            if (userId !=null && userName!=null){
                getUsernameFromAPI("abc123",userId);
            }
        }

        String totalQuestions = sharedPreferences1.getString("totalQuestions", "");
        if (totalQuestions != null && !totalQuestions.isEmpty()) {
            String totalQuestions2 = sharedPreferences1.getString("totalQuestions", "");
            String wrongAnswer = sharedPreferences1.getString("wrongAnswer", "");
            String correctAnswer = sharedPreferences1.getString("correctAnswer", "").trim();
            String notAnswered = sharedPreferences1.getString("notAnswred", "");
            String totalExam = sharedPreferences1.getString("totalExam", "");

            int correctAnswer1 = 0;
            int wrongAnswer1 = 0;
            int totalQuestions1 = 0;
            int notAnswered1 = 0;

            try {
                correctAnswer1 = Integer.parseInt(correctAnswer);
                wrongAnswer1 = Integer.parseInt(wrongAnswer);
                totalQuestions1 = Integer.parseInt(totalQuestions2);
                notAnswered1 = Integer.parseInt(notAnswered);
            } catch (NumberFormatException e) {
                // Handle the exception appropriately, such as logging an error or showing an error message
                e.printStackTrace();
            }

            float totalPercentageCorrect = ((float) correctAnswer1 / totalQuestions1) * 100;
            float totalPercentageWrong = ((float) wrongAnswer1 / totalQuestions1) * 100;
            float totalPercentageNotAnswered = ((float) notAnswered1 / totalQuestions1) * 100;

            totalQuestionTextView.setText(totalQuestions2);
            correctAnswerTextView.setText(correctAnswer + " (" + String.valueOf(String.format("%.2f", totalPercentageCorrect)) + "%)");
            wrongAnswerTextView.setText(wrongAnswer + " (" + String.valueOf(String.format("%.2f", totalPercentageWrong)) + "%)");
            notAnswredTextView.setText(notAnswered + " (" + String.valueOf(String.format("%.2f", totalPercentageNotAnswered)) + "%)");

            totalExamTextView.setText(totalExam);

            progressBarCorrect.setProgress(Math.round(totalPercentageCorrect));
            progressBarWrong.setProgress(Math.round(totalPercentageWrong));
            progressBarNotAnswered.setProgress(Math.round(totalPercentageNotAnswered));
        }
        profileImage.setOnClickListener(view1 -> {

            openUpdateProfileDialog();
        } );

        processData();
        return view;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            profileImageUpdate.setImageURI(selectedImage);
            base64Image = convertImageToBase64(selectedImage);
        }
    }
    public String convertImageToBase64(Uri imageUri) {
        try {
            InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Calculate the new dimensions while preserving the aspect ratio
            int maxWidth = 900;
            int maxHeight = 900;
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float ratio = (float) width / height;
            if (width > maxWidth || height > maxHeight) {
                if (ratio > 1.0f) {
                    width = maxWidth;
                    height = (int) (maxWidth / ratio);
                } else {
                    width = (int) (maxHeight * ratio);
                    height = maxHeight;
                }
            }

            // Resize the bitmap to the desired resolution
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void openUpdateProfileDialog( ) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.update_profile_layout);
        profileImageUpdate = dialog.findViewById(R.id.profileImageID);
        // Set dialog window attributes for full-screen
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView closeButton = dialog.findViewById(R.id.closeUpdateLayout);
        closeButton.setOnClickListener(view -> {
            dialog.dismiss();
        });

        if (!base64Image.isEmpty()){
            Bitmap bitmap = convertBase64ToBitmap(base64Image);
            profileImageUpdate.setImageBitmap(bitmap);

        }else if (!base64LocalImage.isEmpty()){
            Bitmap bitmap = convertBase64ToBitmap(base64LocalImage);
            profileImageUpdate.setImageBitmap(bitmap);

        }else {
            profileImageUpdate.setImageResource(R.drawable.test_profile_image);

        }


        TextInputEditText textInputEditTextName = dialog.findViewById(R.id.fullName);
        textInputEditTextName.setEnabled(false);
        textInputEditTextName.setText(userName);

        TextInputEditText textInputEditTextUserId = dialog.findViewById(R.id.userId);
        textInputEditTextUserId.setText(userId);
        textInputEditTextUserId.setEnabled(false);


        profileImageUpdate.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE);
        });


        ProgressBar progressBar =dialog.findViewById(R.id.progressBar4);
        TextView updateButton = dialog.findViewById(R.id.btnUpdate);

        updateButton.setOnClickListener(view -> {

            progressBar.setVisibility(View.VISIBLE);
            profileImageUpdate.setEnabled(false);
            updateButton.setEnabled(false);
            closeButton.setEnabled(false);

            if (!base64Image.isEmpty()){
                saveBase64ProfileImage(userId, base64Image, new SaveImageCallback() {
                    @Override
                    public void onImageSaved(int layoutState) {

                        int DAILOG_LAYOUT_STATE = layoutState;
                        if (DAILOG_LAYOUT_STATE == 200){

                            sharedPreferences1 = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences1.edit();
                            editor.putString("profileImage",base64Image);
                            editor.apply();

                            progressBar.setVisibility(View.GONE);
                            profileImageUpdate.setEnabled(true);
                            closeButton.setEnabled(true);
                            updateButton.setEnabled(true);
                            dialog.dismiss();
                        }
                    }
                });

            }else {
                closeButton.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                profileImageUpdate.setEnabled(true);
                updateButton.setEnabled(true);
                Toast.makeText(getContext(),"Please select a image ",Toast.LENGTH_SHORT).show();
            }


        });



        dialog.show();
    }
    public Bitmap convertBase64ToBitmap(String base64Image) {
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }


    private void saveBase64ProfileImage(String userId, String base64Image,SaveImageCallback callback) {

        StringRequest request = new StringRequest(Request.Method.POST, UPLOAD_URL+"&action=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                sharedPreferences1 = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();

                Bitmap bitmap = convertBase64ToBitmap(base64Image);
                profileImage.setImageBitmap(bitmap);
                profileImageUpdate.setImageBitmap(bitmap);
                int DAILOG_LAYOUT_STATE = 200;
                callback.onImageSaved(DAILOG_LAYOUT_STATE);
                editor.putString("profileImage",base64Image);
                editor.apply();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("base64Image", base64Image);
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }


    private void getUserProfileImage(String userId ) {
        String url = "https://emon.searchwizy.com/saveImage2.php?apiKey=abc123&action="+"3"+"&userId="+userId;
        sharedPreferences1 = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0) {
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                base64LocalImage = jsonObject.getString("base64Image");
                                // Use the base64Image string as needed

                                editor.putString("profileImage",base64LocalImage);
                                editor.apply();
                                Bitmap bitmap = convertBase64ToBitmap(base64LocalImage);
                                profileImage.setImageBitmap(bitmap);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


    private void getUsernameFromAPI(String apiKey, String phoneNumber) {
        // API URL

        String url = "https://emon.searchwizy.com/Test%20Api's/holder2.php?api_key=abc123&query=SELECT name FROM users WHERE phone = "+"'"+phoneNumber+"'";
        // Create a new JSONArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Handle the JSON response
                            JSONObject jsonObject = response.getJSONObject(0);
                            String username = jsonObject.getString("name");
                            userNameTextView.setText(username);
                            editor = sharedPreferences1.edit();
                            editor.putString("name",username);
                            editor.apply();

                            // TODO: Process the username as desired
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                    }
                });

        // Add the request to the Volley request queue
        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }


    public void processData() {
        // Create a new StringRequest to retrieve data from the API
        sharedPreferences = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("key_phone", "");

        String API_URL = ApiKeys.API_URL+"api/getData.php?apiKey=abc123&apiNum=6&userId=";

        StringRequest request = new StringRequest(API_URL+userId,
                response -> {
                    try {
                        Gson gson = new Gson();
                        ExamResult[] examResults = gson.fromJson(response, ExamResult[].class);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recview.setLayoutManager(linearLayoutManager);

                        resultAdapter adapter = new resultAdapter(examResults);

                        recview.setAdapter(adapter);

                        totalExam = adapter.getItemCount();

                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recview.setVisibility(View.INVISIBLE);
                        textViewDitels.setVisibility(View.GONE);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        textViewDitels.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);

                    }
                },
                error -> {

                });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }



    private BroadcastReceiver totalQuestionsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(resultAdapter.ACTION_TOTAL_QUESTIONS_CHANGED)) {
                int totalQuestions = intent.getIntExtra("totalQuestions", 0);
                int wrongAnswer = intent.getIntExtra("wrongAnswer", 0);
                int correctAnswer = intent.getIntExtra("correctAnswer",0);
                int notAnswered= intent.getIntExtra("notAnswred",0);

                float totalPercentageCorrect  =((float) correctAnswer/totalQuestions)*100;
                float totalPercentageWrong  =((float) wrongAnswer/totalQuestions)*100;
                float totalPercentageNotAnswred =((float) notAnswered/totalQuestions)*100;

                sharedPreferences1 = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);

                editor = sharedPreferences1.edit();
                editor.putString("totalQuestions",String.valueOf(totalQuestions));
                editor.putString("wrongAnswer",String.valueOf(wrongAnswer));
                editor.putString("correctAnswer",String.valueOf(correctAnswer));
                editor.putString("notAnswred",String.valueOf(notAnswered));
                editor.putString("totalExam",String.valueOf(totalExam));
                editor.apply();
                editor.commit();

//
                totalQuestionTextView.setText(String.valueOf(totalQuestions));
                wrongAnswerTextView.setText(String.valueOf(wrongAnswer)+" ("+String.valueOf(String.format("%.2f", totalPercentageWrong))+"%)");
                correctAnswerTextView.setText(String.valueOf(correctAnswer)+" ("+String.valueOf(String.format("%.2f", totalPercentageCorrect))+"%)");
                notAnswredTextView.setText(String.valueOf(notAnswered)+" ("+String.valueOf(String.format("%.2f", totalPercentageNotAnswred))+"%)");

                totalExamTextView.setText(String.valueOf(totalExam));


                progressBarCorrect.setProgress(Math.round(totalPercentageCorrect));
                progressBarWrong.setProgress(Math.round(totalPercentageWrong));
                progressBarNotAnswered.setProgress(Math.round(totalPercentageNotAnswred));
                // Handle the updated totalQuestions value here
            }
        }
    };



    public interface SaveImageCallback {
        void onImageSaved(int layoutState);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(resultAdapter.ACTION_TOTAL_QUESTIONS_CHANGED);
        requireContext().registerReceiver(totalQuestionsReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        requireContext().unregisterReceiver(totalQuestionsReceiver);
    }

}