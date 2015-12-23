package net.balbum.baby.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.balbum.baby.R;
import net.balbum.baby.VO.FamilyVo;

import java.util.List;

/**
 * Created by hyes on 2015. 12. 20..
 */
public class WaitingFamilyAdapter extends RecyclerView.Adapter<WaitingFamilyAdapter.FamilyViewHolder> {

    List<FamilyVo> familyVoList;
    Context context;

    public WaitingFamilyAdapter(List<FamilyVo> babyVoList, Context context) {
        this.familyVoList = babyVoList;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FamilyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_wating_list_row, parent, false);
        FamilyViewHolder pvh = new FamilyViewHolder(view);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FamilyViewHolder holder, int position) {

        holder.name.setText(familyVoList.get(position).familyName);
        holder.role.setText(familyVoList.get(position).familyRole);
        //Picasso.with(context).load(Define.URL+ familyVoList.get(position).familyImage).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return familyVoList.size();
    }


    class FamilyViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;
        TextView name;
        TextView role;
        Button accept;

        public FamilyViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView)itemView.findViewById(R.id.family_image);
            name = (TextView)itemView.findViewById(R.id.family_name);
            role = (TextView)itemView.findViewById(R.id.family_role);
            accept = (Button)itemView.findViewById(R.id.accept_family);
        }
    }
}

