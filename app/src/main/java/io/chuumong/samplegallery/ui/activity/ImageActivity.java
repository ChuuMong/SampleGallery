package io.chuumong.samplegallery.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import io.chuumong.samplegallery.App;
import io.chuumong.samplegallery.R;
import io.chuumong.samplegallery.data.ImageDataRepository;
import io.chuumong.samplegallery.data.model.ImageData;
import io.chuumong.samplegallery.ui.adapter.ImagePagerAdapter;
import rx.functions.Action1;

public class ImageActivity extends AppCompatActivity {

    private static final String IMAGE_DATA_LIST = "IMAGE_DATA_LIST";
    private static final String IMAGE_DATA_POSITION = "IMAGE_DATA_POSITION";

    public static void start(Context context, int position) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(IMAGE_DATA_POSITION, position);
        context.startActivity(intent);
    }

    private Toolbar toolbar;
    private ViewPager imageViewPager;
    private ImagePagerAdapter imagePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final int position = getIntent().getIntExtra(IMAGE_DATA_POSITION, 0);

        imageViewPager = (ViewPager) findViewById(R.id.image_view_pager);
        imagePagerAdapter = new ImagePagerAdapter(getSupportFragmentManager());
        imageViewPager.setAdapter(imagePagerAdapter);

        ImageDataRepository.getImageData().subscribe(new Action1<List<ImageData>>() {
            @Override
            public void call(List<ImageData> lists) {
                imagePagerAdapter.add(lists);

                if (position != 0) {
                    imageViewPager.setCurrentItem(position, false);
                }
            }
        });
    }

    private int getCurrentItemPosition() {
        return imageViewPager.getCurrentItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Uri uri = Uri.fromFile(imagePagerAdapter.get(getCurrentItemPosition()).getImageFile());
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(intent, getString(R.string.share_image)));
                break;
            case R.id.delete:
                int position = getCurrentItemPosition();
                imagePagerAdapter.imageFileRemove(position);
                imageViewPager.setCurrentItem(position, true);
                break;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        App.getRefWatcher(this).watch(this);
    }
}
