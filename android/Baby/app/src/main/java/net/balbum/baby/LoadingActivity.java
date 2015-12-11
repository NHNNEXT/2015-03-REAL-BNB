package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.balbum.baby.VO.ResponseVo;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hyes on 2015. 11. 17..
 */
public class LoadingActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Context context;
    TaskService taskService;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        context = this;

        taskService = ServiceGenerator.createService(TaskService.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(sharedPreferences.contains("tokenB")) {
            String t = sharedPreferences.getString("tokenB", "");
            Log.d("test", "token~~" + t);

            //서버한테 토큰 보내서 유효성 확인인 후 success면 이거 인텐
            taskService.tokenCheck(t, new Callback<ResponseVo>() {
                @Override
                public void success(ResponseVo responseVo, Response response) {
                    Log.d("test", "responseVo" + responseVo.state);

                    if(responseVo.state) {
                        Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        goToStartActivity();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("test", "error" + error);
                }
            });


        }else {
            goToStartActivity();
            Log.d("test", "token~ 없어~" );
        }

    }

    private void goToStartActivity() {
        Intent intent = new Intent(LoadingActivity.this, StartActivity.class);
        startActivity(intent);
    }
}
