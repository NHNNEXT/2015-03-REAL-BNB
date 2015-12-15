package net.balbum.baby;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import net.balbum.baby.Util.ActivityUtil;


/**
 * Created by hyes on 2015. 12. 3..
 */
public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fisrt);
        context = this;

        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.already_signed).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id =  v.getId();

        switch(id){
            case R.id.start:
                Log.d("test", "잉befor");
                ActivityUtil.goToActivity(context, SignActivity.class);
                Log.d("test", "잉after");
                break;
            case R.id.already_signed:
                ActivityUtil.goToActivity(context, LoginActivity.class);
                break;
        }
    }
}
