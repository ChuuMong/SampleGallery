package io.chuumong.samplegallery.data;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.chuumong.samplegallery.data.model.ImageData;
import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LeeJongHun on 2016-06-23.
 */
public class ImageDataRepository {

    public static Single<List<ImageData>> getImageData() {
        return Single.create(new Single.OnSubscribe<List<ImageData>>() {
            @Override
            public void call(SingleSubscriber<? super List<ImageData>> singleSubscriber) {
                singleSubscriber.onSuccess(initGalleryPath());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private static List<ImageData> initGalleryPath() {
        File galleryDir = new File(/* 폴더 경로 입력 */);

        // 파일이 존재하지 않으면 False
        if (!galleryDir.exists()) {

            // 파일이 존재하지 않으면 디렉토리 생성, 경로에 해당하는 모든 디렉토리를 생성
            if (!galleryDir.mkdirs()) {
                // TODO: 2016-06-21 디렉토리 생성 실패 시 작성
            }
        }

        String galleryPath = galleryDir.getPath();

        String[] paths = new File(galleryPath).list();
        ArrayList<ImageData> dataList = new ArrayList<>();
        for (String path : paths) {
            dataList.add(new ImageData(galleryPath + File.separator + path));
        }

        return dataList;
    }
}
