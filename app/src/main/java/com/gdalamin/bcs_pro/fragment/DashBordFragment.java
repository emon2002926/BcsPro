package com.gdalamin.bcs_pro.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.PreferencesUserInfo;
import com.gdalamin.bcs_pro.api.ResultPref;

import java.text.NumberFormat;
import java.util.Locale;


public class DashBordFragment extends Fragment {
    ShimmerFrameLayout shimmerFrameLayout;
    TextView textViewDitels,
            userIdTv,totalExamTextView,totalQuestionTextView,wrongAnswerTextView,correctAnswerTextView,notAnswredTextView,userNameTextView;
    ProgressBar progressBarCorrect,progressBarWrong,progressBarNotAnswered;
    LinearLayout showResultList,fullProfileLayout;
    ImageView profileImage,profileImageUpdate;

    public String userId = "";
    public String userName = "";

    PreferencesUserInfo preferencesUserInfo;
    ResultPref resultPref;

  /*  TextView updateButton;
    ImageView closeButton;

    ProgressBar progressBar;
    public String base64LocalImage = "";
    public String base64Image = "";
    private static final int REQUEST_CODE = 1;

   */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_dash_bord, container, false);


       /*
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.update_profile_layout);
        profileImageUpdate = dialog.findViewById(R.id.profileImageID);

        */

//        profileImage = view.findViewById(R.id.profileImageID);
//
//        Picasso.get().load("https://graph.facebook.com/3272620626383464/picture?type=large").into(profileImage);
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(view.getContext());
//        if (account != null) {
//            // User is signed in
//            Uri profilePhotoUrl = account.getPhotoUrl();
//            if (profilePhotoUrl != null) {
//                Picasso.get().load(profilePhotoUrl).into(profileImage);
//            } else {
//                profileImage.setImageResource(R.drawable.test_profile_image);
//            }
//        } else {
//            profileImage.setImageResource(R.drawable.test_profile_image);
//        }


        shimmerFrameLayout = view.findViewById(R.id.shimer);
        shimmerFrameLayout.startShimmer();
        textViewDitels = view.findViewById(R.id.ditels);
        totalExamTextView = view.findViewById(R.id.totalExamTv);
        totalQuestionTextView = view.findViewById(R.id.totalQuestionTv);
        wrongAnswerTextView = view.findViewById(R.id.wrongAnswerTv);
        correctAnswerTextView = view.findViewById(R.id.correctAnswerTv);
        notAnswredTextView = view.findViewById(R.id.notAnswredTv);
        userIdTv = view.findViewById(R.id.userIdTv);
        userNameTextView = view.findViewById(R.id.userNameTvD);
        progressBarCorrect = view.findViewById(R.id.percentageProgressBarCorrect);
        progressBarWrong = view.findViewById(R.id.percentageProgressBarWrong);
        progressBarNotAnswered = view.findViewById(R.id.percentageProgressBarNotAnswred);


        preferencesUserInfo = new PreferencesUserInfo(view.getContext());
        resultPref = new ResultPref(view.getContext());



        showResultList = view.findViewById(R.id.resultListLayout);
        showResultList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(view.getContext(), ResultListActivity.class));
                replaceFragment(new ResultListFragment());
            }
        });



//        userId = preferencesUserInfo.getString("key_phone");
//        Log.d("fkdjhkds",userId);
//        if (!userId.isEmpty()){
//            userName = preferencesUserInfo.getString("name");
//            userIdTv.setText("ID: "+userId);
//            userNameTextView.setText(userName);
//
//        }
//        String totalQuestions = String.valueOf(resultPref.getString("totalQuestions1"));


