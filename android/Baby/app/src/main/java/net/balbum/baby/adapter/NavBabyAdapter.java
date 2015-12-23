package net.balbum.baby.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.balbum.baby.R;
import net.balbum.baby.Util.Config;
import net.balbum.baby.VO.BabyTagVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyes on 2015. 11. 11..
 */
public class NavBabyAdapter extends RecyclerView.Adapter<NavBabyAdapter.BabyViewHolder> {

    List<BabyTagVo> babyTag;
    Context context;
    ArrayList<Long> selectedList =new ArrayList<Long>();


    public NavBabyAdapter(List<BabyTagVo> babyTag, Context context) {
        this.babyTag = babyTag;
        this.context = context;

    }

    @Override
    public BabyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_baby_row, parent, false);
        BabyViewHolder bvh = new BabyViewHolder(view);
        return bvh;
    }

    @Override
    public void onBindViewHolder(final BabyViewHolder holder, final int position) {

        Picasso.with(context).load(Config.URL+ babyTag.get(position).babyImg).into(holder.photo);
        holder.name.setText(babyTag.get(position).name);


        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                babyTag.get(position).isSelected = !babyTag.get(position).isSelected;
                notifyDataSetChanged();

            }
        });
    }


    @Override
    public int getItemCount() {
        return babyTag.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class BabyViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;
        TextView name;

        public BabyViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView)itemView.findViewById(R.id.baby_iv);
            name = (TextView)itemView.findViewById(R.id.baby_tv);
        }
    }


}

