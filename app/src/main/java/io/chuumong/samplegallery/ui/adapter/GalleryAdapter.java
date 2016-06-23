package io.chuumong.samplegallery.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.chuumong.samplegallery.R;
import io.chuumong.samplegallery.data.model.ImageData;


/**
 * Created by LeeJongHun on 2016-06-21.
 */
public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final OnItemClickListener listener;
    private List<ImageData> galleryList;
    private boolean isCheck = false;

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
        final ImageViewHolder viewHolder = (ImageViewHolder) holder;
        final ImageData imageData = galleryList.get(position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isCheck) {
                    listener.onItemClick(view, position);
                }
                else {
                    if (viewHolder.checkbox.isChecked()) {
                        imageData.setChecked(false);
                        viewHolder.checkbox.setChecked(false);
                    }
                    else {
                        imageData.setChecked(true);
                        viewHolder.checkbox.setChecked(true);
                    }
                }
            }
        });

        if (isCheck) {
            viewHolder.checkbox.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.checkbox.setVisibility(View.GONE);
        }

        if (imageData.isChecked()) {
            viewHolder.checkbox.setChecked(true);
        }
        else {
            viewHolder.checkbox.setChecked(false);
        }

        Glide.with(viewHolder.itemView.getContext())
                .load(imageData.getImageFile())
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

    public void add(List<ImageData> imageDataList) {
        galleryList.addAll(imageDataList);

        notifyDataSetChanged();
    }

    public void setCheckState(boolean isCheck) {
        this.isCheck = isCheck;
        if (!isCheck) {
            for (ImageData imageData : galleryList) {
                imageData.setChecked(false);
            }
        }
        notifyDataSetChanged();
    }

    public List<ImageData> getCheckImageList() {
        List<ImageData> imageDataList = new ArrayList<>();
        for (ImageData imageData : galleryList) {
            if (imageData.isChecked()) {
                imageDataList.add(imageData);
            }
        }

        return imageDataList;
    }

    public void imageFileDelete() {
        if (!isCheck) {
            return;
        }

        for (int i = 0; i < galleryList.size(); i++) {
            ImageData imageData = galleryList.get(i);

            if (imageData.isChecked()) {
                imageData.getImageFile().delete();
                galleryList.remove(i);
                i--;
            }
        }

        notifyDataSetChanged();
    }

    public void clear() {
        galleryList.clear();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkbox;
        ImageView image;

        public ImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View v, int position);
    }

}
