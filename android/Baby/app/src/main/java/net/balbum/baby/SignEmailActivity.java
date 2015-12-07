package net.balbum.baby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * Created by hyes on 2015. 12. 3..
 */
public class SignEmailActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_email_fragment);

        findViewById(R.id.sign_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SignEmailActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
