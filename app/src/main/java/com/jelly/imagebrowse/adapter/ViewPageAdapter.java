package com.jelly.imagebrowse.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jelly.imagebrowse.R;
import com.jelly.imagebrowse.utils.WindowUtil;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 图片浏览ViewPageAdapter
 * Created by Jelly on 2016/3/10.
 */
public class ViewPageAdapter extends PagerAdapter {
    private Context context;
    private List<String> images;
    private SparseArray<View> cacheView;
    private ViewGroup containerTemp;

    public ViewPageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
        cacheView = new SparseArray<>(images.size());
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        if(containerTemp == null) containerTemp = container;

        View view = cacheView.get(position);

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.vp_item_image,container,false);
            view.setTag(position);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);


            Glide.with(context).load(images.get(position)).asBitmap().into(new MyTarget(photoViewAttacher));

            photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    Activity activity = (Activity) context;
                    activity.finish();
                }
            });
            cacheView.put(position,view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    private class MyTarget extends SimpleTarget<Bitmap>{

        private PhotoViewAttacher viewAttacher;

        public MyTarget(PhotoViewAttacher viewAttacher){
            this.viewAttacher = viewAttacher;
        }

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            int width = resource.getWidth();
            int height = resource.getHeight();

            int newWidth = width;
            int newHeight = height;

            int screenWidth = WindowUtil.getInstance().getScreenWidth((Activity) context);
            int screenHeight = WindowUtil.getInstance().getScreenHeight((Activity) context);

            if(width > screenWidth){
                newWidth = screenWidth;
            }

            if(height > screenHeight){
                newHeight = screenHeight;
            }


            if(newWidth == width && newHeight == height){
                viewAttacher.getImageView().setImageBitmap(resource);
                viewAttacher.update();
                return;
            }

            //计算缩放比例
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float)newHeight) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth,scaleHeight);

            Log.v("size",width + "");
            Log.v("size",height + "");

            Bitmap resizeBitmap = Bitmap.createBitmap(resource,0,0,width,height,matrix,true);

            viewAttacher.getImageView().setImageBitmap(resizeBitmap);
            viewAttacher.update();
        }

    }

}
