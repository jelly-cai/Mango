package com.jelly.mango;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2017/8/7.
 */

public class Mango {
    public static final String TAG = Mango.class.getName();
    public static ImageSelectListener imageSelectListener;
    public static List<MultiplexImage> images;
    public static int position = 0;

    public static void open(Context context) throws IllegalAccessError{
        if(images == null){
            throw new IllegalAccessError("must set imageUrls");
        }

        Intent intent = new Intent(context,ImageBrowseActivity.class);
        intent.putParcelableArrayListExtra("images", (ArrayList<? extends Parcelable>) images);
        intent.putExtra("position",position);
        context.startActivity(intent);
    }

    public static void setImages(List<MultiplexImage> images){
        Mango.images = images;
    }

    public static void setPosition(int position){
        Mango.position = position;
    }

    public static void setImageSelectListener(ImageSelectListener listener){
        Mango.imageSelectListener = listener;
    }



}
