package net.balbum.baby;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.FacebookSdk;

/**
 * Created by hyes on 2015. 12. 7..
 */
public class SignFacebookActivity extends AppCompatActivity{

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_facebook);
        Toast.makeText(context, "tet", Toast.LENGTH_SHORT).show();

    }
}