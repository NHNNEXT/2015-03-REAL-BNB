package net.balbum.baby;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.balbum.baby.Util.Define;
import net.balbum.baby.Util.RoundedTransformation;
import net.balbum.baby.Util.TimeUtil;
import net.balbum.baby.VO.BabyVo;
import net.balbum.baby.VO.CardIdListVo;
import net.balbum.baby.VO.CardListVo;
import net.balbum.baby.VO.GeneralCardVo;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hyes on 2015. 12. 16..
 */
public class PosterMakingActivity2 extends AppCompatActivity {

    List<Integer> posterImages = new ArrayList<Integer>();
    Context context;
    List<GeneralCardVo> cardList;
    List<BabyVo> babies;
    RelativeLayout container;
    EditText poster_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_making3);
        context = this;

        container = (RelativeLayout)findViewById(R.id.poster_container);
        poster_title = (EditText)findViewById(R.id.poster_title);

        List<Long> list = (ArrayList<Long>) getIntent().getSerializableExtra("cIds");
        Log.d("test", "PosterMakingActivity : " + list.toString());

        if(list != null){

            CardIdListVo req = new CardIdListVo();
            req.cardIds = list;
            TaskService taskService = ServiceGenerator.createService(TaskService.class);
            taskService.getCardList(req, new Callback<CardListVo>() {
                @Override
                public void success(CardListVo cardListVo, Response response) {
                    cardList = cardListVo.cardList;
                    //Log.d("test", "list" + cardListVo.cardList.get(0).content);
                    showPoster();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("test", "fail??");
                }
            });
        }
    }

    private void showPoster() {

        View card1 = findViewById(R.id.card1);
        ImageView c1_iv = (ImageView)card1.findViewById(R.id.iv_image);
        TextView c1_diary = (TextView)card1.findViewById(R.id.diary_text);
        TextView c1_date = (TextView)card1.findViewById(R.id.tv_date);
        LinearLayout c1_profile = (LinearLayout)card1.findViewById(R.id.profile_container);
        TextView c1_ed = (TextView)card1.findViewById(R.id.event_date);
        TextView c1_em = (TextView)card1.findViewById(R.id.event_memo);

        View card2 = findViewById(R.id.card2);
        ImageView c2_iv = (ImageView)card2.findViewById(R.id.iv_image);
        TextView c2_diary = (TextView)card2.findViewById(R.id.diary_text);
        TextView c2_date = (TextView)card2.findViewById(R.id.tv_date);
        LinearLayout c2_profile = (LinearLayout)card2.findViewById(R.id.profile_container);
        TextView c2_ed = (TextView)card2.findViewById(R.id.event_date);
        TextView c2_em = (TextView)card2.findViewById(R.id.event_memo);

        View card3 = findViewById(R.id.card3);
        ImageView c3_iv = (ImageView)card3.findViewById(R.id.iv_image);
        TextView c3_diary = (TextView)card3.findViewById(R.id.diary_text);
        TextView c3_date = (TextView)card3.findViewById(R.id.tv_date);
        LinearLayout c3_profile = (LinearLayout)card3.findViewById(R.id.profile_container);
        TextView c3_ed = (TextView)card3.findViewById(R.id.event_date);
        TextView c3_em = (TextView)card3.findViewById(R.id.event_memo);

        View card4 = findViewById(R.id.card4);
        ImageView c4_iv = (ImageView)card4.findViewById(R.id.iv_image);
        TextView c4_diary = (TextView)card4.findViewById(R.id.diary_text);
        TextView c4_date = (TextView)card4.findViewById(R.id.tv_date);
        LinearLayout c4_profile = (LinearLayout)card4.findViewById(R.id.profile_container);
        TextView c4_ed = (TextView)card4.findViewById(R.id.event_date);
        TextView c4_em = (TextView)card4.findViewById(R.id.event_memo);

        View card5 = findViewById(R.id.card5);
        ImageView c5_iv = (ImageView)card5.findViewById(R.id.iv_image);
        TextView c5_diary = (TextView)card5.findViewById(R.id.diary_text);
        TextView c5_date = (TextView)card5.findViewById(R.id.tv_date);
        LinearLayout c5_profile = (LinearLayout)card5.findViewById(R.id.profile_container);
        TextView c5_ed = (TextView)card5.findViewById(R.id.event_date);
        TextView c5_em = (TextView)card5.findViewById(R.id.event_memo);

        View card6 = findViewById(R.id.card6);
        ImageView c6_iv = (ImageView)card6.findViewById(R.id.iv_image);
        TextView c6_diary = (TextView)card6.findViewById(R.id.diary_text);
        TextView c6_date = (TextView)card6.findViewById(R.id.tv_date);
        LinearLayout c6_profile = (LinearLayout)card6.findViewById(R.id.profile_container);
        TextView c6_ed = (TextView)card6.findViewById(R.id.event_date);
        TextView c6_em = (TextView)card6.findViewById(R.id.event_memo);

        View card7 = findViewById(R.id.card7);
        ImageView c7_iv = (ImageView)card7.findViewById(R.id.iv_image);
        TextView c7_diary = (TextView)card7.findViewById(R.id.diary_text);
        TextView c7_date = (TextView)card7.findViewById(R.id.tv_date);
        LinearLayout c7_profile = (LinearLayout)card7.findViewById(R.id.profile_container);
        TextView c7_ed = (TextView)card7.findViewById(R.id.event_date);
        TextView c7_em = (TextView)card7.findViewById(R.id.event_memo);

        View card8 = findViewById(R.id.card8);
        ImageView c8_iv = (ImageView)card8.findViewById(R.id.iv_image);
        TextView c8_diary = (TextView)card8.findViewById(R.id.diary_text);
        TextView c8_date = (TextView)card8.findViewById(R.id.tv_date);
        LinearLayout c8_profile = (LinearLayout)card8.findViewById(R.id.profile_container);
        TextView c8_ed = (TextView)card8.findViewById(R.id.event_date);
        TextView c8_em = (TextView)card8.findViewById(R.id.event_memo);

        View card9 = findViewById(R.id.card9);
        ImageView c9_iv = (ImageView)card9.findViewById(R.id.iv_image);
        TextView c9_diary = (TextView)card9.findViewById(R.id.diary_text);
        TextView c9_date = (TextView)card9.findViewById(R.id.tv_date);
        LinearLayout c9_profile = (LinearLayout)card9.findViewById(R.id.profile_container);
        TextView c9_ed = (TextView)card9.findViewById(R.id.event_date);
        TextView c9_em = (TextView)card9.findViewById(R.id.event_memo);

        View card10 = findViewById(R.id.card10);
        ImageView c10_iv = (ImageView)card10.findViewById(R.id.iv_image);
        TextView c10_diary = (TextView)card10.findViewById(R.id.diary_text);
        TextView c10_date = (TextView)card10.findViewById(R.id.tv_date);
        LinearLayout c10_profile = (LinearLayout)card10.findViewById(R.id.profile_container);
        TextView c10_ed = (TextView)card10.findViewById(R.id.event_date);
        TextView c10_em = (TextView)card10.findViewById(R.id.event_memo);

        View card11 = findViewById(R.id.card11);
        ImageView c11_iv = (ImageView)card11.findViewById(R.id.iv_image);
        TextView c11_diary = (TextView)card11.findViewById(R.id.diary_text);
        TextView c11_date = (TextView)card11.findViewById(R.id.tv_date);
        LinearLayout c11_profile = (LinearLayout)card11.findViewById(R.id.profile_container);
        TextView c11_ed = (TextView)card11.findViewById(R.id.event_date);
        TextView c11_em = (TextView)card11.findViewById(R.id.event_memo);

        View card12 = findViewById(R.id.card12);
        ImageView c12_iv = (ImageView)card12.findViewById(R.id.iv_image);
        TextView c12_diary = (TextView)card12.findViewById(R.id.diary_text);
        TextView c12_date = (TextView)card12.findViewById(R.id.tv_date);
        LinearLayout c12_profile = (LinearLayout)card12.findViewById(R.id.profile_container);
        TextView c12_ed = (TextView)card12.findViewById(R.id.event_date);
        TextView c12_em = (TextView)card12.findViewById(R.id.event_memo);

        View card13 = findViewById(R.id.card13);
        ImageView c13_iv = (ImageView)card13.findViewById(R.id.iv_image);
        TextView c13_diary = (TextView)card13.findViewById(R.id.diary_text);
        TextView c13_date = (TextView)card13.findViewById(R.id.tv_date);
        LinearLayout c13_profile = (LinearLayout)card13.findViewById(R.id.profile_container);
        TextView c13_ed = (TextView)card13.findViewById(R.id.event_date);
        TextView c13_em = (TextView)card13.findViewById(R.id.event_memo);

        setCard(c1_ed, c1_em, c1_iv, c1_date, c1_profile, c1_diary, cardList.get(0));
        setCard(c2_ed, c2_em, c2_iv, c2_date, c2_profile, c2_diary, cardList.get(1));
        setCard(c3_ed, c3_em, c3_iv, c3_date, c3_profile, c3_diary, cardList.get(2));
        setCard(c4_ed, c4_em, c4_iv, c4_date, c4_profile, c4_diary, cardList.get(3));
        setCard(c5_ed, c5_em, c5_iv, c5_date, c5_profile, c5_diary, cardList.get(4));
        setCard(c6_ed, c6_em, c6_iv, c6_date, c6_profile, c6_diary, cardList.get(5));
        setCard(c7_ed, c7_em, c7_iv, c7_date, c7_profile, c7_diary, cardList.get(6));
        setCard(c8_ed, c8_em, c8_iv, c8_date, c8_profile, c8_diary, cardList.get(7));
        setCard(c9_ed, c9_em, c9_iv, c9_date, c9_profile, c9_diary, cardList.get(8));
        setCard(c10_ed, c10_em, c10_iv, c10_date, c10_profile, c10_diary, cardList.get(9));
        setCard(c11_ed, c11_em, c11_iv, c11_date, c11_profile, c11_diary, cardList.get(10));
        setCard(c12_ed, c12_em, c12_iv, c12_date, c12_profile, c12_diary, cardList.get(11));
        setCard(c13_ed, c13_em, c13_iv, c13_date, c13_profile, c13_diary, cardList.get(12));

    }

    private void setCard(TextView ed, TextView em, ImageView iv, TextView date, LinearLayout profile, TextView diary, GeneralCardVo card) {

        if(card.getType().equals("EVENT")){
            ed.setText(card.modifiedDate);
            em.setText(card.content);
        }else {
            date.setText(card.modifiedDate);
            diary.setText(card.content);
        }
        babiesInfo(profile, card);
        Picasso.with(context).load(Define.URL + card.cardImg).into(iv);
    }

    private void babiesInfo(final LinearLayout profile_container, GeneralCardVo card) {

        profile_container.removeAllViews();

        final int idx = card.getBabies().size();

        final LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(30, 30);
        final LinearLayout.LayoutParams tvParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 30);

        babies = new ArrayList<BabyVo>();

        for(int i = 0 ; i < idx; i++) {

            LinearLayout linLayout = new LinearLayout(context);
            linLayout.setOrientation(LinearLayout.HORIZONTAL);
            linLayout.setGravity(Gravity.CENTER_VERTICAL);

            ImageView iv_profile = new ImageView(context);
            Picasso.with(context).load(Define.URL + card.getBabies().get(i).babyImg).transform(new RoundedTransformation()).into(iv_profile);
            iv_profile.setScaleType(ImageView.ScaleType.FIT_XY);
            linLayout.addView(iv_profile, imageParam);

            TextView tv = new TextView(context);
            tv.setText(card.getBabies().get(i).babyDate);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 6);
            tv.setLayoutParams(tvParam);
            tv.setGravity(Gravity.CENTER);
            linLayout.addView(tv);
            ((LinearLayout) profile_container).addView(linLayout);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.poster_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            container.postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {
                        posterCapture(container);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            },1000);
        }
        return super.onOptionsItemSelected(item);
    }

    private void posterCapture(View container) {

        container.setDrawingCacheEnabled(true);

        container.buildDrawingCache(true);


/***********************핵심부분**********************************/
        Bitmap captureView = Bitmap.createBitmap(container.getMeasuredWidth(),

                container.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas screenShotCanvas = new Canvas(captureView);

        container.draw(screenShotCanvas);
/***********************핵심부분*****************************************/


        FileOutputStream fos;
        File fileRoute = null;
        fileRoute = Environment.getExternalStorageDirectory();
        String str_name = TimeUtil.getRecordedTime();

        try {

            File path = new File(fileRoute,"temp");

            if(!path.exists()){//if(!path.isDirectory()){
                path.mkdirs();
            }

            fos = new FileOutputStream(fileRoute+"/temp/poster_"+str_name+".png");
            captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
            container.setDrawingCacheEnabled(false);

        }catch (FileNotFoundException e) {

            e.printStackTrace();

        }
    }


}
