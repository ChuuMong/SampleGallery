package com.motionblue.samplegallery.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.motionblue.samplegallery.R;
import com.motionblue.samplegallery.data.model.ImageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeeJongHun on 2016-06-21.
 */
public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final OnItemClickListener listener;
    private List<ImageData> galleryList;

    public GalleryAdapter(OnItemClickListener listener) {
        this.listener = listener;
        this.galleryList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ImageViewHolder viewHolder = (ImageViewHolder) holder;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, position);
            }
        });
        Glide.with(viewHolder.itemView.getContext())
                .load(galleryList.get(position).getImageFile())
                .asBitmap()
                .dontAnimate()
                .centerCrop()
                .override(200, 200)
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public void add(List<ImageData> paths) {
        galleryList.addAll(paths);
        notifyDataSetChanged();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView;
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View v, int position);
    }

}
