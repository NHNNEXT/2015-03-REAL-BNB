package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.balbum.baby.Util.ConvertBitmapToFileUtil;
import net.balbum.baby.Util.TimeUtil;
import net.balbum.baby.VO.CardListVo;
import net.balbum.baby.VO.GeneralCardVo;
import net.balbum.baby.adapter.RVAdapter;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private List<GeneralCardVo> cardGeneralModelList;
    private FloatingActionButton fab;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private LinearLayout drawerLayout;
    private SharedPreferences sharedPreferences;
    RVAdapter adapter;
    TaskService taskService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        initToolbar();
        initNavigationView();
        initFab();
        initData();
        initView(cardGeneralModelList);
//
    }

    private void initView(List<GeneralCardVo> cardGeneralModelList) {

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);

        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore(int current_page) {

            }
        });

        adapter = new RVAdapter(cardGeneralModelList, context);
        Log.d("test", cardGeneralModelList.size()+"!!!");
        rv.setAdapter(adapter);
    }

    private void initFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CardWritingActivity.class);
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawerLayout = (LinearLayout)findViewById(R.id.drawer_calendar);
        drawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "선택된 날짜로 타임라인 이동~~", Toast.LENGTH_SHORT).show();
            }
        });

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
            super.onBackPressed();
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

        if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if ( id == R.id.logout){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Context initData(){
        ArrayList<String> names = new ArrayList<String>();
        names.add("산체");
        names.add("벌이");

        cardGeneralModelList = new ArrayList<>();
//        cardGeneralModelList.add("String1");
//        cardGeneralModelList.add("String2");
//        cardGeneralModelList.add("String3");

        Bitmap img1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img1);
        Bitmap img2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img2);
        Bitmap img3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img3);
        Bitmap img4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img5);

        File a = ConvertBitmapToFileUtil.convertFile(img1);
        File b = ConvertBitmapToFileUtil.convertFile(img2);
        File c = ConvertBitmapToFileUtil.convertFile(img3);
        File d = ConvertBitmapToFileUtil.convertFile(img4);

        GeneralCardVo data1 = new GeneralCardVo("ririririskskdjfsldjfslkdj", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYgdGMhHf6TaMiIwvslhKy-FfL77RLopOlYEAXOhyIwtBQbyZT",TimeUtil.getRecordedMoment());
        GeneralCardVo data2 = new GeneralCardVo("ririrasdasdaddj", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYgdGMhHf6TaMiIwvslhKy-FfL77RLopOlYEAXOhyIwtBQbyZT",TimeUtil.getRecordedMoment());

//        GeneralCardVo data3 = new GeneralCardVo(new Date().toString(), TimeUtil.getRecordedMoment(), c, names, "햇살 따듯, 한가로운 오후", "엄마");
//        GeneralCardVo data4 = new GeneralCardVo(new Date().toString(), TimeUtil.getRecordedMoment(), d, names, "아무리봐도 아빠를 너무 닮은 것 같아 속상하다 크면서 바뀌겠지. 그래 그럴거야! 우리 아가는 점점 나를 닮아갈거야!!!", "엄마");
//        GeneralCardVo data5 = new GeneralCardVo(new Date().toString(), TimeUtil.getRecordedMoment(), b, names, "아가들 씐나씐나", "아빠");
//        GeneralCardVo data6 = new GeneralCardVo(new Date().toString(), TimeUtil.getRecordedMoment(), a, names, "우리아가 이쁜이 옹알옹알 잘한다", "엄마");

        cardGeneralModelList.add(data1);
        cardGeneralModelList.add(data2);
//        cardGeneralModelList.add(data3);
//        cardGeneralModelList.add(data4);
//        cardGeneralModelList.add(data5);
//        cardGeneralModelList.add(data6);

//        cardGeneralModelList.add(new CardGeneralModel("2015.10.10", R.drawable.img1, "륜이 12개월", "챙챙 12개월", "오늘은 하늘이 하늘하늘"));
//        cardGeneralModelList.add(new CardGeneralModel("2015.10.22",  R.drawable.img2, "챙챙 12개월", "유림 12개월", "꺄르르 까궁!"));
//        cardGeneralModelList.add(new CardGeneralModel("2015.10.28", R.drawable.img3, "유림 12개월", "륜이12개월", "우리아가들 잘도 잔다. 무럭무럭 건강하게만 자라다오(..아마 10년 뒤엔 공부하라고 하겠지?)"));
//        cardGeneralModelList.add(new CardGeneralModel("2015.11.03", R.drawable.img6, "유림 13개월", "륜이13개월", "오늘도 맑음"));
//        cardGeneralModelList.add(new CardGeneralModel("2015.11.04", R.drawable.img5, "유림 13개월", "오늘의 일과는 블라블라블라~~~~"));
        return null;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        taskService = ServiceGenerator.createService(TaskService.class);
//
//        taskService.getCard(new Callback<CardListVo>() {
//            @Override
//            public void success(CardListVo cardListVo, Response response) {
//                Toast.makeText(context, "Toast.make", Toast.LENGTH_LONG).show();
//                cardGeneralModelList = new ArrayList<>();
//                if(cardListVo == null || cardListVo.cardList.size() == 0){
//                    Toast.makeText(context, "card empty?null?", Toast.LENGTH_SHORT).show();
//                }else {
//                    cardGeneralModelList = cardListVo.cardList;
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Toast.makeText(context, "ERORRRRRRRR", Toast.LENGTH_LONG).show();
//            }
//        });
//    }


    @Override
    protected void onResume() {
        super.onResume();

        taskService = ServiceGenerator.createService(TaskService.class);
        Log.d("test", " getCard시작?~");
        taskService.getCard("token", new Callback<CardListVo>() {
            @Override
            public void success(CardListVo cardListVo, Response response) {
                CardListVo cd =  cardListVo;
                Log.d("test", " getCard test success~");
                cardGeneralModelList = cd.cardList;
                Log.d("test", "size~: " + cardGeneralModelList.size());
                getCardsFromServer(cardGeneralModelList);
                //되는지 확인할 것
                adapter.notifyDataSetChanged();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", " taskService failure");
          }
        });
    }

    private void getCardsFromServer(List<GeneralCardVo> cardGeneralModelList) {
            initView(cardGeneralModelList);
    }
}

