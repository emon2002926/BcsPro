package com.gdalamin.bcs_pro.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.gdalamin.bcs_pro.Activity.ActivityLogin;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.SharedPreferencesManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout messengerChatBtn,facebookGroup,logOutButton;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);



        facebookGroup = view.findViewById(R.id.facebookGroup);
        facebookGroup.setOnClickListener(view1 -> {
            String groupId = "887679785774255"; //   Messenger group ID
            String groupName = "BCS Pro"; //  name of the Messenger group
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://group/" + groupId));
            intent.putExtra("title", groupName);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(view1.getContext(), "Please install Facebook Messenger", Toast.LENGTH_SHORT).show();
            }
        });

        logOutButton = view.findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(view1 -> {

            singOutOption();

        }
        );
        messengerChatBtn = view.findViewById(R.id.messengerChat);

        messengerChatBtn.setOnClickListener(view1 -> {
            openMessenger();

        });





        return view;
    }


    private void openMessenger(){
        String userIdOrGroupId = "100009066774848";

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
        loginPreferences   = context.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = loginPreferences.edit();

        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);

        // Build the GoogleSignInOptions for signing in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // If the user is already signed in, sign them out
        if (account != null) {
            // Create a GoogleSignInClient using the given context and GoogleSignInOptions
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

            // Call signOut() on the GoogleSignInClient to sign the user out
            mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Sign out successful
                    Log.d("SignOut", "Google sign out successful");

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

}