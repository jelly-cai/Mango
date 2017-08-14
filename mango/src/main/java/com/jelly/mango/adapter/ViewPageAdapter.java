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
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.jelly.mango.MultiplexImage;
import com.jelly.mango.ProgressGlide.ProgressTarget;
import com.jelly.mango.R;

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
            ImageView image = (ImageView) view.findViewById(R.id.image);
            ProgressBar loadImage = (ProgressBar) view.findViewById(R.id.load);

            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);

            int type = images.get(position).getType();

            MyProgressTarget<Bitmap> myProgressTarget = new MyProgressTarget<>(new BitmapImageViewTarget(image), loadImage);
            String model = images.get(position).getPath();
            myProgressTarget.setModel(model);
            if(type == MultiplexImage.ImageType.GIF){

            }else{
                Glide.with(context).asBitmap().load(model).into(myProgressTarget);
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

        private final ProgressBar progressBar;

        public MyProgressTarget(Target<Z> target, ProgressBar progressBar) {
            super(target);
            this.progressBar = progressBar;
        }

        @Override
        public float getGranualityPercentage() {
            return super.getGranualityPercentage();
        }

        @Override
        protected void onConnecting() {

        }

        @Override
        protected void onDownloading(long bytesRead, long expectedLength) {
            progressBar.setProgress((int) (100 * bytesRead / expectedLength));
            Log.e("zzzz", bytesRead + "/" + expectedLength);
        }

        @Override
        protected void onDownloaded() {
            Log.e("zzzz", "onDownloaded");
        }

        @Override
        protected void onDelivered() {

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
