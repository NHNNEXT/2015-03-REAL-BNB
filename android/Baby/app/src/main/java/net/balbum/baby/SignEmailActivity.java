package net.balbum.baby;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import net.balbum.baby.Util.EmailUtil;
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
    static final int PICTURE_EDIT_COMPLETE = 2;
    Context context;

    EditText email;
    EditText password;
    EditText role;
    ImageView profile_image;
    LoginVo loginVo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_email_fragment);

        context = this;
        findViewById(R.id.sign).setOnClickListener(this);

        profile_image = (ImageView)findViewById(R.id.sign_email_profile_photo);
        email = (EditText)findViewById(R.id.sign_email);
        password = (EditText)findViewById(R.id.sign_password);
        role = (EditText)findViewById(R.id.sign_role);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CardImageEditActivity.class);
                startActivityForResult(intent, PICTURE_EDIT_COMPLETE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == PICTURE_EDIT_COMPLETE) {
            this.setSelectedImage(CardImageEditActivity.croppedBitmap);
        }
    }

    public void setSelectedImage(Bitmap bitmap){
        profile_image.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {


        TaskService taskService = ServiceGenerator.createService(TaskService.class);

        if(getInfo()) {
            taskService.createLogin(loginVo, new Callback<AuthVo>() {
                @Override
                public void success(AuthVo authVo, Response response) {

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tokenB", authVo.token);
                    editor.putString("profileName", email.getText().toString());
                   // editor.putString("profileImage", profileImage);
                    editor.putString("profileRole", password.getText().toString());
                    editor.commit();
                    editor.commit();

                    goToActivity(context, InitialSettingActivity.class);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }

    }

    private boolean getInfo() {

        if (email.getText().toString().matches("")) {
            ToastUtil.show(context, "메일을 입력하세요");
        } else if (password.getText().toString().matches("")) {
            ToastUtil.show(context, "비밀번호를 입력하세요");
        } else if(role.getText().toString().matches("")){
            ToastUtil.show(context, "역할을 입력하세요");
        } else {
            if (!EmailUtil.isValidEmail((CharSequence) email.getText().toString())) {
                ToastUtil.show(context, "메일형식을 확인하세요");
                return false;
            }
            loginVo = new LoginVo(email.getText().toString(), password.getText().toString());
            return true;
        }
        return false;
    }


}
