package net.balbum.baby.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

import net.balbum.baby.R;
import net.balbum.baby.Util.Define;
import net.balbum.baby.VO.GeneralCardVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyes on 2015. 12. 22..
 */
public class CardSelectingAdapter extends RecyclerView.Adapter<CardSelectingAdapter.ViewHolder> {

    Context context;
    List<GeneralCardVo> cards;
    Typeface typeface;
    OnItemClickListener itemClickListener;

    public CardSelectingAdapter(List<GeneralCardVo> cards, Context context) {
        this.cards = cards;
        this.context = context;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/milkyway.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_selecting_row, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.diary_text.setText(cards.get(position).content);
        holder.date.setText(cards.get(position).modifiedDate);
        Picasso.with(context)
                .load((Define.URL + cards.get(position).cardImg))
                .placeholder(R.drawable.eggplant)
                .into(holder.photo);

        holder.diary_text.setText(cards.get(position).content);
        holder.diary_text.setTypeface(typeface);
        babiesInfo(holder.profile_container, position);
    }

    private void babiesInfo(LinearLayout profile_container, int position) {

            profile_container.removeAllViews();

            int idx = cards.get(position).babies.size();

            LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(60, 60);
            LinearLayout.LayoutParams tvParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 60);

            //이 가족이 가진 애기 리스트를 가지고 loop돌림
            List<Integer> baby_list = new ArrayList();
            baby_list.add(R.drawable.b1);
            baby_list.add(R.drawable.b2);
            baby_list.add(R.drawable.b3);

            for (int i = 0; i < idx; i++) {

                LinearLayout linLayout = new LinearLayout(context);
                linLayout.setOrientation(LinearLayout.HORIZONTAL);
                linLayout.setGravity(Gravity.CENTER_VERTICAL);

                ImageView iv_profile = new ImageView(context);
                iv_profile.setImageResource(baby_list.get(i));
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
    public int getItemCount() {
        return cards.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        TextView date, diary_text;
        ImageView photo;
        LinearLayout profile_container;
        LinearLayout delete_modify_layout;
        TextView delete;
        TextView modify;
        ImageButton more_btn;
        RelativeLayout container;


        ViewHolder(View itemView) {
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
            more_btn = (ImageButton) itemView.findViewById(R.id.more_btn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener != null){
                itemClickListener.onItemClick(v, getPosition(), cards);
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position, List<GeneralCardVo> cards);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public List<GeneralCardVo> getCards() {
        return cards;
    }
}
