package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.balbum.baby.Util.ConvertBitmapToFileUtil;
import net.balbum.baby.VO.BabyTagVo;
import net.balbum.baby.VO.CardFormVo;
import net.balbum.baby.VO.ResponseVo;
import net.balbum.baby.adapter.BabyTagAdapter;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by hyes on 2015. 11. 10..
 */
public class CardWritingActivity extends AppCompatActivity {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> fragmentTitleList = new ArrayList<>();

    Context context;
    Toolbar toolbar;
    List<BabyTagVo> babyTagNamesList;
    TaskService taskService;
    ViewPager viewPager;
    BabyTagAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_writing);
        context = this;
        taskService = ServiceGenerator.createService(TaskService.class);

        initToolbar();
        initData();
        initTagBar();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        RecyclerView rv_baby = (RecyclerView)findViewById(R.id.rv_baby_list);
        StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        rv_baby.setLayoutManager(sgm);

        Log.i("test", "1: " + babyTagNamesList.size());

        adapter = new BabyTagAdapter(babyTagNamesList, context);
        rv_baby.setAdapter(adapter);

    }

    private Context initData(){


        babyTagNamesList = new ArrayList<>();


        Bitmap img1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.b1);
        Bitmap img2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.b2);
        Bitmap img3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.b3);

        File a = ConvertBitmapToFileUtil.convertFile(img1);
        File b = ConvertBitmapToFileUtil.convertFile(img2);
        File c = ConvertBitmapToFileUtil.convertFile(img3);

        BabyTagVo baby1 = new BabyTagVo(a, "산체");
        BabyTagVo baby2 = new BabyTagVo(b, "연두");
        BabyTagVo baby3 = new BabyTagVo(c, "벌이");

        babyTagNamesList.add(baby1);
        babyTagNamesList.add(baby2);
        babyTagNamesList.add(baby3);

        return null;

    }

    private void initTagBar() {

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new GeneralCardFragment(), "일상의 순간");
        adapter.addFrag(new EventCardFragment(), "특별한 순간");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.writing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_share) {
            return true;
        }

        if (id == R.id.action_save) {

            int currentItem = viewPager.getCurrentItem();
                Log.i("test", "current: " + currentItem);


           // GeneralCardVo vo =  ((OnGetCardListener)fragmentList.get(currentItem)).getCardInfo();
            Intent intent = new Intent(CardWritingActivity.this, MainActivity.class);

            Bitmap img1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.b1);
            File a = ConvertBitmapToFileUtil.convertFile(img1);

            TypedFile typedFile = new TypedFile("multipart/form-data", a);

            List<Long> asd = new ArrayList<Long>();
            asd.add(new Long(2));
            asd.add(new Long(3));
            asd.add(new Long(4));

            Long l = new Long(2);
            String content = "asdasd";
            CardFormVo cardFormVo = new CardFormVo(l, "token", asd, "경륜이랑 짝코딩딩딩", "1");

            taskService.createCard(typedFile, l, "token", l, "3qe", "20302030", new Callback<ResponseVo>() {
                @Override
                public void success(ResponseVo responseVo, Response response) {
                    Log.i("test", "card success" + responseVo.state + ", error: "+ responseVo.error);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i("test", "card error: " + error);
                }
            });

            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("test", "여긴 어디1");
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);

        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}


