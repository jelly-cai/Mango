package com.jelly.mango.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jelly.mango.MultiplexImage;
import com.jelly.mango.progressGlide.GlideApp;
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
    private String[] imageTypes = new String[] { ".jpg",".png", ".jpeg","webp"};

    public ImageBrowsePresenter(ImageBrowseView view) {
        this.view = view;
    }

    public void loadImage(){
        Intent intent = view.getDataIntent();
        images = intent.getParcelableArrayListExtra("images");
        view.setImageBrowse(images,intent.getIntExtra("position",0));
    }

    public void saveImage() {

        final String imageUrl = getPositionImage().getOPath();

        GlideApp.with(view.getMyContext()).asBitmap().load(imageUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                // 创建目录
                File appDir = new File(Environment.getExternalStorageDirectory(), "mango");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }

                String imageType = getImageType(imageUrl); //获取图片类型
                String fileName = System.currentTimeMillis() + "." + imageType;
                File file = new File(appDir, fileName);
                //保存图片
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    if (TextUtils.equals(imageType, "jpg")) imageType = "jpeg";
                    imageType = imageType.toUpperCase();
                    resource.compress(Bitmap.CompressFormat.valueOf(imageType), 100, fos);
                    fos.flush();
                    fos.close();
                    Toast.makeText(view.getMyContext(), "保存成功", Toast.LENGTH_SHORT).show(); //Toast
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 其次把文件插入到系统图库
                try {
                    MediaStore.Images.Media.insertImage(view.getMyContext().getContentResolver(), file.getAbsolutePath(), fileName, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                view.getMyContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
            }
        });
    }


    public MultiplexImage getPositionImage(){
        return images.get(view.getPosition());
    }

    public List<MultiplexImage> getImages() {
        return images;
    }

    public String getImageType(String imageUrl){
        String imageType = "";
        if(imageUrl.endsWith(imageTypes[0])){
            imageType = "jpg";
        }else if(imageUrl.endsWith(imageTypes[1])){
            imageType = "png";
        }else{
            imageType = "jpeg";
        }
        return imageType;
    }

}

