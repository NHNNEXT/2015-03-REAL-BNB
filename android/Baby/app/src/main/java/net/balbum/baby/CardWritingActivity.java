package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
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

import net.balbum.baby.Util.Define;
import net.balbum.baby.Util.ImageUtil;
import net.balbum.baby.Util.TokenUtil;
import net.balbum.baby.VO.BabyTagVo;
import net.balbum.baby.VO.BabyVo;
import net.balbum.baby.VO.GeneralCardVo;
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

import static net.balbum.baby.Util.ActivityUtil.goToActivity;

/**
 * Created by hyes on 2015. 11. 10..
 */
public class CardWritingActivity extends AppCompatActivity {

    Context context;
    List<Fragment> fragmentList = new ArrayList<>();
    List<BabyTagVo> babyTagNamesList;
    List<BabyTagVo> sel;
    List<BabyVo> babyVoList;
    ViewPager viewPager;
    BabyTagAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_writing);

        context = this;

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        final Long card_id = intent.getLongExtra("cId", 0);
        final String card_img = intent.getStringExtra("cImg");
        GeneralCardFragment generalCardFragment = new GeneralCardFragment();
        EventCardFragment eventCardFragment = new EventCardFragment();

        initToolbar();
        initData();
        initViewPager(generalCardFragment, eventCardFragment);
