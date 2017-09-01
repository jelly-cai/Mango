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
import android.widget.ImageView;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.Target;
import com.jelly.mango.MultiplexImage;
import com.jelly.mango.R;
import com.jelly.mango.progressGlide.GlideApp;
import com.jelly.mango.progressGlide.MangoBitmapTarget;
import com.jelly.mango.progressGlide.MangoGIFDrawableTarget;
import com.jelly.mango.progressGlide.ProgressTarget;
import com.jelly.mango.progressview.RingProgressView;

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
            ImageView image = (ImageView) view.findViewById(R.id.image);
            RingProgressView progressView = (RingProgressView) view.findViewById(R.id.progress);
            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);
            glideLoadImage(photoViewAttacher,progressView,image,position,false);
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

    public void glideLoadImage(PhotoViewAttacher photoViewAttacher,RingProgressView progressView,ImageView image,int position,boolean isO){

        int type = images.get(position).getType();
        String model = null;
        if(isO){
            model = TextUtils.isEmpty(images.get(position).getOPath()) ? images.get(position).getTPath() : images.get(position).getOPath();
        }else{
            model = TextUtils.isEmpty(images.get(position).getTPath()) ? images.get(position).getOPath() : images.get(position).getTPath();
        }
        if(type == MultiplexImage.ImageType.GIF){
            MangoProgressTarget<GifDrawable> gifTarget = null;
            if(isO){
                gifTarget = new OMangoProgressTarget<>(context, new MangoGIFDrawableTarget(photoViewAttacher), progressView,image);
            }else{
                gifTarget = new MangoProgressTarget<>(context, new MangoGIFDrawableTarget(photoViewAttacher), progressView);
            }

            gifTarget.setModel(model);
            GlideApp.with(context).asGif().load(model).fitCenter().into(gifTarget);
        }else{
            MangoProgressTarget<Bitmap> otherTarget = null;
            if(isO){
                otherTarget = new OMangoProgressTarget<>(context, new MangoBitmapTarget(photoViewAttacher),progressView,image);
            }else{
                otherTarget = new MangoProgressTarget<>(context, new MangoBitmapTarget(photoViewAttacher),progressView);
            }

            otherTarget.setModel(model);
            GlideApp.with(context).asBitmap().load(model).fitCenter().into(otherTarget);
        }

    }

    /**
     * 加载原图
     */
    public void loadOriginalPicture(){
        View view = cacheView.get(position) != null ? cacheView.get(position).get() : null;
        if(view != null){
            ImageView oImage = (ImageView) view.findViewById(R.id.oImage);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            RingProgressView progressView = (RingProgressView) view.findViewById(R.id.progress);
            progressView.setVisibility(View.VISIBLE);
            progressView.setProgress(0);
            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);
            glideLoadImage(photoViewAttacher,progressView,image,position,true);
            view.setTag(photoViewAttacher);
        }
    }

    static class MangoProgressTarget<Z> extends ProgressTarget<String, Z> {

        private RingProgressView progressView;

        public MangoProgressTarget(Context context,Target<Z> target,RingProgressView progressView) {
            super(context,target);
            this.progressView = progressView;
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
            progressView.setProgress((int) (100 * bytesRead / expectedLength));
        }

        @Override
        protected void onDownloaded() {

        }

        @Override
        protected void onDelivered() {
            progressView.setProgress(100);
            progressView.setVisibility(View.INVISIBLE);
        }
    }

    static class OMangoProgressTarget<Z> extends MangoProgressTarget<Z>{

        private ImageView image;

        public OMangoProgressTarget(Context context,Target<Z> target,RingProgressView progressView,ImageView image){
            super(context,target,progressView);
            this.image = image;
        }

        @Override
        protected void onDelivered() {
            super.onDelivered();
            Log.d(TAG, "onDelivered: ");
            image.setVisibility(View.INVISIBLE);
        }
    }

}
