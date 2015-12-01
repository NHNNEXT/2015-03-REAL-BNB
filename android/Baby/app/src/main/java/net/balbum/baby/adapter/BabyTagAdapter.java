package net.balbum.baby.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.balbum.baby.R;
import net.balbum.baby.Util.ConvertFileToBitmapUtil;
import net.balbum.baby.VO.BabyTagVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyes on 2015. 11. 11..
 */
public class BabyTagAdapter extends RecyclerView.Adapter<BabyTagAdapter.BabyViewHolder> {

    private List<BabyTagVo> names;
    private Context context;
    private ArrayList<String> selectedList =new ArrayList<String>();




    public BabyTagAdapter(List<BabyTagVo> names, Context context) {
        this.names = names;
        this.context = context;

        if(names != null && names.size() == 1){
            names.get(0).isSelected = true;
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
        holder.photo.setImageBitmap(ConvertFileToBitmapUtil.convertBitmap(names.get(position).image));
        holder.name.setText(names.get(position).name);

        if (!names.get(position).isSelected) {
            holder.photo.setAlpha(0.3f);
        } else {
            holder.photo.setAlpha(1.0f);
        }

        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names.get(position).isSelected = !names.get(position).isSelected;
                notifyDataSetChanged();

            }
        });
    }

    public ArrayList<String> getSelectedNames(){
        for(int i =0; i< names.size(); i++){
            if(names.get(i).isSelected){
                selectedList.add(names.get(i).name.toString());
            }
        }
        return selectedList;
    }

    @Override
    public int getItemCount() {
        return names.size();
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
//        viewHolder.photo.setImageBitmap(ConvertFileToBitmapUtil.convertBitmap(names.get(position).imgUrl));
//        viewHolder.name.setText(names.get(position).name);
//
//
//        return convertView;
//    }


    public ArrayList<String> getSelectedList() {
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

