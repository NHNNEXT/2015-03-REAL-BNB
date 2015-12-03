package net.balbum.baby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by hyes on 2015. 12. 3..
 */
public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fisrt);

        findViewById(R.id.start_btn).setOnClickListener(this);
        findViewById(R.id.already_signed_tv).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id =  v.getId();

        switch(id){
            case R.id.start_btn :
                Intent intentSign = new Intent(StartActivity.this, SignActivity.class);
                startActivity(intentSign);
                break;
            case R.id.already_signed_tv :
                Intent intentLogin = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intentLogin);
        }

    }
}
