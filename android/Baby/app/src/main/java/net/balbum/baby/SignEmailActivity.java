package net.balbum.baby;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import net.balbum.baby.Util.ToastUtil;
import net.balbum.baby.VO.AuthVo;
import net.balbum.baby.VO.LoginVo;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static net.balbum.baby.Util.ActivityUtil.goToActivity;


/**
 * Created by hyes on 2015. 12. 3..
 */
public class SignEmailActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;

    EditText email;
    EditText password;
    EditText role;

    LoginVo loginVo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_email_fragment);

        context = this;
        findViewById(R.id.sign).setOnClickListener(this);

        email = (EditText)findViewById(R.id.sign_email);
        password = (EditText)findViewById(R.id.sign_password);
        role = (EditText)findViewById(R.id.sign_role);

    }

    @Override
    public void onClick(View v) {


        TaskService taskService = ServiceGenerator.createService(TaskService.class);

        if(getInfo()) {
            taskService.createLogin(loginVo, new Callback<AuthVo>() {
                @Override
                public void success(AuthVo authVo, Response response) {
                    goToActivity(context, MainActivity.class);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }

    }

    private boolean getInfo() {

        if(email.getText().toString().matches("")){
            ToastUtil.show(context, "메일을 입력하세요");
        }else if(password.getText().toString().matches("")){
            ToastUtil.show(context, "비밀번호를 입력하세요");
        }else{
             loginVo = new LoginVo(email.getText().toString(), password.getText().toString());
            return true;
        }
        return false;
    }
}
