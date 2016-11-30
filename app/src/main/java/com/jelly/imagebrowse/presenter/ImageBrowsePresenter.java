package com.jelly.imagebrowse.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.jelly.imagebrowse.view.ImageBrowseView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Jelly on 2016/9/3.
 */
public class ImageBrowsePresenter {

    private ImageBrowseView view;
    private List<String> images;
    private int position;
    private String[] imageTypes = new String[] { ".jpg",".png", ".jpeg","webp"};

    public ImageBrowsePresenter(ImageBrowseView view) {
        this.view = view;
    }

    public void loadImage(){
        Intent intent = view.getDataIntent();
        images = intent.getStringArrayListExtra("images");
        position = intent.getIntExtra("position",0);
        view.setImageBrowse(images,position);
    }

    public void saveImage(){
        //利用Picasso加载图片

        final String imageUrl = getPositionImage();

        Glide.with(view.getMyContext()).load(imageUrl).asBitmap().into(new Target<Bitmap>() {
            @Override
            public void onLoadStarted(Drawable placeholder) {

            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                // 创建目录
                File appDir = new File(Environment.getExternalStorageDirectory(), "JellyImage");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }

                String imageType = getImageType(imageUrl); //获取图片类型
                String fileName = System.currentTimeMillis() + "." + imageType;
                File file = new File(appDir, fileName);
                //保存图片
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    if(TextUtils.equals(imageType,"jpg")) imageType = "jpeg";
                    imageType = imageType.toUpperCase();
                    resource.compress(Bitmap.CompressFormat.valueOf(imageType), 100, fos);
                    fos.flush();
                    fos.close();
                    Toast.makeText(view.getMyContext(),"保存成功",Toast.LENGTH_SHORT).show(); //Toast
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
                // 最后通知图库更新
                view.getMyContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
            }

            @Override
            public void onLoadCleared(Drawable placeholder) {

            }

            @Override
            public void getSize(SizeReadyCallback cb) {

            }

            @Override
            public void setRequest(Request request) {

            }

            @Override
            public Request getRequest() {
                return null;
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onDestroy() {

            }
        });
    }

    public String getPositionImage(){
        return images.get(position);
    }

    public List<String> getImages() {
        return images;
    }

    public void setPosition(int position) {
        this.position = position;
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

