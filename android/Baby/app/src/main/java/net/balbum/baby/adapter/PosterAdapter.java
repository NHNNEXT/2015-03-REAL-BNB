package net.balbum.baby.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.balbum.baby.R;

/**
 * Created by hyes on 2015. 11. 10..
 */

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.viewHolder> {
    Bitmap[] bitmap;
    Context context;


    public PosterAdapter(Bitmap[] bitmap, Context context) {
        this.bitmap = bitmap;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_list_row, parent, false);
        viewHolder pvh = new viewHolder(view);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        holder.poster.setImageBitmap(bitmap[position]);
    }


    @Override
    public int getItemCount() {

        if (bitmap == null || bitmap.length == 0) {
            return 0;
        }
        return bitmap.length;
    }


    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView poster;

        viewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster_image);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }
}