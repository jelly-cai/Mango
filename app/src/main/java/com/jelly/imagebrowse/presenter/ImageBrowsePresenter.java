package com.jelly.imagebrowse.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import com.jelly.imagebrowse.view.ImageBrowseView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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

        Picasso.with(view.getMyContext()).load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
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
                    bitmap.compress(Bitmap.CompressFormat.valueOf(imageType), 100, fos);
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
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

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