//            String totalQuestions2 = preferencesUserInfo.getString("totalQuestions");
//            String wrongAnswer = preferencesUserInfo.getString("wrongAnswer");
//            String correctAnswer = preferencesUserInfo.getString("correctAnswer").trim();
//            String notAnswered = preferencesUserInfo.getString("notAnswred");
//            String totalExam = preferencesUserInfo.getString("totalExam");



            String totalExam = String.valueOf(resultPref.getInt("totalExam"));
            String totalQuestions2 = String.valueOf(resultPref.getInt("totalQuestions1"));
            String correctAnswer = String.valueOf(resultPref.getInt("overAllCorrectAnswer"));
            String wrongAnswer = String.valueOf(resultPref.getInt("overAllWrongAnswer"));
            String notAnswered = String.valueOf(resultPref.getInt("overAllNotAnswered"));


            Log.d("klgkyh", totalQuestions2);



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
            String totalPercentageCorrect2 = convertToBengaliString(String.valueOf(String.format("%.2f",totalPercentageCorrect)));

            float totalPercentageWrong = ((float) wrongAnswer1 / totalQuestions1) * 100;
            String totalPercentageWrong2 = convertToBengaliString(String.valueOf(String.format("%.2f",totalPercentageWrong)));

            float totalPercentageNotAnswered = ((float) notAnswered1 / totalQuestions1) * 100;
            String totalPercentageNotAnswered2 = convertToBengaliString(String.valueOf(String.format("%.2f",totalPercentageNotAnswered)));

            totalQuestionTextView.setText(convertToBengaliString(totalQuestions2));
            correctAnswerTextView.setText(String.format("%s (%s%%)", convertToBengaliString(correctAnswer), totalPercentageCorrect2));
            wrongAnswerTextView.setText(String.format("%s (%s%%)", convertToBengaliString(wrongAnswer), totalPercentageWrong2));
            notAnswredTextView.setText(String.format("%s (%s%%)", convertToBengaliString(notAnswered), totalPercentageNotAnswered2));

            totalExamTextView.setText(convertToBengaliString(totalExam));

            progressBarCorrect.setProgress(Math.round(totalPercentageCorrect));
            progressBarWrong.setProgress(Math.round(totalPercentageWrong));
            progressBarNotAnswered.setProgress(Math.round(totalPercentageNotAnswered));



        /*

        fullProfileLayout = view.findViewById(R.id.fullProfileLayout);

        base64LocalImage = preferencesUserInfo.getString("userImage");
        if (!base64LocalImage.isEmpty()){
            Bitmap bitmap = convertBase64ToBitmap(base64LocalImage);
            profileImage.setImageBitmap(bitmap);
            profileImageUpdate.setImageBitmap(bitmap);
        }else {
            profileImage.setImageResource(R.drawable.test_profile_image);
        }

         */
        /*
        fullProfileLayout.setOnClickListener(view1 -> {

            openUpdateProfileDialog();
        } );

         */


        return view;

    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment); // Replace the current fragment with FragmentTwo
        transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
        transaction.commit();
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            profileImageUpdate.setImageURI(selectedImage);
            base64Image = convertImageToBase64(selectedImage);
        }
    }

     */
    /*
    private void openUpdateProfileDialog() {
        // Create a dialog
        Dialog dialog = new Dialog(getContext());

        // Set the layout for the dialog
        dialog.setContentView(R.layout.update_profile_layout);

        // Find views in the dialog layout
        profileImageUpdate = dialog.findViewById(R.id.profileImageID);
        closeButton = dialog.findViewById(R.id.closeUpdateLayout);
        TextInputEditText textInputEditTextName = dialog.findViewById(R.id.fullName);
        TextInputEditText textInputEditTextUserId = dialog.findViewById(R.id.userId);
        progressBar = dialog.findViewById(R.id.progressBar4);
        updateButton = dialog.findViewById(R.id.btnUpdate);

        // Set dialog window attributes for full-screen
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Set initial values for views
        if (!base64Image.isEmpty()) {
            Bitmap bitmap = convertBase64ToBitmap(base64Image);
            profileImageUpdate.setImageBitmap(bitmap);
            } else if (!base64LocalImage.isEmpty()) {
                Bitmap bitmap = convertBase64ToBitmap(base64LocalImage);
                 profileImageUpdate.setImageBitmap(bitmap);
                 } else {
                    profileImageUpdate.setImageResource(R.drawable.test_profile_image);
        }

        textInputEditTextName.setEnabled(false);
        textInputEditTextName.setText(userName);
        textInputEditTextUserId.setText(userId);
        textInputEditTextUserId.setEnabled(false);

        View.OnClickListener buttonClickListener = v -> {

            switch (v.getId()){
                case R.id.profileImageID:
                    // select profile image
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE);
                    break;

                    case R.id.btnUpdate:
                    // for update button
                        progressBar.setVisibility(View.VISIBLE);
                        profileImageUpdate.setEnabled(false);
                        updateButton.setEnabled(false);
                        closeButton.setEnabled(false);
                        if (!base64Image.isEmpty()) {
                            // Save the base64 profile image
                            saveBase64ProfileImage(userId, base64Image, new SaveImageCallback() {
                                @Override
                                public void onImageSaved(int layoutState) {
                                    int DAILOG_LAYOUT_STATE = layoutState;
                                    if (DAILOG_LAYOUT_STATE == 200) {
                                        preferencesUserInfo.saveString("profileImage", base64Image);
                                        progressBar.setVisibility(View.GONE);
                                        profileImageUpdate.setEnabled(true);
                                        closeButton.setEnabled(true);
                                        updateButton.setEnabled(true);
                                        dialog.dismiss();
                                    }
                                }
                            });
                        } else {
                            closeButton.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            profileImageUpdate.setEnabled(true);
                            updateButton.setEnabled(true);
                            Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();
                        }
                        break;

                        case R.id.closeUpdateLayout:
                            dialog.dismiss();
                            break;

            }

        } ;
        profileImageUpdate.setOnClickListener(buttonClickListener);
        updateButton.setOnClickListener(buttonClickListener);
        closeButton.setOnClickListener(buttonClickListener);

        dialog.show();
    }


     */
    /*

    private void saveBase64ProfileImage(String userId, String base64Image,SaveImageCallback callback) {

         String UPLOAD_UR2L = "https://emon.searchwizy.com/api/getData.php?apiKey=abc123&apiNum=9&action=1";
        StringRequest request = new StringRequest(Request.Method.POST, UPLOAD_UR2L, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Bitmap bitmap = convertBase64ToBitmap(base64Image);
//                profileImage.setImageBitmap(bitmap);
                profileImageUpdate.setImageBitmap(bitmap);
                int DAILOG_LAYOUT_STATE = 200;
                callback.onImageSaved(DAILOG_LAYOUT_STATE);
                preferencesUserInfo.saveString("profileImage",base64Image);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                closeButton.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                profileImageUpdate.setEnabled(true);
                updateButton.setEnabled(true);
                Toast.makeText(getContext(),"Please check your internet connection and try again",Toast.LENGTH_LONG).show();

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


     */
    /*
    private void getUserProfileImage(String userId ) {
        String url = "https://emon.searchwizy.com/api/getData.php?apiKey=abc123&apiNum=9&action="+"3"+"&userId="+userId;

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

                                preferencesUserInfo.saveString("profileImage",base64LocalImage);
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



 */
    /*
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


     */
    /*
    public Bitmap convertBase64ToBitmap(String base64Image) {
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

 */
/*
    public interface SaveImageCallback {
        void onImageSaved(int layoutState);
    }


 */

}