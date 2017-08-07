package com.jelly.mango;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by Jelly on 2017/8/7.
 */

public class Mango {
    public static final String TAG = "Mango";
    public static ImageSelectListener imageSelectListener;
    public static ArrayList<String> imageUrls;
    public static int position = 0;

    public static void open(Context context) throws Exception{

        if(imageUrls == null){
            throw new Exception("must set imageUrls");
        }

        Intent intent = new Intent(context,ImageBrowseActivity.class);
        intent.putStringArrayListExtra("images",imageUrls);
        intent.putExtra("position",position);
        context.startActivity(intent);
    }

    public static void setImages(ArrayList<String> imageUrls){
        Mango.imageUrls = imageUrls;
    }

    public static void setPosition(int position){
        Mango.position = position;
    }

    public static void setImageSelectListener(ImageSelectListener listener){
        Mango.imageSelectListener = listener;
    }



}
