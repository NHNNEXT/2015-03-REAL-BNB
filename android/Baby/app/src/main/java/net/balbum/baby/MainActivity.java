package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.balbum.baby.Util.ActivityUtil;
import net.balbum.baby.Util.Define;
import net.balbum.baby.Util.RoundedTransformation;
import net.balbum.baby.Util.TokenUtil;
import net.balbum.baby.VO.BabyTagVo;
import net.balbum.baby.VO.BabyVo;
import net.balbum.baby.VO.CardListVo;
import net.balbum.baby.VO.GeneralCardVo;
import net.balbum.baby.adapter.BabyTagAdapter;
import net.balbum.baby.adapter.CardViewAdapter;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    RecyclerView recyclerView;
    List<GeneralCardVo> cardGeneralModelList;
    FloatingActionButton fab;
    NavigationView navigationView;
    Toolbar toolbar;
    LinearLayout drawerLayout;
    SharedPreferences sharedPreferences;
    CardViewAdapter adapter;
    List<BabyTagVo> babyList;
    ImageView imageView;
    DrawerLayout drawer;
    BabyTagAdapter babyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        initToolbar();
        initNavigationView();
        initFab();
        getData();
        getBabyInfo();
        initNavProfile();

    }

    private void initNavProfile() {

        imageView = (ImageView) findViewById(R.id.nav_profile_imageView);
        TextView nav_name = (TextView)findViewById(R.id.nav_profile_id);
        TextView nav_role = (TextView)findViewById(R.id.nav_profile_role);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        if(sharedPreferences.contains("profileName")) {
            String profileName = sharedPreferences.getString("profileName", "");
            nav_name.setText(profileName);
        }
        if(sharedPreferences.contains("profileImage")) {

            String profileImage = sharedPreferences.getString("profileImage", "");
            Log.d("test", "initImage: " + profileImage);
            Picasso.with(context)
                    .load(profileImage)
                    .transform(new RoundedTransformation())
                    .into(imageView);

        }
        if(sharedPreferences.contains("profileRole")) {
            String profileRole = sharedPreferences.getString("profileRole", "");
            Log.d("test", "initNavProifile: " + profileRole);
            nav_role.setText(profileRole);
        }
    }

    private void getBabyInfo() {
        babyList = new ArrayList<BabyTagVo>();

        TokenUtil tu = new TokenUtil(context);
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.getBabies(tu.getToken(), new Callback<ArrayList<BabyVo>>() {
            @Override
            public void success(ArrayList<BabyVo> babyVos, Response response) {
                for (BabyVo baby : babyVos) {
                    BabyTagVo babyTag = new BabyTagVo(baby.babyImg, baby.bId, false, baby.babyName);
                    babyList.add(babyTag);
                }
                initNavRecyclerView();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void initNavRecyclerView() {
        RecyclerView navRecyclerView = (RecyclerView)findViewById(R.id.nav_recycler_view);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        navRecyclerView.setLayoutManager(sglm);
        babyAdapter = new BabyTagAdapter(babyList, context);
        navRecyclerView.setAdapter(babyAdapter);
    }

    private void getData() {
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        Log.d("test", "url 정보: " + taskService.toString() + "URL" + Define.URL);
        TokenUtil tu = new TokenUtil(context);
        Log.d("test", "token: " + tu.getToken());

        taskService.getCard(tu.getToken(), new Callback<CardListVo>() {
            @Override
            public void success(CardListVo cardListVo, Response response) {
                CardListVo cd = cardListVo;
                cardGeneralModelList = cd.cardList;
                initView(cardGeneralModelList);
                Log.d("test", "error: " + cardListVo.error);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", " taskService failure");
            }
        });
    }

    private void initView(List<GeneralCardVo> cardGeneralModelList) {

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
//        Log.d("test", "initview name" + cardGeneralModelList.get(0).babies.get(0).babyName);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new CardViewAdapter(cardGeneralModelList, context, R.layout.card_general_row_portrait);

        }else {
            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(sglm);
            adapter = new CardViewAdapter(cardGeneralModelList, context, R.layout.card_general_row_landscape);
        }

        recyclerView.setAdapter(adapter);
    }

    private void initFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CardWritingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d("test", "navBaby" + babyAdapter.getSelectedNames().size());
                babyFilter(babyAdapter.getSelectedNames());
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    private void babyFilter(List<BabyTagVo> selectedNames) {
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        //선택된 애기 리스트 서버 전달하고 그 아이들이 태깅된 카드 리스트 받아와서 initview 시킬 것!!
        
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camara) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {

        if (id == R.id.nav_setting) {
            ActivityUtil.goToActivity(context, FamilySettingActivity.class);

        } else if (id == R.id.nav_poster) {
            ActivityUtil.goToActivity(context, PosterCardSelectingActivity.class);

        } else if (id == R.id.nav_poster2) {
            ActivityUtil.goToActivity(context, PosterCardSelectingActivity2.class);

        }  else if (id == R.id.nav_poster3) {
            ActivityUtil.goToActivity(context, PosterList.class);

        } else if ( id == R.id.logout){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            ActivityUtil.goToActivity(context, StartActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

