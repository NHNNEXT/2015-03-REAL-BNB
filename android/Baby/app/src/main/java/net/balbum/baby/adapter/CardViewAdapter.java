package net.balbum.baby.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.balbum.baby.CardShareActivity;
import net.balbum.baby.CardWritingActivity;
import net.balbum.baby.MainActivity;
import net.balbum.baby.R;
import net.balbum.baby.Util.ActivityUtil;
import net.balbum.baby.Util.Define;
import net.balbum.baby.Util.RoundedTransformation;
import net.balbum.baby.Util.ToastUtil;
import net.balbum.baby.VO.GeneralCardVo;
import net.balbum.baby.VO.ResponseVo;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hyes on 2015. 11. 10..
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.viewHolder> {
    List<GeneralCardVo> cards;
    int layout;
    Context context;
    Typeface typeface;


    public CardViewAdapter(List<GeneralCardVo> cards, Context context, int layout) {
        this.cards = cards;
        this.context = context;
        this.layout = layout;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/milkyway.ttf");
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        viewHolder pvh = new viewHolder(view);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        final boolean[] flag = {false};


        if(cards.get(position).getType().equals("EVENT")){
            holder.event_date.setText(cards.get(position).modifiedDate);
            holder.event_memo.setText(cards.get(position).content);
            Log.d("test", "drawable" + cards.get(position).cardImg);

        }else {
            holder.event_date.setVisibility(View.GONE);
            holder.event_memo.setVisibility(View.GONE);
            holder.date.setText(cards.get(position).modifiedDate);
            holder.diary_text.setText(cards.get(position).content);
            holder.diary_text.setTypeface(typeface);
        }
        Picasso.with(context)
                .load((Define.URL + cards.get(position).cardImg))
                .into(holder.photo);

        //.placeholder(R.drawable.eggplant)


        babiesInfo(holder.profile_container, position);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCard(position);
            }
        });
        holder.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCard(position);
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareCard(position);
            }
        });

        holder.more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCardSetting(holder, flag);
            }
        });

//        holder.container.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                cardCapture(holder.container, cards.get(position).cid);
//
//            }
//        },1000);
        //poster test 카드 화면 view 생
//        Log.d("test", "dt width: " + holder.diary_text.getWidth());
//        Log.d("test", "cv width: " + holder.diary_text.getText());

    }

