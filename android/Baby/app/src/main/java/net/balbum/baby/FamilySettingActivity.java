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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.balbum.baby.Util.Define;
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

/**
 * Created by hyes on 2015. 12. 23..
 */
public class FamilySettingActivity extends AppCompatActivity {

    Context context;
    List<BabyVo> babies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_setting);
        context = this;
        init();
        initProfileInfo();
        initFamilyMember();
        initBabies();
        initWaitingMember();

    }

    private void init() {
        Log.d("test", "family init()");
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.getFamilyGlobalInfo("token", new Callback<FamilyVo>() {
            @Override
            public void success(FamilyVo familyVo, Response response) {
                Log.d("test", "family size: " + familyVo.families.size());
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
            Picasso.with(context).load(profileImage).into(imageView);
        }
        if (sharedPreferences.contains("profileRole")) {
            String profileRole = sharedPreferences.getString("profileRole", "");
            nav_role.setText(profileRole);
        }
    }

    private void initFamilyMember() {
        RecyclerView familyRv = (RecyclerView) findViewById(R.id.setting_family_member);

        List<UserVo> familyList = new ArrayList<UserVo>();
        familyList.add(new UserVo(Define.URL + "/img/imgs/baby/1450845479648219d744ae7db4fe88da0a7c55431e764.png", "삼촌", false));

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        familyRv.setLayoutManager(sglm);
        FamilyInfoAdapter adapter = new FamilyInfoAdapter(familyList, context);
        familyRv.setAdapter(adapter);

    }

    private void initBabies() {
        babies = new ArrayList<BabyVo>();
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.getBabies("token", new Callback<ArrayList<BabyVo>>() {
            @Override
            public void success(ArrayList<BabyVo> babyVos, Response response) {
                babies = babyVos;
                initBabyRecyclerView();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void initBabyRecyclerView() {
        RecyclerView babyRv = (RecyclerView) findViewById(R.id.setting_babies);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        babyRv.setLayoutManager(sglm);
        BabyListAdapter adapter = new BabyListAdapter(babies, context, R.layout.setting_babies_row);
        babyRv.setAdapter(adapter);
    }

    private void initWaitingMember() {
        RecyclerView waitingRv = (RecyclerView) findViewById(R.id.setting_family_waiting);

        List<UserVo> familyList = new ArrayList<UserVo>();
        familyList.add(new UserVo(Define.URL + "/img/imgs/baby/1450845479648219d744ae7db4fe88da0a7c55431e764.png", "수퍼히어로아빠", false));
        familyList.add(new UserVo(Define.URL + "/img/imgs/baby/1450843303888635a1c73b662482ca42e0b956b981d3b.png", "이모", false));

        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        waitingRv.setLayoutManager(llm);

        WaitingFamilyAdapter adapter = new WaitingFamilyAdapter(familyList, context);
        waitingRv.setAdapter(adapter);
    }


}