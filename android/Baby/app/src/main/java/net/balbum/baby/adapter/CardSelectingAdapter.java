package net.balbum.baby.adapter;

import android.content.Context;
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
   // OnItemClickListener itemClickListener;
    public List<Long> selectedCardListLong;

    public CardSelectingAdapter(List<GeneralCardVo> cards, Context context) {
        this.cards = cards;
        this.context = context;
        selectedCardListLong = new ArrayList<Long>();
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/milkyway.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_selecting_row, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.diary_text.setText(cards.get(position).content);
        holder.date.setText(cards.get(position).modifiedDate);
        Picasso.with(context)
                .load((Define.URL + cards.get(position).cardImg))
                .placeholder(R.drawable.eggplant)
                .into(holder.photo);

        holder.diary_text.setText(cards.get(position).content);
        holder.diary_text.setTypeface(typeface);
        babiesInfo(holder.profile_container, position);

        if(cards.get(position).isSelected){
            holder.check_img.setVisibility(View.VISIBLE);
        }else{
            holder.check_img.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cards.get(position).isSelected = !cards.get(position).isSelected;

                if (cards.get(position).isSelected) {
                    selectedCardListLong.add(cards.get(position).cid);
                    Log.d("test", "add size: " + selectedCardListLong.size());
                } else {
                    selectedCardListLong.remove(cards.get(position).cid);
                    Log.d("test", "remove size: " + selectedCardListLong.size());
                }

                notifyDataSetChanged();

//                if (holder.check_img.getVisibility() == View.GONE) {
//                    holder.check_img.setVisibility(View.VISIBLE);
//                    selectedCardListLong.add(cards.get(position).cid);
//
//                    Log.d("test", "추가 후 사이즈" + selectedCardListLong.size());
//                } else {
//                    holder.check_img.setVisibility(View.GONE);
//                    selectedCardListLong.remove(cards.get(position).cid);
//                    Log.d("test", "추가 취소 후 사이즈" + selectedCardListLong.size());
//                }
            }
        });

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


    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView date, diary_text;
        ImageView photo;
        LinearLayout profile_container;
        LinearLayout delete_modify_layout;
        TextView delete;
        TextView modify;
        ImageButton more_btn;
        RelativeLayout container;
        ImageView check_img;


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
            check_img = (ImageView) itemView.findViewById(R.id.check_image);
        }
    }
}
