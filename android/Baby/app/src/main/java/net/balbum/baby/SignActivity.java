package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * Created by hyes on 2015. 12. 3..
 */
public class SignActivity extends AppCompatActivity implements View.OnClickListener {
    CallbackManager callbackManagerSign;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;
    ProfileTracker profileTracker;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_signin);

        findViewById(R.id.sign_facebook).setOnClickListener(this);
        findViewById(R.id.sign_email).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.sign_facebook:
                 signFacebook();
                Log.d("test", "signFacebook()");
//                Intent intentFacebook = new Intent(SignActivity.this, SignFacebookActivity.class);
//                startActivity(intentFacebook);
                break;
            case R.id.sign_email:
                Intent intentEmail = new Intent(SignActivity.this, SignEmailActivity.class);
                startActivity(intentEmail);
                break;
        }
    }

    private void signFacebook() {
        Toast.makeText(context, "sign", Toast.LENGTH_SHORT).show();
        callbackManagerSign = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackManagerSign, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(context, "sign", Toast.LENGTH_SHORT).show();
                        Log.d("test", "onSuccess");
                        Log.d("test", "token: "+loginResult.getAccessToken().getToken());
//                        GraphRequest.newMeRequest(
//                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                                    @Override
//                                    public void onCompleted(JSONObject json, GraphResponse response) {
//                                        if (response.getError() != null) {
//                                        } else {
//
////                                                String jsonresult = String.valueOf(json);
////                                                System.out.println("JSON Result" + jsonresult);
////
////                                                String str_email = json.getString("email");
////                                                String str_id = json.getString("id");
////                                                String str_firstname = json.getString("first_name");
////                                                String str_lastname = json.getString("last_name");
//
//                                        }
//                                    }
//
//                                }).executeAsync();

                        Handler handler = new Handler();
                        handler.postDelayed(runnable, 100);

                    }

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SignActivity.this, SignFacebookActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    };


                    @Override
                    public void onCancel() {
                        Log.d("test", "On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("test",error.toString());
                    }
                });
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManagerSign.onActivityResult(requestCode, resultCode, data);
    }

}
