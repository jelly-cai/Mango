package com.jelly.mango;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jelly on 2017/8/13.
 */

public class MultiplexImage implements Parcelable {
    private String OPath;
    private String TPath;
    private int type;

    public MultiplexImage(String OPath,String TPath, int type) {
        this.OPath = OPath;
        this.TPath = TPath;
        this.type = type;
    }

    public String getOPath() {
        return OPath;
    }

    public void setOPath(String OPath) {
        this.OPath = OPath;
    }

    public String getTPath() {
        return TPath;
    }

    public void setTPath(String TPath) {
        this.TPath = TPath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static final class ImageType{
        public static final int NORMAL = 1;
        public static final int GIF = 2;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.OPath);
        dest.writeString(this.TPath);
        dest.writeInt(this.type);
    }

    protected MultiplexImage(Parcel in) {
        this.OPath = in.readString();
        this.TPath = in.readString();
        this.type = in.readInt();
    }

    public static final Creator<MultiplexImage> CREATOR = new Creator<MultiplexImage>() {
        @Override
        public MultiplexImage createFromParcel(Parcel source) {
            return new MultiplexImage(source);
        }

        @Override
        public MultiplexImage[] newArray(int size) {
            return new MultiplexImage[size];
        }
    };
}
