package net.balbum.baby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by hyes on 2015. 12. 3..
 */
public class SignActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        findViewById(R.id.sign_facebook).setOnClickListener(this);
        findViewById(R.id.sign_email).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.sign_facebook:
                break;
            case R.id.sign_email:
                Intent intentEmail = new Intent(SignActivity.this, SignEmailActivity.class);
                startActivity(intentEmail);
                break;


        }
    }
}
