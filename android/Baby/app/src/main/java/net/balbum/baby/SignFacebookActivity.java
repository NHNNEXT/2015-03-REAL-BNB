package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.ProfilePictureView;

import net.balbum.baby.Util.ImageUtil;
import net.balbum.baby.Util.ToastUtil;
import net.balbum.baby.Util.TokenUtil;
import net.balbum.baby.VO.AuthVo;
import net.balbum.baby.VO.LoginVo;
import net.balbum.baby.VO.ResponseVo;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import java.io.File;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import static net.balbum.baby.Util.ActivityUtil.goToActivity;

/**
 * Created by hyes on 2015. 12. 7..
 */
public class SignFacebookActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_sign_facebook);

        Intent intent = getIntent();
        String profileId = intent.getStringExtra("profileId");
        final String profileEmail = intent.getStringExtra("profileEmail");
        final String profileName = intent.getStringExtra("profileName");
        final String profileImage = intent.getStringExtra("profileImage");
        final String token = intent.getStringExtra("token");
        Log.d("test", "token: " + token);

        ProfilePictureView profilePictureView = (ProfilePictureView) findViewById(R.id.image);
        profilePictureView.setProfileId(profileId);

        TextView signName = (TextView) findViewById(R.id.sign_email_tv);
        signName.setText(profileName);

        final TextView signEmail = (TextView) findViewById(R.id.sign_name_tv);
        signEmail.setText(profileEmail);

        final EditText signRole = (EditText) findViewById(R.id.sign_role);

        Button sign_btn = (Button) findViewById(R.id.sign);
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String profileRole = signRole.getText().toString();

                if (profileRole.matches("")) {
                    ToastUtil.show(context, "역할을 입력해주세요.");
                } else {
                    TaskService taskService = ServiceGenerator.createService(TaskService.class);
                    taskService.createSign(new LoginVo(profileEmail, profileRole, token), new Callback<AuthVo>() {
                        @Override
                        public void success(AuthVo authVo, Response response) {

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = sharedPreferences.edit();


                            if (authVo.message.equals("이미 가입 되었습니다.")) {
                                ToastUtil.show(context, "이미 가입 되었습니다");
                                editor.putString("tokenB", authVo.token);
                                editor.commit();
                                goToActivity(context, MainActivity.class);
                            } else {
                                editor.putString("tokenB", authVo.token);
                                editor.putString("profileName", profileName);
                                editor.putString("profileImage", profileImage);
                                editor.putString("profileRole", profileRole);
                                editor.commit();
                                sendProfileImage(profileImage);
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.d("test", "error+ " + error.toString());

                        }
                    });
                }
            }

        });
    }
    private void sendProfileImage(final String profileImage) {
        
        final File[] file = {null};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bit = ImageUtil.getBitmapFromURL(profileImage);
                file[0] = ImageUtil.ConvertBitmapToFile(bit);
                Message message = new Message();
                message.obj = file[0];
                handler.sendMessage(message);
            }
        });

        thread.start();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            sendProfile(msg.obj);

            return false;
        }
    });

    private void sendProfile(Object obj) {

        TypedFile typedFile = new TypedFile("multipart/form-data", (File)obj);
        TokenUtil tu = new TokenUtil(context);
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.sendProfileImage(typedFile, tu.getToken(), new Callback<ResponseVo>() {
            @Override
            public void success(ResponseVo responseVo, Response response) {
                Log.d("test", "sendProifile 성공");
                goToActivity(context, InitialSettingActivity.class);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", "sendProifile 실패 ");
            }
        });
    }


}
