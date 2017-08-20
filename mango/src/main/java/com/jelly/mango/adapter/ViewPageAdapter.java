package com.jelly.mango.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.target.Target;
import com.jelly.mango.MultiplexImage;
import com.jelly.mango.R;
import com.jelly.mango.progressGlide.GlideApp;
import com.jelly.mango.progressGlide.ProgressImageViewTarget;
import com.jelly.mango.progressGlide.ProgressTarget;
import com.jelly.mango.progressview.ProgressImageView;

import java.lang.ref.SoftReference;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 图片浏览ViewPageAdapter
 * Created by Jelly on 2016/3/10.
 */
public class ViewPageAdapter extends PagerAdapter {

    private static String TAG = ViewPageAdapter.class.getName();

    private Context context;
    private List<MultiplexImage> images;
    private SparseArray<SoftReference<View>> cacheView;
    private ViewGroup containerTemp;
    private int prePosition;
    private int position;

    public ViewPageAdapter(Context context, List<MultiplexImage> images) {
        this.context = context;
        this.images = images;
        cacheView = new SparseArray<>(images.size());
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        if(containerTemp == null) containerTemp = container;
        View view = cacheView.get(position) != null ? cacheView.get(position).get() : null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.vp_item_image,container,false);
            view.setTag(position);
            ProgressImageView image = (ProgressImageView) view.findViewById(R.id.image);
            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);

            int type = images.get(position).getType();

            MyProgressTarget<Bitmap> myProgressTarget = new MyProgressTarget<>(context, new ProgressImageViewTarget(photoViewAttacher),image);
            String model = images.get(position).getPath();
            myProgressTarget.setModel(model);
            if(type == MultiplexImage.ImageType.GIF){

            }else{
                //Glide.with(context).asBitmap().load(model).into(myProgressTarget);
                GlideApp.with(context).asBitmap().load(model).into(myProgressTarget);
            }
            //Glide.with(context).load(images.get(position).getPath()).asBitmap().into(new MyTarget(photoViewAttacher,loadImage));

            photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    Activity activity = (Activity) context;
                    activity.finish();
                }
            });
            view.setTag(photoViewAttacher);
            cacheView.put(position,new SoftReference(view));
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


    public void updatePhotoView(int position){
        View view = cacheView.get(position) != null ? cacheView.get(position).get() : null;
        if(view != null){
            PhotoViewAttacher photoViewAttacher = (PhotoViewAttacher) view.getTag();
            photoViewAttacher.update();
        }
    }

    public int getPrePosition() {
        return prePosition;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.prePosition = this.position;
        this.position = position;
    }

    static class MyProgressTarget<Z> extends ProgressTarget<String, Z> {

        private ProgressImageView progressImageView;

        public MyProgressTarget(Context context,Target<Z> target,ProgressImageView progressImageView) {
            super(context,target);
            this.progressImageView = progressImageView;
        }

        @Override
        public float getGranualityPercentage() {
            return super.getGranualityPercentage();
        }

        @Override
        protected void onConnecting() {
            Log.d(TAG, "onConnecting: ");
        }

        @Override
        protected void onDownloading(long bytesRead, long expectedLength) {
            progressImageView.setProgress((int) (100 * bytesRead / expectedLength));
            Log.d(TAG, "onDownloading: " + (int) (100 * bytesRead / expectedLength));
        }

        @Override
        protected void onDownloaded() {
            Log.d(TAG, "onDownloaded: ");
        }

        @Override
        protected void onDelivered() {
            progressImageView.setProgress(100);
            progressImageView.setFinish();
        }
    }


    /*
    private class MyTarget extends SimpleTarget<Bitmap>{
        private PhotoViewAttacher viewAttacher;
        private ProgressBar load;

        public MyTarget(PhotoViewAttacher viewAttacher,ProgressBar load){
            this.viewAttacher = viewAttacher;
            this.load = load;
        }

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            load.setVisibility(View.INVISIBLE);
            viewAttacher.getImageView().setImageBitmap(resource);
            viewAttacher.update();
        }

    }*/

}
