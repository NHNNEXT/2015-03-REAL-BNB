package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by hyes on 2015. 11. 17..
 */
public class LoadingActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Context context;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        context = this;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(sharedPreferences.contains("tokenB")) {
            String t = sharedPreferences.getString("tokenB", "");
            Log.d("test", "token~~" + t);
            Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
            startActivity(intent);

        }else {
            Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }
}
