package io.chuumong.samplegallery.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.chuumong.samplegallery.data.model.ImageData;
import io.chuumong.samplegallery.ui.fragment.ImagePagerFragment;

/**
 * Created by LeeJongHun on 2016-06-23.
 */
public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    private List<ImageData> imageDataList;

    public ImagePagerAdapter(FragmentManager fm) {
        super(fm);
        this.imageDataList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return ImagePagerFragment.createFragment(imageDataList.get(position));
    }

    @Override
    public int getCount() {
        return imageDataList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void add(List<ImageData> lists) {
        imageDataList.addAll(lists);

        notifyDataSetChanged();
    }

    public ImageData get(int position) {
        return imageDataList.get(position);
    }

    public void imageFileRemove(int position) {
        imageDataList.get(position).getImageFile().delete();
        imageDataList.remove(position);

        notifyDataSetChanged();
    }
}
