package net.balbum.baby;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.balbum.baby.Util.Define;
import net.balbum.baby.VO.BabyVo;
import net.balbum.baby.VO.CardIdListVo;
import net.balbum.baby.VO.CardListVo;
import net.balbum.baby.VO.GeneralCardVo;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hyes on 2015. 12. 16..
 */
public class PosterMakingActivity extends AppCompatActivity {

    List<Integer> posterImages = new ArrayList<Integer>();
    Bitmap[] map;
    Context context;
    List<GeneralCardVo> cardList;
    List<BabyVo> babies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_making2);
        context = this;

        List<Long> list = (ArrayList<Long>) getIntent().getSerializableExtra("cIds");

        for(int i=0; i<list.size(); i++){
            Log.d("test", "list: " +i + ", " + list.get(i));
        }
        if(list != null){

            CardIdListVo req = new CardIdListVo();
            req.cardIds = list;
            TaskService taskService = ServiceGenerator.createService(TaskService.class);
            taskService.getCardList(req, new Callback<CardListVo>() {
                @Override
                public void success(CardListVo cardListVo, Response response) {
                    cardList = cardListVo.cardList;
                    Log.d("test", "list" + cardListVo.cardList.get(0).content);
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

        View card2 = findViewById(R.id.card2);
        ImageView c2_iv = (ImageView)card2.findViewById(R.id.iv_image);
        TextView c2_diary = (TextView)card2.findViewById(R.id.diary_text);
        TextView c2_date = (TextView)card2.findViewById(R.id.tv_date);
        LinearLayout c2_profile = (LinearLayout)card2.findViewById(R.id.profile_container);

        View card3 = findViewById(R.id.card3);
        ImageView c3_iv = (ImageView)card3.findViewById(R.id.iv_image);
        TextView c3_diary = (TextView)card3.findViewById(R.id.diary_text);
        TextView c3_date = (TextView)card3.findViewById(R.id.tv_date);
        LinearLayout c3_profile = (LinearLayout)card3.findViewById(R.id.profile_container);

        View card4 = findViewById(R.id.card4);
        ImageView c4_iv = (ImageView)card4.findViewById(R.id.iv_image);
        TextView c4_diary = (TextView)card4.findViewById(R.id.diary_text);
        TextView c4_date = (TextView)card4.findViewById(R.id.tv_date);
        LinearLayout c4_profile = (LinearLayout)card4.findViewById(R.id.profile_container);

        View card5 = findViewById(R.id.card5);
        ImageView c5_iv = (ImageView)card5.findViewById(R.id.iv_image);
        TextView c5_diary = (TextView)card5.findViewById(R.id.diary_text);
        TextView c5_date = (TextView)card5.findViewById(R.id.tv_date);
        LinearLayout c5_profile = (LinearLayout)card5.findViewById(R.id.profile_container);

        View card6 = findViewById(R.id.card6);
        ImageView c6_iv = (ImageView)card6.findViewById(R.id.iv_image);
        TextView c6_diary = (TextView)card6.findViewById(R.id.diary_text);
        TextView c6_date = (TextView)card6.findViewById(R.id.tv_date);
        LinearLayout c6_profile = (LinearLayout)card6.findViewById(R.id.profile_container);

        View card7 = findViewById(R.id.card7);
        ImageView c7_iv = (ImageView)card7.findViewById(R.id.iv_image);
        TextView c7_diary = (TextView)card7.findViewById(R.id.diary_text);
        TextView c7_date = (TextView)card7.findViewById(R.id.tv_date);
        LinearLayout c7_profile = (LinearLayout)card7.findViewById(R.id.profile_container);

        View card8 = findViewById(R.id.card8);
        ImageView c8_iv = (ImageView)card8.findViewById(R.id.iv_image);
        TextView c8_diary = (TextView)card8.findViewById(R.id.diary_text);
        TextView c8_date = (TextView)card8.findViewById(R.id.tv_date);
        LinearLayout c8_profile = (LinearLayout)card8.findViewById(R.id.profile_container);

        View card9 = findViewById(R.id.card9);
        ImageView c9_iv = (ImageView)card9.findViewById(R.id.iv_image);
        TextView c9_diary = (TextView)card9.findViewById(R.id.diary_text);
        TextView c9_date = (TextView)card9.findViewById(R.id.tv_date);
        LinearLayout c9_profile = (LinearLayout)card9.findViewById(R.id.profile_container);


        setCard(c1_iv, c1_date, c1_profile, c1_diary, cardList.get(0));
        setCard(c2_iv, c2_date, c2_profile, c2_diary, cardList.get(1));
        setCard(c3_iv, c3_date, c3_profile, c3_diary, cardList.get(2));
        setCard(c4_iv, c4_date, c4_profile, c4_diary, cardList.get(3));
        setCard(c5_iv, c5_date, c5_profile, c5_diary, cardList.get(4));
        setCard(c6_iv, c6_date, c6_profile, c6_diary, cardList.get(5));
        setCard(c7_iv, c7_date, c7_profile, c7_diary, cardList.get(6));
        setCard(c8_iv, c8_date, c8_profile, c8_diary, cardList.get(7));
        setCard(c9_iv, c9_date, c9_profile, c9_diary, cardList.get(8));

    }

    private void setCard(ImageView iv, TextView date, LinearLayout profile, TextView diary, GeneralCardVo card) {
        Picasso.with(context).load(Define.URL + card.cardImg).into(iv);
        date.setText(card.modifiedDate);
        diary.setText(card.content);
        babiesInfo(profile, card);

    }

    private void babiesInfo(final LinearLayout profile_container, GeneralCardVo card) {

        profile_container.removeAllViews();

        final int idx = card.babies.size();

        final LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(60, 60);
        final LinearLayout.LayoutParams tvParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 60);

        babies = new ArrayList<BabyVo>();
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.getBabies("token", new Callback<ArrayList<BabyVo>>() {
            @Override
            public void success(ArrayList<BabyVo> babyVos, Response response) {
                babies = babyVos;

                for (int i = 0; i < idx; i++) {

                    LinearLayout linLayout = new LinearLayout(context);
                    linLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linLayout.setGravity(Gravity.CENTER_VERTICAL);

                    ImageView iv_profile = new ImageView(context);

                    Picasso.with(context).load(Define.URL + babies.get(i).babyImg).into(iv_profile);

                    // iv_profile.setImageResource(babies.get(i).babyImg);
                    iv_profile.setScaleType(ImageView.ScaleType.FIT_XY);
                    linLayout.addView(iv_profile, imageParam);

                    TextView tv = new TextView(context);
                    tv.setText("13개월");
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    tv.setLayoutParams(tvParam);
                    tv.setGravity(Gravity.CENTER);
                    linLayout.addView(tv);
                    ((LinearLayout) profile_container).addView(linLayout);
                }
            }


            @Override
            public void failure(RetrofitError error) {

            }
        });
        //이 가족이 가진 애기 리스트를 가지고 loop돌림
//        List<Integer> baby_list = new ArrayList();
//        baby_list.add(R.drawable.b1);
//        baby_list.add(R.drawable.b2);
//        baby_list.add(R.drawable.b3);



    }


    private void initDummy() {
        posterImages.add(R.drawable.b1);
        posterImages.add(R.drawable.b2);
        posterImages.add(R.drawable.b3);
    }
}
