package com.gdalamin.bcs_pro.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.gdalamin.bcs_pro.R;

public class SettingFragment extends Fragment {
    LinearLayout messengerChatBtn,facebookGroup,logOutButton,shareButton,privacyPolicyBtn,termsConditionsBtn,aboutUsBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);

        facebookGroup = view.findViewById(R.id.facebookGroup);
        messengerChatBtn = view.findViewById(R.id.messengerChat);
        shareButton = view.findViewById(R.id.shareBtn);
        privacyPolicyBtn = view.findViewById(R.id.privacy);
        termsConditionsBtn = view.findViewById(R.id.termsOfUse);
        aboutUsBtn = view.findViewById(R.id.about_us);

//        logOutButton = view.findViewById(R.id.logOutButton);




        // Create a Locale for Bengali (Bangladesh)


        // Create a NumberFormat instance with the Bengali locale


        View.OnClickListener clickListener = v -> {
            Intent intent;
            switch (v.getId()){
                case R.id.messengerChat:
                    openMessenger();
                    break;
                case R.id.facebookGroup:
                    String pageId = "100094890072982"; //   Facebook page ID
                    String pageName = "BCS PRO"; //  name of the Messenger group
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + pageId));
                    intent.putExtra("title", pageName);
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        Toast.makeText(v.getContext(), "Please install Facebook Messenger", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.shareBtn:
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String playStoreLink = "https://play.google.com/store/apps/details?id=com.gdalamin.bcs_pro";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, playStoreLink);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                    break;
                case R.id.about_us:
                    String pixatoneUrl = "http://pixatone.com/";
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(pixatoneUrl));
                    startActivity(intent);
                    break;

                case R.id.privacy:
                    String privacyPolicyUrl = "https://doc-hosting.flycricket.io/bcs-pro-privacy-policy/5c7da9fa-9a36-49af-915c-27bb7a5483df/privacy";
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(privacyPolicyUrl));
                    startActivity(intent);
                    break;
                case R.id.termsOfUse:
                    String  termsConditionUrl = "https://doc-hosting.flycricket.io/bcs-pro-terms-of-use/4267f876-2eee-43ce-8417-0fd41c538b03/terms";
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(termsConditionUrl));
                    startActivity(intent);
                    break;
//                case R.id.logOutButton:
////                    singOutOption();
//                    break;

            }
        };
        facebookGroup.setOnClickListener(clickListener);
        messengerChatBtn.setOnClickListener(clickListener);
        shareButton.setOnClickListener(clickListener);
        privacyPolicyBtn.setOnClickListener(clickListener);
        termsConditionsBtn.setOnClickListener(clickListener);
//        logOutButton.setOnClickListener(clickListener);
        aboutUsBtn.setOnClickListener(clickListener);


        return view;
    }

    private void openMessenger(){
        String userIdOrGroupId = "100094890072982";
        // Launch Messenger chat with the specified user or group ID
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb-messenger://user/" + userIdOrGroupId));
        intent.putExtra(Intent.EXTRA_TEXT, "Hello, let's chat!"); // Optional: add a message
        intent.setPackage("com.facebook.orca"); // Set the package name of Messenger app
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Messenger is not installed, handle the error here
        }
    }

    /*

    private void  singOutOption(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Alert");
        builder.setMessage("Are you sure to sing out");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Action to be taken when OK button is clicked
                signOut(getContext());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Action to be taken when Cancel button is clicked
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void signOut(Context context) {
        // Get the last signed-in Google account from the given context
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);

        // Get the SharedPreferences instance for storing login information
        SharedPreferences loginPreferences;
        loginPreferences   = context.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = loginPreferences.edit();

        SharedPreferencesManagerAppLogic sharedPreferencesManager = new SharedPreferencesManagerAppLogic(context);


        if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired()) {
            //User is logged in with Facebook
            sharedPreferencesManager.clear();
            editor.clear();
            editor.apply();
            signOutFromFacebook();

        } else {
            // User is not logged in with Facebook
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            if (account != null) {
                // Create a GoogleSignInClient using the given context and GoogleSignInOptions
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

                // Call signOut() on the GoogleSignInClient to sign the user out
                mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign out successful
                        // Clear the login information from SharedPreferences
                        sharedPreferencesManager.clear();
                        editor.clear();
                        editor.apply();


                        // Start the LoginActivity and finish the current Activity
                        Intent intent = new Intent(context, ActivityLogin.class);
                        context.startActivity(intent);
                        if (context instanceof Activity) {
                            ((Activity) context).finish();
                        }
                    } else {
                        // Sign out failed
                        Log.w("SignOut", "Google sign out failed", task.getException());
                    }
                });

            } else {
                // User is not signed in
                // Clear the login information from SharedPreferences
                Intent intent = new Intent(context, ActivityLogin.class);
                context.startActivity(intent);
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
                sharedPreferencesManager.clear();
                editor.clear();
                editor.apply();
            }

        }
        // Build the GoogleSignInOptions for signing in


        // If the user is already signed in, sign them out
    }
    private void signOutFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            // User is already logged out
            return;
        }

        GraphRequest delPermRequest = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        LoginManager.getInstance().logOut();

                        // Perform any additional operations after logout if needed
                    }
                });

        delPermRequest.executeAsync();
    }

     */

}