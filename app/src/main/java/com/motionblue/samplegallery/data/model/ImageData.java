package com.motionblue.samplegallery.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Created by LeeJongHun on 2016-06-21.
 */
public class ImageData implements Parcelable {

    String path;
    File imageFile;

    public ImageData(String path) {
        this.path = path;
        this.imageFile = new File(path);
    }

    public String getPath() {
        return path;
    }

    public File getImageFile() {
        return imageFile;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeSerializable(this.imageFile);
    }

    protected ImageData(Parcel in) {
        this.path = in.readString();
        this.imageFile = (File) in.readSerializable();
    }

    public static final Parcelable.Creator<ImageData> CREATOR = new Parcelable.Creator<ImageData>() {
        @Override
        public ImageData createFromParcel(Parcel source) {
            return new ImageData(source);
        }

        @Override
        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };
}
