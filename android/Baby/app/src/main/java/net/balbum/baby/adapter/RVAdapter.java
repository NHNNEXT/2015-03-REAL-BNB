package net.balbum.baby.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import net.balbum.baby.R;
import net.balbum.baby.VO.GeneralCardVo;
import java.util.List;

/**
 * Created by hyes on 2015. 11. 10..
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.viewHolder>{

    private static final String CORESERVER_URL = "http://192.168.0.14:8080/";
    private List<GeneralCardVo> cards;
    private Context context;
    private boolean open = true;
    private Typeface typeface;
            Animation anim;


    public RVAdapter(List<GeneralCardVo> cards, Context context){
        this.cards = cards;
        this.context = context;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/milkyway.ttf");
//        anim = AnimationUtils.loadAnimation(context, R.anim.anim_card_alpha);
        }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_general_row, parent, false);
            viewHolder pvh = new viewHolder(v);
            return pvh;
        }

    @Override
    public void onBindViewHolder(final viewHolder holder, int position) {

    final boolean[] flag = {false};

            holder.diary_text.setText(cards.get(position).content);

            holder.date.setText(cards.get(position).modifiedDate);
           // holder.photo.setImageBitmap(ConvertFileToBitmapUtil.convertBitmap(cards.get(position).imgUrl));

            Picasso.with(context)
                .load((CORESERVER_URL+cards.get(position).imgUrl))
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.photo);

            holder.diary_text.setText(cards.get(position).content);
            holder.diary_text.setTypeface(typeface);

            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!flag[0]) {
                        // holder.photo.setImageAlpha(64);
                        //   holder.photo.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_card_alpha));

                        flag[0] = !flag[0];
                        //  holder.diary_text.setVisibility(View.VISIBLE);

                        //    holder.diary_text.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_card_alpha2));
                        holder.photo.setImageAlpha(65);
                        addUserTag(view);
                    } else {
                        holder.photo.setImageAlpha(255);
                        flag[0] = !flag[0];
                        //    holder.diary_text.setVisibility(View.INVISIBLE);
                        removeUserTag(view);
                    }
                }
            });


    //        holder.cv.setOnTouchListener(new View.OnTouchListener() {
    //            @Override
    //            public boolean onTouch(View view, MotionEvent motionEvent) {
    //               // view.getParent().requestDisallowInterceptTouchEvent(true);
    //
    //                if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
    //                    holder.photo.setImageAlpha(255);
    //                    flag[0] = !flag[0];
    //                    holder.diary_text.setVisibility(View.GONE);
    //                    removeUserTag(view);
    //                }
    //                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
    //                    holder.photo.setImageAlpha(64);
    //                    flag[0] = !flag[0];
    //                    holder.diary_text.setVisibility(View.VISIBLE);
    //                    addUserTag(view);
    //                }
    //
    //
    //                return true;
    //            }
    //        });
            }


    private void addUserTag(View view) {

            LinearLayout profile_container = (LinearLayout) view.findViewById(R.id.profile_container);

                profile_container.setVisibility(View.VISIBLE);

                LinearLayout linLayout = new LinearLayout(context);
                linLayout.setOrientation(LinearLayout.HORIZONTAL);
                linLayout.setGravity(Gravity.CENTER_VERTICAL);
                LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                ImageView iv_profile = new ImageView(context);
                iv_profile.setImageResource(R.mipmap.ic_launcher);
                linLayout.addView(iv_profile, lpView);

                TextView tv = new TextView(context);
                tv.setText("똘똘이 13개월");
                tv.setLayoutParams(lpView);
                tv.setGravity(Gravity.CENTER);
                linLayout.addView(tv);
                ((LinearLayout) profile_container).addView(linLayout);

            }

    private void removeUserTag(View view) {
            // view.setVisibility(View.GONE);
            view.findViewById(R.id.profile_container).setVisibility(View.GONE);
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

        viewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            date = (TextView)itemView.findViewById(R.id.tv_date);
            photo = (ImageView)itemView.findViewById(R.id.iv_image);
            diary_text = (TextView)itemView.findViewById(R.id.diary_text);
            profile_container = (LinearLayout) itemView.findViewById(R.id.profile_container);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
