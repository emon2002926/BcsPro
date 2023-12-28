package com.gdalamin.bcs_pro.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.api.ApiKeys;
import com.gdalamin.bcs_pro.api.PreferencesUserInfo;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


public class ActivityLogin extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView signInTv, signUpTv;
    LinearLayout layoutSignIn, layoutSignUp;
    View devider1, devider2;
    EditText phoneEtS, fullNameEtS, passEtS, phoneEtL, passEtL;
    TextView contunueBtnS, contunueBtnL;


    private static final String API_URL= ApiKeys.API_URL;

    PreferencesUserInfo preferencesUserInfo;


    ProgressBar progressBar;

//    private CallbackManager callbackManager;
//    private LoginManager loginManager;
//    private LoginButton mButtonFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
/*

        preferencesUserInfo = new PreferencesUserInfo(ActivityLogin.this);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(view -> signInWithGoogle());



        mButtonFacebook = findViewById(R.id.fb_login_button);
        FacebookSdk.sdkInitialize(ActivityLogin.this);
        callbackManager = CallbackManager.Factory.create();
        facebookLogin();
        mButtonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginManager.logInWithReadPermissions(
                        ActivityLogin.this,
                        Arrays.asList(
                                "email",
                                "public_profile",
                                "user_birthday"));
            }
        });

 */

    }



/*

    public void facebookLogin()
    {

        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();

        loginManager.registerCallback(
                        callbackManager,
                        new FacebookCallback<LoginResult>() {

                            @Override
                            public void onSuccess(LoginResult loginResult)
                            {

                                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                                GraphRequest request = GraphRequest.newMeRequest(
                                        accessToken,
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(
                                                    JSONObject object,
                                                    GraphResponse response) {

                                                try {
                                                    String name = object.getString("name");
                                                    String id = object.getString("id");
                                                    preferencesUserInfo.saveString("key_phone",id);
                                                    signUp(name,id,"");
                                                } catch (JSONException e) {
                                                    throw new RuntimeException(e);
                                                }
                                                // Application code
                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,name,link");
                                request.setParameters(parameters);
                                request.executeAsync();



                            }

                            @Override
                            public void onCancel()
                            {
                                Log.v("LoginScreen", "---onCancel");
                            }

                            @Override
                            public void onError(FacebookException error)
                            {
                                // here write code when get error
                                Log.v("LoginScreen", "----onError: "
                                        + error.getMessage());
                            }
                        });
    }

    @Keep void signInWithGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    @Keep    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String name = account.getDisplayName();
                String email = account.getEmail();
                preferencesUserInfo.saveString("key_phone",email);

                signUp(name, email,"");
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        callbackManager.onActivityResult(
                requestCode,
                resultCode,
                data);

        super.onActivityResult(requestCode,
                resultCode,
                data);


    }






    private void signUp(final String name, final String phone, final String password) {
        StringRequest request = new StringRequest(Request.Method.POST, API_URL + "api/singUpAndLogin/signUp.php?apiKey=ghi789",
                response ->  {
                    Log.d("loginn","sing up complite");
                    navigateToMainActivity();
                }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("phone", phone);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


    void navigateToMainActivity() {

        Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }




 */


}