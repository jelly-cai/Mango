package com.jelly.mango;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ImageBean
 * Created by Jelly on 2017/8/13.
 */

public class MultiplexImage implements Parcelable {
    /**
     * original image
     */
    private String OPath;
    /**
     * thumbnails image
     */
    private String TPath;
    /**
     * image type
     */
    private int type;
    /**
     * whether load original image
     */
    private boolean Loading;

    public MultiplexImage(String TPath,String OPath, int type) {
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

    public boolean isLoading() {
        return Loading;
    }

    public void setLoading(boolean loading) {
        Loading = loading;
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
        dest.writeByte(this.Loading ? (byte) 1 : (byte) 0);
    }

    protected MultiplexImage(Parcel in) {
        this.OPath = in.readString();
        this.TPath = in.readString();
        this.type = in.readInt();
        this.Loading = in.readByte() != 0;
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
