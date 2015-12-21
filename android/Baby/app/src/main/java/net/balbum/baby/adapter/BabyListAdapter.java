package net.balbum.baby.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.balbum.baby.R;
import net.balbum.baby.Util.Config;
import net.balbum.baby.VO.BabyVo;

import java.util.List;

/**
 * Created by hyes on 2015. 12. 20..
 */
public class BabyListAdapter extends RecyclerView.Adapter<BabyListAdapter.BabyVoHolder> {

    List<BabyVo> babyVoList;
    Context context;

    public BabyListAdapter(List<BabyVo> babyVoList, Context context) {
        this.babyVoList = babyVoList;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public BabyVoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.baby_list_row, parent, false);
        BabyVoHolder pvh = new BabyVoHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(BabyVoHolder holder, int position) {

        holder.name.setText(babyVoList.get(position).babyName);
        holder.birthday.setText(babyVoList.get(position).babyBirth);
        holder.gender.setText(babyVoList.get(position).babyGender + "");
        Picasso.with(context).load(Config.URL+ babyVoList.get(position).babyImg).into(holder.photo);
        Log.d("test", "img" + babyVoList.get(position).babyImg);
    }

    @Override
    public int getItemCount() {
        return babyVoList.size();
    }


    class BabyVoHolder extends RecyclerView.ViewHolder {

        ImageView photo;
        TextView name;
        TextView birthday;
        TextView gender;

        public BabyVoHolder(View itemView) {
            super(itemView);
            photo = (ImageView)itemView.findViewById(R.id.baby_photo);
            name = (TextView)itemView.findViewById(R.id.baby_name);
            birthday = (TextView)itemView.findViewById(R.id.baby_birthday);
            gender = (TextView)itemView.findViewById(R.id.baby_gender);
        }
    }
}

