package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.ProfilePictureView;

import static net.balbum.baby.Util.ActivityUtil.goToActivity;

/**
 * Created by hyes on 2015. 12. 7..
 */
public class SignFacebookActivity extends AppCompatActivity{

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_sign_facebook);

        Intent intent = getIntent();
        String profileId = intent.getStringExtra("profileId");
        String profileEmail = intent.getStringExtra("profileEmail");
        String profileName = intent.getStringExtra("profileName");
        Log.d("test", "id : " + profileId + " profileEmail: " + profileEmail + " profileName: "+profileName);

        ProfilePictureView  profilePictureView = (ProfilePictureView) findViewById(R.id.image);
        profilePictureView.setProfileId(profileId);

        TextView signName = (TextView)findViewById(R.id.sign_email_tv);
        signName.setText(profileName);

        TextView signEmail = (TextView)findViewById(R.id.sign_name_tv);
        signEmail.setText(profileEmail);

        final EditText signRole =(EditText)findViewById(R.id.sign_role);

        Button sign_btn = (Button)findViewById(R.id.sign);
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String role = signRole.getText().toString();
                goToActivity(context, InitialSettingActivity.class);
            }
        });

    }
}