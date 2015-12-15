package net.balbum.baby;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static net.balbum.baby.Util.ActivityUtil.goToActivity;


/**
 * Created by hyes on 2015. 12. 3..
 */
public class SignEmailActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_email_fragment);

        findViewById(R.id.sign).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        goToActivity(SignEmailActivity.this, MainActivity.class);
    }
}
