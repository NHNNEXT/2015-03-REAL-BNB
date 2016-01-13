package net.balbum.baby;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.balbum.baby.Util.TokenUtil;
import net.balbum.baby.VO.BabyVo;
import net.balbum.baby.VO.FamilyVo;
import net.balbum.baby.VO.UserVo;
import net.balbum.baby.adapter.BabyListAdapter;
import net.balbum.baby.adapter.FamilyInfoAdapter;
import net.balbum.baby.adapter.WaitingFamilyAdapter;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static net.balbum.baby.Util.ActivityUtil.goToActivity;

/**
 * Created by hyes on 2015. 12. 23..
 */
public class FamilySettingActivity extends AppCompatActivity {

    Context context;
    List<BabyVo> babies;
    List<UserVo> familyList;
    List<UserVo> readyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_setting);
        context = this;
        init();
        initProfileInfo();
        myProfileSetting();
        babySetting();

    }

    private void myProfileSetting() {
        TextView profile_setting = (TextView)findViewById(R.id.my_profile_setting);
        profile_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(context, ProfileSettingActivity.class);
            }
        });
    }

    private void babySetting() {
        LinearLayout baby_setting = (LinearLayout)findViewById(R.id.baby_setting);
        baby_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(context, BabySettingActivity.class);
            }
        });
    }

    private void init() {

        familyList = new ArrayList<UserVo>();
        babies = new ArrayList<BabyVo>();
        readyList = new ArrayList<UserVo>();

        TokenUtil tu = new TokenUtil(context);
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.getFamilyGlobalInfo(tu.getToken(), new Callback<FamilyVo>() {
            @Override
            public void success(FamilyVo familyVo, Response response) {
                familyList = familyVo.families;
                babies = familyVo.babies;
                //Log.d("test", babies.size()+", 0 "+babies.get(2).bid+", "+babies.get(2).babyName);
                readyList = familyVo.readyList;
                initFamilyMember();
                initBabies();
                initWaitingMember();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", "family info fail!");
            }
        });
    }


    private void initProfileInfo() {

        ImageView imageView = (ImageView) findViewById(R.id.nav_profile_imageView);
        TextView nav_name = (TextView) findViewById(R.id.nav_profile_id);
        TextView nav_role = (TextView) findViewById(R.id.nav_profile_role);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (sharedPreferences.contains("profileName")) {
            String profileName = sharedPreferences.getString("profileName", "");
            nav_name.setText(profileName);
        }

        if (sharedPreferences.contains("profileImage")) {

            String profileImage = sharedPreferences.getString("profileImage", "");
            Log.d("test", "profileImage있음 "+ profileImage);
            Picasso.with(context).load(profileImage).into(imageView);
        }
        Log.d("test", "profileImage없음 ");
        if (sharedPreferences.contains("profileRole")) {
            String profileRole = sharedPreferences.getString("profileRole", "");
            nav_role.setText(profileRole);
        }
    }

    private void initFamilyMember() {
        RecyclerView familyRv = (RecyclerView) findViewById(R.id.setting_family_member);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        familyRv.setLayoutManager(sglm);
        FamilyInfoAdapter adapter = new FamilyInfoAdapter(familyList, context);
        familyRv.setAdapter(adapter);

    }

    private void initBabies() {
        RecyclerView babyRv = (RecyclerView) findViewById(R.id.setting_babies);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        babyRv.setLayoutManager(sglm);
        BabyListAdapter adapter = new BabyListAdapter(babies, context, R.layout.setting_babies_row);
        babyRv.setAdapter(adapter);
    }

    private void initWaitingMember() {
        RecyclerView waitingRv = (RecyclerView) findViewById(R.id.setting_family_waiting);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        waitingRv.setLayoutManager(llm);
        WaitingFamilyAdapter adapter = new WaitingFamilyAdapter(readyList, context);
        waitingRv.setAdapter(adapter);
    }
}
