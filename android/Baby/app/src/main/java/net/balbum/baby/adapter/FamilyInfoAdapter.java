package net.balbum.baby.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.balbum.baby.R;
import net.balbum.baby.VO.UserVo;

import java.util.List;

/**
 * Created by hyes on 2015. 11. 11..
 */
public class FamilyInfoAdapter extends RecyclerView.Adapter<FamilyInfoAdapter.FamilyViewHolder> {

    List<UserVo> familyList;
    Context context;

    public FamilyInfoAdapter(List<UserVo> familyList, Context context) {
        this.familyList = familyList;
        this.context = context;
    }

    @Override
    public FamilyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.family_info_row, parent, false);
        FamilyViewHolder pvh = new FamilyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final FamilyViewHolder holder, final int position) {

       // Picasso.with(context).load(Define.URL+ familyList.get(position).familyImage).into(holder.photo);
        holder.name.setText(familyList.get(position).familyName);

    }


    @Override
    public int getItemCount() {
        return familyList.size();
    }


    class FamilyViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;
        TextView name;

        public FamilyViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView)itemView.findViewById(R.id.family_iv);
            name = (TextView)itemView.findViewById(R.id.family_tv);
        }
    }
}

