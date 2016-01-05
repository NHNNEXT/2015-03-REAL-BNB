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
import net.balbum.baby.Util.RoundedTransformation;
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
        // .placeholder(R.drawable.eggplant)

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
                } else {
                    selectedCardListLong.remove(cards.get(position).cid);
                }

                notifyDataSetChanged();

            }
        });

    }

    private void babiesInfo(LinearLayout profile_container, int position) {

        profile_container.removeAllViews();

        int idx = cards.get(position).babies.size();

        LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(40, 40);
        LinearLayout.LayoutParams tvParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 40);

        for (int i = 0; i < idx; i++) {

            LinearLayout linLayout = new LinearLayout(context);
            linLayout.setOrientation(LinearLayout.HORIZONTAL);
            linLayout.setGravity(Gravity.CENTER_VERTICAL);

            ImageView iv_profile = new ImageView(context);

            //Log.d("test", "애기 프로필 사진 " + cards.get(position).getBabies().get(i).babyName + cards.get(position).getBabies().get(i).babyDate);
            Picasso.with(context).load(Define.URL + cards.get(position).getBabies().get(i).babyImg).transform(new RoundedTransformation()).into(iv_profile);

            iv_profile.setScaleType(ImageView.ScaleType.FIT_XY);
            linLayout.addView(iv_profile, imageParam);

            TextView tv = new TextView(context);
            tv.setText(cards.get(position).getBabies().get(i).babyDate);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 6);
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
        TextView event_date;
        TextView event_memo;


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
            event_date = (TextView)itemView.findViewById(R.id.event_date);
            event_memo=(TextView)itemView.findViewById(R.id.event_memo);
        }
    }
}
