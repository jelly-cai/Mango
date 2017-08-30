package com.jelly.mango.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.Target;
import com.jelly.mango.MultiplexImage;
import com.jelly.mango.R;
import com.jelly.mango.progressGlide.GlideApp;
import com.jelly.mango.progressGlide.MangoBitmapTarget;
import com.jelly.mango.progressGlide.MangoGIFDrawableTarget;
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
            ProgressImageView image = (ProgressImageView) view.findViewById(R.id.image);
            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);
            String model = TextUtils.isEmpty(images.get(position).getTPath()) ? images.get(position).getOPath() : images.get(position).getTPath();
            glideLoadImage(photoViewAttacher,position,model,true);
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

    public void glideLoadImage(PhotoViewAttacher photoViewAttacher,int position,String model,boolean isPlaceHolder){

        int type = images.get(position).getType();

        if(type == MultiplexImage.ImageType.GIF){
            MangoProgressTarget<GifDrawable> gifTarget = new MangoProgressTarget<>(context, new MangoGIFDrawableTarget(photoViewAttacher), (ProgressImageView) photoViewAttacher.getImageView());
            gifTarget.setModel(model);
            if(isPlaceHolder){
                GlideApp.with(context).asGif().load(model).placeholder(R.drawable.placeholder).into(gifTarget);
            }else{
                GlideApp.with(context).asGif().load(model).into(gifTarget);
            }

        }else{
            MangoProgressTarget<Bitmap> otherTarget = new MangoProgressTarget<>(context, new MangoBitmapTarget(photoViewAttacher),(ProgressImageView) photoViewAttacher.getImageView());
            otherTarget.setModel(model);
            if(isPlaceHolder){
                GlideApp.with(context).asBitmap().load(model).placeholder(R.drawable.placeholder).into(otherTarget);
            }else{
                GlideApp.with(context).asBitmap().load(model).into(otherTarget);
            }

        }

    }



    /**
     * 加载原图
     */
    public void loadOriginalPicture(){
        View view = cacheView.get(position) != null ? cacheView.get(position).get() : null;
        if(view != null){
            ProgressImageView image = (ProgressImageView) view.findViewById(R.id.image);
            image.setProgress(true);
            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);
            String model = TextUtils.isEmpty(images.get(position).getOPath()) ? images.get(position).getTPath() : images.get(position).getOPath();
            glideLoadImage(photoViewAttacher,position,model,false);
            view.setTag(photoViewAttacher);
        }
    }

    static class MangoProgressTarget<Z> extends ProgressTarget<String, Z> {

        private ProgressImageView progressImageView;

        public MangoProgressTarget(Context context,Target<Z> target,ProgressImageView progressImageView) {
            super(context,target);
            this.progressImageView = progressImageView;
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
            Log.d(TAG, "onDownloading: "+(int) (100 * bytesRead / expectedLength));
            progressImageView.setProgress((int) (100 * bytesRead / expectedLength));
        }

        @Override
        protected void onDownloaded() {
            Log.d(TAG, "onDownloaded: ");
            progressImageView.setFinish();
        }

        @Override
        protected void onDelivered() {
            Log.d(TAG, "onDelivered: ");
            progressImageView.setProgress(100);
            progressImageView.setFinish();
        }
    }

}