//    private void modifyDate(int position, View v) {
//        DialogHandler pickerDialog = new DialogHandler(v);
//        pickerDialog.show(context);
//    }

    private void shareCard(int position) {
        Intent intent = new Intent(context, CardShareActivity.class);
        intent.putExtra("card", cards.get(position).cid);
        context.startActivity(intent);

    }

    private void modifyCard(int position) {

        Intent intent = new Intent(context, CardWritingActivity.class);
        intent.putExtra("type", Define.CARD_MODIFY);
        Long card_id = cards.get(position).cid;
        intent.putExtra("cId", card_id);
        intent.putExtra("cImg", cards.get(position).cardImg);
        context.startActivity(intent);
    }

    private void deleteCard(int position) {

        TaskService taskService = ServiceGenerator.createService(TaskService.class);

        taskService.deleteCard(cards.get(position).cid, new Callback<ResponseVo>() {
            @Override
            public void success(ResponseVo responseVo, Response response) {
                Log.d("test", "state: " + responseVo.state);
                Log.d("test", "delete 성공");

                ActivityUtil.goToActivity(context, MainActivity.class);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", "delete 실패");
            }
        });

        ToastUtil.show(context, "delete");
    }

    private void showCardSetting(viewHolder holder, boolean[] flag) {
        if (!flag[0]) {
            flag[0] = !flag[0];
            holder.delete_modify_layout.setVisibility(View.VISIBLE);

            //    holder.diary_text.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_card_alpha2));
            holder.photo.setImageAlpha(65);
        } else {
            holder.delete_modify_layout.setVisibility(View.INVISIBLE);
            holder.photo.setImageAlpha(255);
            flag[0] = !flag[0];
        }
    }

    private void babiesInfo(final LinearLayout profile_container, int position) {

        profile_container.removeAllViews();

        final int idx = cards.get(position).babies.size();
//        Log.d("test", "baby" + cards.get(position).babies.size() + " , position " + position);

        final LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(85, 85);
        final LinearLayout.LayoutParams tvParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 85);


            for (int i = 0; i < idx; i++) {

                LinearLayout linLayout = new LinearLayout(context);
                linLayout.setOrientation(LinearLayout.HORIZONTAL);
                linLayout.setGravity(Gravity.CENTER_VERTICAL);

                ImageView iv_profile = new ImageView(context);

                Log.d("test", "애기 프로필 사진 " + cards.get(position).getBabies().get(i).babyName + cards.get(position).getBabies().get(i).babyDate);
                Picasso.with(context).load(Define.URL + cards.get(position).getBabies().get(i).babyImg).transform(new RoundedTransformation()).into(iv_profile);

               // iv_profile.setImageResource(babies.get(i).babyImg);
                iv_profile.setScaleType(ImageView.ScaleType.FIT_XY);
                linLayout.addView(iv_profile, imageParam);

                TextView tv = new TextView(context);
                tv.setText(cards.get(position).getBabies().get(i).babyDate);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                tv.setLayoutParams(tvParam);
                tv.setGravity(Gravity.CENTER);
                linLayout.addView(tv);
                profile_container.addView(linLayout);
            }

    }



    @Override
    public int getItemCount() {

        if (cards == null || cards.size() == 0) {
            return 0;
        }
        return cards.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView date, diary_text;
        TextView event_date;
        TextView event_memo;
        ImageView photo;
        LinearLayout profile_container;
        LinearLayout delete_modify_layout;
        TextView delete;
        TextView modify;
        ImageButton share;
        ImageButton more_btn;
        RelativeLayout container;


        viewHolder(View itemView) {
            super(itemView);
            container = (RelativeLayout)itemView.findViewById(R.id.card_layout);
            cv = (CardView) itemView.findViewById(R.id.cv);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            photo = (ImageView) itemView.findViewById(R.id.iv_image);
            diary_text = (TextView) itemView.findViewById(R.id.diary_text);
            profile_container = (LinearLayout) itemView.findViewById(R.id.profile_container);
            delete_modify_layout = (LinearLayout) itemView.findViewById(R.id.delete_modify_layout);
            delete = (TextView) itemView.findViewById(R.id.delete_btn);
            modify = (TextView) itemView.findViewById(R.id.modify_btn);
//            share = (TextView) itemView.findViewById(R.id.share_btn);
            share = (ImageButton) itemView.findViewById(R.id.share_btn);
            more_btn = (ImageButton) itemView.findViewById(R.id.more_btn);
            event_date = (TextView)itemView.findViewById(R.id.event_date);
            event_memo=(TextView)itemView.findViewById(R.id.event_memo);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

//    public void cardCapture(View container, long i){
//
////        int width_container = container.getWidth() ;//캡쳐할 레이아웃 크기
////
////        int height_container = container.getHeight() ;//캡쳐할 레이아웃 크기
//
//
//
//        container.setDrawingCacheEnabled(true);
//
//        container.buildDrawingCache(true);
//
//
///***********************핵심부분**********************************/
//        Bitmap captureView = Bitmap.createBitmap(container.getMeasuredWidth(),
//
//                container.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//
//        Canvas screenShotCanvas = new Canvas(captureView);
//
//        container.draw(screenShotCanvas);
///***********************핵심부분*****************************************/
//
//
//        FileOutputStream fos;
//        File fileRoute = null;
//        fileRoute = Environment.getExternalStorageDirectory();
//        String str_name = i+"";
//
//        try {
//
//            File path = new File(fileRoute,"temp");
//
//            if(!path.exists()){//if(!path.isDirectory()){
//                path.mkdirs();
//            }
//
//            fos = new FileOutputStream(fileRoute+"/temp/"+str_name+".png");
//            captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
//            container.setDrawingCacheEnabled(false);
//
//        }catch (FileNotFoundException e) {
//
//            e.printStackTrace();
//
//        }
//    }

}
