package io.chuumong.samplegallery.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.chuumong.samplegallery.R;
import io.chuumong.samplegallery.data.model.ImageData;


/**
 * Created by LeeJongHun on 2016-06-22.
 */
public class ImagePagerFragment extends Fragment {

    private static final String IMAGE_DATA = "IMAGE_DATA";

    public static Fragment createFragment(ImageData imageData) {
        Fragment fragment = new ImagePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(IMAGE_DATA, imageData);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageData imageData = getArguments().getParcelable(IMAGE_DATA);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        Glide.with(this).load(imageData.getImageFile()).fitCenter().into(image);
    }
}

