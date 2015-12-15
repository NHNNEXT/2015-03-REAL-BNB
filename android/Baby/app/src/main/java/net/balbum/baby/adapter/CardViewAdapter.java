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
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.balbum.baby.CardWritingActivity;
import net.balbum.baby.MainActivity;
import net.balbum.baby.R;
import net.balbum.baby.Util.Config;
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
    static final int CARD_MODIFY = 1;
    List<GeneralCardVo> cards;
    int layout;
    Context context;
    boolean open = true;
    Typeface typeface;
            Animation anim;
    TaskService taskService;

    public CardViewAdapter(List<GeneralCardVo> cards, Context context, int layout){
        this.cards = cards;
        this.context = context;
        this.layout = layout;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/milkyway.ttf");
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            viewHolder pvh = new viewHolder(v);
            return pvh;
        }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        final boolean[] flag = {false};

        holder.diary_text.setText(cards.get(position).content);
        holder.date.setText(cards.get(position).modifiedDate);
        // holder.photo.setImageBitmap(ConvertFileToBitmapUtil.convertBitmap(cards.get(position).cardImg));

        Log.d("test", "img Url test" + Config.URL + cards.get(position).cardImg);
        Picasso.with(context)
                .load((Config.URL + cards.get(position).cardImg))
                .placeholder(R.drawable.eggplant)
                .into(holder.photo);

        holder.diary_text.setText(cards.get(position).content);
        holder.diary_text.setTypeface(typeface);
        babiesInfo(holder.profile_container, position);

        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCardSetting(holder, flag);
            }
        });

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "position 넘기기 전: " + position);
                deleteCard(position);
                Log.d("test", "cid" + cards.get(position).cid);
            }
        });
        holder.modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCard(position);
            }
        });
    }

    private void modifyCard(int position) {

        Intent intent = new Intent(context, CardWritingActivity.class);
        intent.putExtra("type", CARD_MODIFY);
        GeneralCardVo vo = cards.get(position);
        intent.putExtra("generalCardVo", vo);
        context.startActivity(intent);
    }

    private void deleteCard(int position) {
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.deleteCard(cards.get(position).cid, new Callback<ResponseVo>() {
            @Override
            public void success(ResponseVo responseVo, Response response) {
                Log.d("test", "state: " + responseVo.state);
                Log.d("test", "delete 성공");

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", "delete 실패");
            }
        });

        Toast.makeText(context, "delete~~", Toast.LENGTH_SHORT).show();
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

    private void babiesInfo(LinearLayout profile_container, int position) {
        int idx = cards.get(position).babies.size();
        Log.d("test", "애기 수 : " + idx);

        LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(60, 60);
        LinearLayout.LayoutParams tvParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 60);
        for(int i=0; i<idx; i++){
            LinearLayout linLayout = new LinearLayout(context);
            linLayout.setOrientation(LinearLayout.HORIZONTAL);
            linLayout.setGravity(Gravity.CENTER_VERTICAL);
            ImageView iv_profile = new ImageView(context);
            iv_profile.setImageResource(R.drawable.eggplant);
            linLayout.addView(iv_profile, imageParam);
            TextView tv = new TextView(context);
            tv.setText("13개월");
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
            tv.setLayoutParams(tvParam);
            tv.setGravity(Gravity.CENTER);
            linLayout.addView(tv);
            ((LinearLayout) profile_container).addView(linLayout);
        }
    }


    @Override
    public int getItemCount() {

        if(cards == null || cards.size() ==0){
            return 0;
        }
            return cards.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView date, diary_text;
        ImageView photo;
        LinearLayout profile_container;
        LinearLayout delete_modify_layout;
        Button delete_btn;
        Button modify_btn;

        viewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            date = (TextView)itemView.findViewById(R.id.tv_date);
            photo = (ImageView)itemView.findViewById(R.id.iv_image);
            diary_text = (TextView)itemView.findViewById(R.id.diary_text);
            profile_container = (LinearLayout) itemView.findViewById(R.id.profile_container);
            delete_modify_layout = (LinearLayout)itemView.findViewById(R.id.delete_modify_layout);
            delete_btn = (Button)itemView.findViewById(R.id.delete_btn);
            modify_btn = (Button)itemView.findViewById(R.id.modify_btn);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
