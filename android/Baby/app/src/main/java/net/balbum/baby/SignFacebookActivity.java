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

/**
 * Created by hyes on 2015. 12. 7..
 */
public class SignFacebookActivity extends AppCompatActivity{

    Context context;
    ProfilePictureView profilePictureView;
//    EditText signEmail;
//    EditText signName;
    Button sign_btn;
    TextView signEmail;
    TextView signName;
    EditText signRole;
    String role;

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

        profilePictureView = (ProfilePictureView) findViewById(R.id.image);
        profilePictureView.setProfileId(profileId);

//        signEmail = (EditText)findViewById(R.id.sign_email);
//        signEmail.setText(profileEmail);
//
//        signName = (EditText)findViewById(R.id.sign_name);
//        signName.setText(profileName);

        signName = (TextView)findViewById(R.id.sign_email_tv);
        signName.setText(profileName);
        signEmail = (TextView)findViewById(R.id.sign_name_tv);
        signEmail.setText(profileEmail);
        signRole =(EditText)findViewById(R.id.sign_role);

        sign_btn = (Button)findViewById(R.id.sign_btn);
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = signRole.getText().toString();
                Log.d("test", "role" + role);
                Intent intent = new Intent(SignFacebookActivity.this, InitialSettingActivity.class);
                startActivity(intent);
            }
        });

    }
}