//        initBabyTag();

        if (type == Define.CARD_MODIFY) {

            //GeneralCardVo cardVo = (GeneralCardVo) intent.getParcelableExtra("generalCardVo");
            final GeneralCardVo[] cardVo = new GeneralCardVo[1];
            TaskService taskService = ServiceGenerator.createService(TaskService.class);
            taskService.getOneCard(card_id, new Callback<GeneralCardVo>() {
                @Override
                public void success(GeneralCardVo generalCardVo, Response response) {
                    Log.d("test", "card type" + generalCardVo.getType());
                    cardVo[0] = generalCardVo;

                    Bundle bundle = new Bundle();
                    bundle.putLong("cId", card_id);
                    bundle.putString("cImg", card_img);

                    if(cardVo[0].type.equals("NORMAL")) {
                        GeneralCardFragment generalCardFragment = new GeneralCardFragment();
                        generalCardFragment.setArguments(bundle);

                        Log.d("test", "general");

                    }else if(cardVo[0].type.equals("EVENT")){
                        Log.d("test", "event");
                        EventCardFragment eventCardFragment = new EventCardFragment();
                        eventCardFragment.setArguments(bundle);
                        viewPager.setCurrentItem(1);

                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("test", "fail card_id");
                }
            });

        }


    }

    private void initBabyTag() {
        RecyclerView rv_baby = (RecyclerView)findViewById(R.id.rv_baby_list);
        StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        rv_baby.setLayoutManager(sgm);
        adapter = new BabyTagAdapter(babyTagNamesList, context);
        rv_baby.setAdapter(adapter);
    }

    private void initViewPager(GeneralCardFragment generalCardFragment, EventCardFragment eventCardFragment) {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager, generalCardFragment, eventCardFragment);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private Context initData(){

        //기본 저장되어있는 아이 정보 불러와서 리스트 만들 부분
        babyTagNamesList = new ArrayList<>();
        babyVoList = new ArrayList<>();
        TokenUtil tu = new TokenUtil(context);

        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.getBabies(tu.getToken(), new Callback<ArrayList<BabyVo>>() {
              @Override
              public void success(ArrayList<BabyVo> babyVos, Response response) {
                  for(BabyVo baby : babyVos){
                      BabyTagVo babyTag = new BabyTagVo(baby.babyImg, baby.bid, false, baby.babyName);
                      Log.d("test", "bid: " + baby.bid + " name: "+ baby.babyName);
                      babyTagNamesList.add(babyTag);
                  }
                  initBabyTag();
              }

              @Override
              public void failure(RetrofitError error) {

              }
        });
        return null;
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupViewPager(ViewPager viewPager, GeneralCardFragment generalCardFragment, EventCardFragment eventCardFragment) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(generalCardFragment, "일상의 순간");
        adapter.addFrag(eventCardFragment, "특별한 순간");
        viewPager.setAdapter(adapter);
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
            GeneralCardVo vo =  ((OnGetCardListener)fragmentList.get(currentItem)).getCardInfo();
            File file = null;
            String type = null;
            if(currentItem == 0){
                type = "NORMAL";
                if(vo.cardImg != null) {
                    file = new File(vo.cardImg);
                }else{
                    file = null;
                }
            }else if(currentItem == 1){
                type = "EVENT";
                if(vo.cardImg != null) {
                    file = ImageUtil.getFilefromDrawable(context, vo.cardImg);
                }else{
                    file = null;
                }
            }
            TypedFile typedFile;
            if(vo.cardImg != null) {
                typedFile = new TypedFile("multipart/form-data", file);
            }else{
                typedFile = null;
            }
            TokenUtil tu = new TokenUtil(context);
            tu.getToken();

            //vo.names = adapter.getSelectedNames();
            sel = new ArrayList<BabyTagVo>();
            sel = adapter.getSelectedNames();


            TaskService taskService = ServiceGenerator.createService(TaskService.class);

            if(sel.size() == 1) {
                if ((Long) vo.cid == 0) {

                    taskService.createCard(typedFile, tu.getToken(), sel.get(0).bid, vo.content, vo.modifiedDate, type, new Callback<ResponseVo>() {
                        @Override
                        public void success(ResponseVo responseVo, Response response) {
                            Log.i("test", "card success" + responseVo.state + ", error: " + responseVo.error);
                            goToActivity(CardWritingActivity.this, MainActivity.class);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.i("test", "card error: " + error);
                        }
                    });
                } else {
                    taskService.updateCard(typedFile, tu.getToken(), sel.get(0).bid, vo.content, vo.modifiedDate, type, vo.cid, new Callback<ResponseVo>() {
                        @Override
                        public void success(ResponseVo responseVo, Response response) {
                            Log.i("test", "업데이트 성공!");
                            goToActivity(CardWritingActivity.this, MainActivity.class);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.i("test", "업데이트 실패!");
                        }
                    });

                }
            }else if(sel.size() == 2){
                if ((Long) vo.cid == 0) {

                    taskService.createCard(typedFile, tu.getToken(), sel.get(0).bid, sel.get(1).bid, vo.content, vo.modifiedDate, type, new Callback<ResponseVo>() {
                        @Override
                        public void success(ResponseVo responseVo, Response response) {
                            Log.i("test", "card success" + responseVo.state + ", error: " + responseVo.error);
                            goToActivity(CardWritingActivity.this, MainActivity.class);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.i("test", "card error: " + error);
                        }
                    });
                } else {
                    taskService.updateCard(typedFile, tu.getToken(), sel.get(0).bid, sel.get(1).bid, vo.content, vo.modifiedDate, type, vo.cid, new Callback<ResponseVo>() {
                        @Override
                        public void success(ResponseVo responseVo, Response response) {
                            Log.i("test", "업데이트 성공!");
                            goToActivity(CardWritingActivity.this, MainActivity.class);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.i("test", "업데이트 실패!");
                        }
                    });

                }

            }else if(sel.size() == 3){
                if ((Long) vo.cid == 0) {

                    taskService.createCard(typedFile, tu.getToken(), sel.get(0).bid, sel.get(1).bid, sel.get(2).bid, vo.content, vo.modifiedDate, type, new Callback<ResponseVo>() {
                        @Override
                        public void success(ResponseVo responseVo, Response response) {
                            Log.i("test", "card success" + responseVo.state + ", error: " + responseVo.error);
                            goToActivity(CardWritingActivity.this, MainActivity.class);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.i("test", "card error: " + error);
                        }
                    });
                } else {
                    taskService.updateCard(typedFile, tu.getToken(), sel.get(0).bid, sel.get(1).bid, sel.get(2).bid, vo.content, vo.modifiedDate, type, vo.cid, new Callback<ResponseVo>() {
                        @Override
                        public void success(ResponseVo responseVo, Response response) {
                            Log.i("test", "업데이트 성공!");
                            goToActivity(CardWritingActivity.this, MainActivity.class);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.i("test", "업데이트 실패!");
                        }
                    });

                }

            }else if(sel.size() == 4){
                if ((Long) vo.cid == 0) {

                    taskService.createCard(typedFile, tu.getToken(), sel.get(0).bid, sel.get(1).bid, sel.get(2).bid, sel.get(3).bid, vo.content, vo.modifiedDate, type, new Callback<ResponseVo>() {
                        @Override
                        public void success(ResponseVo responseVo, Response response) {
                            Log.i("test", "card success" + responseVo.state + ", error: " + responseVo.error);
                            goToActivity(CardWritingActivity.this, MainActivity.class);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.i("test", "card error: " + error);
                        }
                    });
                }

            }else if(sel.size() == 5){
                if ((Long) vo.cid == 0) {

                    taskService.createCard(typedFile, tu.getToken(), sel.get(0).bid, sel.get(1).bid, sel.get(2).bid, sel.get(3).bid, sel.get(4).bid,vo.content, vo.modifiedDate, type, new Callback<ResponseVo>() {
                        @Override
                        public void success(ResponseVo responseVo, Response response) {
                            Log.i("test", "card success" + responseVo.state + ", error: " + responseVo.error);
                            goToActivity(CardWritingActivity.this, MainActivity.class);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.i("test", "card error: " + error);
                        }
                    });

                }

            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        List<String> fragmentTitleList = new ArrayList<String>();

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


