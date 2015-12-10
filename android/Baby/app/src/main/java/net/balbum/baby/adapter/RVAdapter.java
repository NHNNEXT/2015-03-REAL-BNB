package net.balbum.baby.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import net.balbum.baby.R;
import net.balbum.baby.Util.Config;
import net.balbum.baby.VO.GeneralCardVo;

import java.util.List;

/**
 * Created by hyes on 2015. 11. 10..
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.viewHolder> {
    private static final int CARD_MODIFY = 1;
    private static final String CORESERVER_URL = Config.URL;
    private List<GeneralCardVo> cards;
    private Context context;
    private boolean open = true;
    private Typeface typeface;
            Animation anim;


    public RVAdapter(List<GeneralCardVo> cards, Context context){
        this.cards = cards;
        this.context = context;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/milkyway.ttf");
//       anim = AnimationUtils.loadAnimation(context, R.anim.anim_card_alpha);
        }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_general_row, parent, false);
            viewHolder pvh = new viewHolder(v);
            return pvh;
        }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        final boolean[] flag = {false};



        holder.diary_text.setText(cards.get(position).content);
        holder.date.setText(cards.get(position).modifiedDate);
        // holder.photo.setImageBitmap(ConvertFileToBitmapUtil.convertBitmap(cards.get(position).imgUrl));

        Picasso.with(context)
                .load((cards.get(position).imgUrl))
                .placeholder(R.mipmap.ic_launcher)
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
                deleteCard(holder, position);
            }
        });
        holder.modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCard(holder, position);
            }
        });
    }

    private void modifyCard(viewHolder holder, int position) {

        Intent intent = new Intent(context, CardWritingActivity.class);
        intent.putExtra("type", CARD_MODIFY);
        GeneralCardVo vo = cards.get(position);
        intent.putExtra("generalCardVo", vo);
        context.startActivity(intent);
    }

    private void deleteCard(viewHolder holder, int position) {
        Toast.makeText(context, "delete~~", Toast.LENGTH_SHORT).show();
    }

    private void showCardSetting(viewHolder holder, boolean[] flag) {
        if (!flag[0]) {
            // holder.photo.setImageAlpha(64);
            //   holder.photo.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_card_alpha));

            flag[0] = !flag[0];

            holder.delete_modify_layout.setVisibility(View.VISIBLE);
            //  holder.diary_text.setVisibility(View.VISIBLE);

            //    holder.diary_text.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_card_alpha2));
            holder.photo.setImageAlpha(65);
//                    holder.cv.setAlpha(0.65f);

        } else {
            holder.delete_modify_layout.setVisibility(View.INVISIBLE);
            holder.photo.setImageAlpha(255);
            flag[0] = !flag[0];
            //    holder.diary_text.setVisibility(View.INVISIBLE);

        }

    }

    private void babiesInfo(LinearLayout profile_container, int position) {
//        int idx = cards.get(position).babies.size();
        int idx = 5;
        LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 60);

        for(int i=0; i<idx; i++){

            LinearLayout linLayout = new LinearLayout(context);
            linLayout.setOrientation(LinearLayout.HORIZONTAL);
            linLayout.setGravity(Gravity.CENTER_VERTICAL);

            ImageView iv_profile = new ImageView(context);
            iv_profile.setImageResource(R.mipmap.ic_launcher);

            linLayout.addView(iv_profile, lpView);

            TextView tv = new TextView(context);
            tv.setText("13개월");
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
            tv.setLayoutParams(lpView);
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
