package io.chuumong.samplegallery.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

import io.chuumong.samplegallery.R;
import io.chuumong.samplegallery.data.model.ImageData;
import io.chuumong.samplegallery.ui.adapter.GalleryAdapter;
import io.chuumong.samplegallery.ui.weiget.MarginDecoration;

public class MainActivity extends AppCompatActivity implements GalleryAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView gallery;
    private GalleryAdapter galleryAdapter;
    private ArrayList<ImageData> imageDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageDataList = initGalleryPath();

        gallery = (RecyclerView) findViewById(R.id.gallery);
        gallery.addItemDecoration(new MarginDecoration(this));
        gallery.setHasFixedSize(true);

        galleryAdapter = new GalleryAdapter(this);
        gallery.setAdapter(galleryAdapter);

        galleryAdapter.add(imageDataList);
    }

    private ArrayList<ImageData> initGalleryPath() {
        File galleryDir = new File(/* 폴더 경로 입력 */);

        Log.d(TAG, "getGalleryList Gallery Path : " + galleryDir.getPath());

        // 파일이 존재하지 않으면 False
        if (!galleryDir.exists()) {

            // 파일이 존재하지 않으면 디렉토리 생성, 경로에 해당하는 모든 디렉토리를 생성
            if (!galleryDir.mkdirs()) {
                // TODO: 2016-06-21 디렉토리 생성 실패 시 작성
            }
        }

        String galleryPath = galleryDir.getPath();

        String[] paths = new File(galleryPath).list();
        ArrayList<ImageData> datas = new ArrayList<>();
        for (String path : paths) {
            datas.add(new ImageData(galleryPath + File.separator + path));
        }

        return datas;
    }

    @Override
    public void onItemClick(View v, int position) {
        Log.d(TAG, "onItemClick position : " + position);
        ImageActivity.start(this, imageDataList, position);
    }
}
