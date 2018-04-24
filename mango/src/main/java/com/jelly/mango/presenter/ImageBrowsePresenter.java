package com.jelly.mango.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jelly.mango.MultiplexImage;
import com.jelly.mango.view.ImageBrowseView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * ImageBrowsePresenter
 * Created by Jelly on 2016/9/3.
 */
public class ImageBrowsePresenter {

    private static final String TAG = ImageBrowsePresenter.class.getName();

    private ImageBrowseView view;
    private List<MultiplexImage> images;
    public ImageBrowsePresenter(ImageBrowseView view) {
        this.view = view;
    }

    public void loadImage(){
        Intent intent = view.getDataIntent();
        images = intent.getParcelableArrayListExtra("images");
        view.setImageBrowse(images,intent.getIntExtra("position",0));
    }

    public void saveImage() {

        final String imageUrl = !TextUtils.isEmpty(getPositionImage().getOPath()) ? getPositionImage().getOPath(): getPositionImage().getTPath();

        Glide.with(view.getMyContext()).asBitmap().load(imageUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                // 其次把文件插入到系统图库
                MediaStore.Images.Media.insertImage(view.getMyContext().getContentResolver(),resource,"title", "description");
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                    view.getMyContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
                }else{
                    view.getMyContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
                }
                Toast.makeText(view.getMyContext(),"保存成功",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public MultiplexImage getPositionImage(){
        return images.get(view.getPosition());
    }

    public List<MultiplexImage> getImages() {
        return images;
    }

}

