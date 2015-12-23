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
import net.balbum.baby.Util.Define;
import net.balbum.baby.VO.BabyTagVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyes on 2015. 11. 11..
 */
public class BabyTagAdapter extends RecyclerView.Adapter<BabyTagAdapter.BabyViewHolder> {

    List<BabyTagVo> babyTag;
    Context context;
    ArrayList<Long> selectedList =new ArrayList<Long>();


    public BabyTagAdapter(List<BabyTagVo> babyTag, Context context) {
        this.babyTag = babyTag;
        this.context = context;

        Log.d("test", "to the 착: " + babyTag.size());
        if(babyTag != null && babyTag.size() == 1){
            babyTag.get(0).isSelected = true;
            Log.d("test", "for 걸림: " + babyTag.size());
        }
    }

    @Override
    public BabyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.baby_names_row, parent, false);
        BabyViewHolder pvh = new BabyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final BabyViewHolder holder, final int position) {
//        holder.photo.setImageBitmap(ConvertFileToBitmapUtil.convertBitmap(babyTag.get(position).image));

        Picasso.with(context).load(Define.URL+ babyTag.get(position).babyImg).into(holder.photo);

        holder.name.setText(babyTag.get(position).name);

        if (!babyTag.get(position).isSelected) {
            holder.photo.setAlpha(0.3f);
        } else {
            holder.photo.setAlpha(1.0f);
        }

        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                babyTag.get(position).isSelected = !babyTag.get(position).isSelected;
                notifyDataSetChanged();

            }
        });
    }

    public ArrayList<Long> getSelectedNames(){
        for(int i =0; i< babyTag.size(); i++){
            if(babyTag.get(i).isSelected){
                selectedList.add(babyTag.get(i).bId);
                Log.d("test", "bId넣어지는지 " + babyTag.get(i).bId);
            }
        }
        return selectedList;
    }

//    public ArrayList<String> getSelectedNames(){
//        for(int i =0; i< babyTag.size(); i++){
//            if(babyTag.get(i).isSelected){
//                selectedList.add(babyTag.get(i).name.toString());
//            }
//        }
//        return selectedList;
//    }

    @Override
    public int getItemCount() {
        return babyTag.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {

//        BabyViewHolder viewHolder;
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.baby_names_row, parent, false);
//
//
//            viewHolder = new BabyViewHolder();
//            viewHolder.photo = (ImageView) convertView.findViewById(R.id.baby_iv);
//            viewHolder.name = (CheckBox) convertView.findViewById(R.id.baby_cb);
//
//
//            convertView.setTag(viewHolder);
//        }
//        // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다
//        else {
//            viewHolder = (BabyViewHolder) convertView.getTag();
//        }
//
//        viewHolder.photo.setImageBitmap(ConvertFileToBitmapUtil.convertBitmap(babyTag.get(position).cardImg));
//        viewHolder.name.setText(babyTag.get(position).name);
//
//
//        return convertView;
//    }


    public ArrayList<Long> getSelectedList() {
        return selectedList;
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

