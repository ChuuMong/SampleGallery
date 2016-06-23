package io.chuumong.samplegallery.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.chuumong.samplegallery.App;
import io.chuumong.samplegallery.R;
import io.chuumong.samplegallery.data.ImageDataRepository;
import io.chuumong.samplegallery.data.model.ImageData;
import io.chuumong.samplegallery.ui.adapter.GalleryAdapter;
import io.chuumong.samplegallery.ui.weiget.MarginDecoration;
import rx.SingleSubscriber;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity implements GalleryAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_STORAGE = 1001;

    private Toolbar toolbar;
    private RecyclerView gallery;
    private Menu menu;

    private GalleryAdapter galleryAdapter;

    private boolean isCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRecyclerView();
        checkNeedPermission();
    }

    private boolean checkNeedPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_STORAGE);
                return true;
            }
        }

        return false;
    }

    private void initRecyclerView() {
        galleryAdapter = new GalleryAdapter(this);

        gallery = (RecyclerView) findViewById(R.id.gallery);
        gallery.setAdapter(galleryAdapter);

        gallery.addItemDecoration(new MarginDecoration(this));
        gallery.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ImageDataRepository.getImageData().subscribe(new SingleSubscriber<List<ImageData>>() {
            @Override
            public void onSuccess(List<ImageData> lists) {
                galleryAdapter.clear();
                galleryAdapter.add(lists);
            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "call: ", error);
            }
        });

        if (menu != null) {
            checkboxHide();
        }
    }

    private void checkboxHide() {
        isCheck = false;
        galleryAdapter.setCheckState(false);
        showOtherMenuItem(false);
    }

    @Override
    public void onItemClick(View v, int position) {
        Log.d(TAG, "onItemClick position : " + position);
        ImageActivity.start(this, position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check:
                isCheck = !isCheck;
                galleryAdapter.setCheckState(isCheck);

                if (isCheck) {
                    showOtherMenuItem(true);
                }
                else {
                    showOtherMenuItem(false);
                }
                break;
            case R.id.share:
                shareImage();
                break;
            case R.id.delete:
                galleryAdapter.imageFileDelete();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareImage() {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        ArrayList<Uri> imageUris = new ArrayList<>();
        for (ImageData imageData : galleryAdapter.getCheckImageList()) {
            if (imageData.isChecked()) {
                imageUris.add(Uri.fromFile(imageData.getImageFile()));
            }
        }

        intent.setType("image/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        startActivity(Intent.createChooser(intent, getString(R.string.share_image)));
    }

    private void showOtherMenuItem(boolean isShow) {
        if (isShow) {
            menu.findItem(R.id.check).setIcon(R.drawable.ic_clear_white_24dp);
        }
        else {
            menu.findItem(R.id.check).setIcon(R.drawable.ic_done_white_24dp);
        }

        menu.findItem(R.id.share).setVisible(isShow);
        menu.findItem(R.id.delete).setVisible(isShow);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ImageDataRepository.getImageData().subscribe(new Action1<List<ImageData>>() {
                        @Override
                        public void call(List<ImageData> lists) {
                            galleryAdapter.clear();
                            galleryAdapter.add(lists);
                        }
                    });
                }
                else {
                    Toast.makeText(this, getString(R.string.need_storege_permisstion), Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isCheck) {
            checkboxHide();
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        App.getRefWatcher(this).watch(this);
    }
}
