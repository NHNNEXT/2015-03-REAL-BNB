package net.balbum.baby.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.balbum.baby.R;
import net.balbum.baby.VO.BabyVo;

import java.util.List;

/**
 * Created by hyes on 2015. 12. 20..
 */
public class BabyListAdapter extends RecyclerView.Adapter<BabyListAdapter.BabyVoHolder> {

    List<BabyVo> babyVoList;

    public BabyListAdapter(List<BabyVo> babyVoList) {
        this.babyVoList = babyVoList;
    }

    @Override
    public BabyVoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.baby_list_row, parent, false);
        BabyVoHolder pvh = new BabyVoHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(BabyVoHolder holder, int position) {

//        holder.photo
        holder.name.setText(babyVoList.get(position).babyName);
        holder.birthday.setText(babyVoList.get(position).babyBirth);

        Log.d("test", "ㅂㅈㄷㅂㅈ");
    }


    @Override
    public int getItemCount() {
        return 0;
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

