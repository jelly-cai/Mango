package com.jelly.imagebrowse.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jelly.imagebrowse.R;

import java.util.List;

/**
 *  图片列表适配器
 * Created by Jelly on 2016/9/3.
 */
public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImageHolder>{

    private List<String> dataList;
    private Context context;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener itemClickListener;

    public ImageRecyclerAdapter(Context context,List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(inflater.inflate(R.layout.item_image,parent,false),itemClickListener);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        Glide.with(context).load(dataList.get(position)).thumbnail(0.1f).into(holder.image);
    }

    public void setItemClickListener(OnRecyclerItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public static class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView image;
        private OnRecyclerItemClickListener itemClickListener;

        public ImageHolder(View itemView,OnRecyclerItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener == null) return;
            itemClickListener.click(itemView,getAdapterPosition());
        }

    }

}
