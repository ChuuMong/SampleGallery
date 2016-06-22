package io.chuumong.samplegallery.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import io.chuumong.samplegallery.R;
import io.chuumong.samplegallery.data.model.ImageData;
import io.chuumong.samplegallery.ui.fragment.ImagePagerFragment;

public class ImageActivity extends AppCompatActivity {

    private static final String IMAGE_DATA_LIST = "IMAGE_DATA_LIST";
    private static final String IMAGE_DATA_POSITION = "IMAGE_DATA_POSITION";

    public static void start(Context context, ArrayList<ImageData> imageDataList) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putParcelableArrayListExtra(IMAGE_DATA_LIST, imageDataList);
        context.startActivity(intent);
    }

    public static void start(Context context, ArrayList<ImageData> imageDataList, int position) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putParcelableArrayListExtra(IMAGE_DATA_LIST, imageDataList);
        intent.putExtra(IMAGE_DATA_POSITION, position);
        context.startActivity(intent);
    }

    private ViewPager imageViewPager;
    private ArrayList<ImageData> imageDataList;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageDataList = getIntent().getParcelableArrayListExtra(IMAGE_DATA_LIST);
        position = getIntent().getIntExtra(IMAGE_DATA_POSITION, 0);

        if (imageDataList == null) {
            throw new RuntimeException();
        }

        imageViewPager = (ViewPager) findViewById(R.id.image_view_pager);
        imageViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ImagePagerFragment.createFragment(imageDataList.get(position));
            }

            @Override
            public int getCount() {
                return imageDataList.size();
            }
        });

        if (position != 0) {
            imageViewPager.setCurrentItem(position);
        }
    }
}
