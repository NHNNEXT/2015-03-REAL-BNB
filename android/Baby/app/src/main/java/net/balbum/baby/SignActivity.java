package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static net.balbum.baby.Util.ActivityUtil.goToActivity;

/**
 * Created by hyes on 2015. 12. 3..
 */
public class SignActivity extends AppCompatActivity implements View.OnClickListener {
    CallbackManager callbackManagerSign;
    Context context;
    String profileId;
    String profileEmail;
    String profileName;
    String token;

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
        switch (id) {
            case R.id.sign_facebook:
                signFacebook();
                break;
            case R.id.sign_email:
                goToActivity(SignActivity.this, SignEmailActivity.class);
                break;
        }
    }

    private void signFacebook() {
        callbackManagerSign = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
        LoginManager.getInstance().registerCallback(callbackManagerSign, new FacebookCallback<LoginResult>() {
             @Override
             public void onSuccess(LoginResult loginResult) {

                 token = loginResult.getAccessToken().getToken();
                 profileId = loginResult.getAccessToken().getUserId();


                 GraphRequest request = GraphRequest.newMeRequest(
                         loginResult.getAccessToken(),
                         new GraphRequest.GraphJSONObjectCallback() {
                             @Override
                             public void onCompleted(JSONObject json, GraphResponse response) {

                                 try {
                                     profileName = json.getString("name");
                                     profileEmail = json.getString("email");


                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }
                         });

                 Bundle parameters = new Bundle();
                 parameters.putString("fields", "id,name,email");
                 request.setParameters(parameters);
                 request.executeAsync();

                 Handler handler = new Handler();
                 handler.postDelayed(runnable, 1000);
             }

             Runnable runnable = new Runnable() {
                 @Override
                 public void run() {
                     Intent intent = new Intent(SignActivity.this, SignFacebookActivity.class);
                     intent.putExtra("profileId", profileId);
                     intent.putExtra("profileEmail", profileEmail);
                     intent.putExtra("profileName", profileName);
                     intent.putExtra("token", token);
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
                 Log.d("test", error.toString());
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

    
