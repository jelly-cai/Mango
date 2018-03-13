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
import com.jelly.mango.ImageBrowseActivity;
import com.jelly.mango.MultiplexImage;
import com.jelly.mango.R;
import com.jelly.mango.progressGlide.GlideApp;
import com.jelly.mango.progressGlide.MangoBitmapTarget;
import com.jelly.mango.progressGlide.MangoGIFDrawableTarget;
import com.jelly.mango.progressGlide.MangoProgressTarget;
import com.jelly.mango.progressGlide.OMangoProgressTarget;
import com.jelly.mango.progressview.RingProgressView;

import java.lang.ref.SoftReference;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Mango ViewPageAdapter
 * Created by Jelly on 2016/3/10.
 */
public class ViewPageAdapter extends PagerAdapter {

    private static final String TAG = ViewPageAdapter.class.getName();

    private Context context;
    /**
     * Image source
     */
    private List<MultiplexImage> images;
    private SparseArray<SoftReference<View>> cacheView;
    /**
     * Previous Position
     */
    private int prePosition;
    /**
     * Curr Position
     */
    private int position;

    public ViewPageAdapter(Context context, List<MultiplexImage> images) {
        this.context = context;
        this.images = images;
        cacheView = new SparseArray<>(images.size());
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View view = cacheView.get(position) != null ? cacheView.get(position).get() : null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.vp_item_image,container,false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) view.findViewById(R.id.image);
            viewHolder.progressView = (RingProgressView) view.findViewById(R.id.progress);
            viewHolder.oImage = (ImageView) view.findViewById(R.id.oImage);

            viewHolder.progressView.initProgress();

            //if is load original image before,hidden thumbnails ImageView and load original image
            if(images.get(position).isLoading()){
                viewHolder.photoViewAttacher = new PhotoViewAttacher(viewHolder.oImage);
                viewHolder.image.setVisibility(View.INVISIBLE);
                glideLoadImage(viewHolder.photoViewAttacher,viewHolder.progressView,viewHolder.image,position,true);
            }else{
                viewHolder.photoViewAttacher = new PhotoViewAttacher(viewHolder.image);
                glideLoadImage(viewHolder.photoViewAttacher,viewHolder.progressView,viewHolder.image,position,false);
            }
            viewHolder.photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    Activity activity = (Activity) context;
                    activity.finish();
                }
            });
            view.setTag(viewHolder);
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

    /**
     * Update PhotoView on the slide the ViewPager
     *
     * @param position ViewPager position
     */
    public void updatePhotoView(int position){
        View view = cacheView.get(position) != null ? cacheView.get(position).get() : null;
        if(view != null){
            PhotoViewAttacher photoViewAttacher = ((ViewHolder) view.getTag()).photoViewAttacher;
            photoViewAttacher.update();
        }
    }

    /**
     * Get previous position after the slide
     *
     * @return previous position
     */
    public int getPrePosition() {
        return prePosition;
    }

    /**
     * Get current position
     *
     * @return current position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set previous position and current position
     *
     * @param position ViewPager position
     */
    public void setPosition(int position) {
        this.prePosition = this.position;
        this.position = position;
    }

    /**
     * <p>Start load image with glide</p>
     *
     * <p>If is GIF image use MangoGIFDrawableTarget and others use MangoBitmapTarget</p>
     * <p>if is Original image use OMangoProgressTarget and others use MangoProgressTarget</p>
     *
     * @param photoViewAttacher {@link uk.co.senab.photoview.PhotoViewAttacher}
     * @param progressView {@link com.jelly.mango.progressview.RingProgressView}
     * @param image ImageView
     * @param position ViewPager position
     * @param isO whether
     */
    public void glideLoadImage(PhotoViewAttacher photoViewAttacher,RingProgressView progressView,ImageView image,int position,boolean isO){
        int type = images.get(position).getType(); //image type
        //get image url(Thumbnails or Original)
        String model = null;
        if(isO){
            model = TextUtils.isEmpty(images.get(position).getOPath()) ? images.get(position).getTPath() : images.get(position).getOPath();
        }else{
            model = TextUtils.isEmpty(images.get(position).getTPath()) ? images.get(position).getOPath() : images.get(position).getTPath();
        }
        if(type == MultiplexImage.ImageType.GIF){
            if(isO){
                OMangoProgressTarget<GifDrawable> gifTarget = new OMangoProgressTarget<>(context, new MangoGIFDrawableTarget(photoViewAttacher), progressView,image);
                gifTarget.setModel(model);
                GlideApp.with(context).asGif().load(model).fitCenter().into(gifTarget);
            }else{
                MangoProgressTarget<GifDrawable> gifTarget = new MangoProgressTarget<>(context, new MangoGIFDrawableTarget(photoViewAttacher), progressView);
                gifTarget.setModel(model);
                GlideApp.with(context).asGif().load(model).fitCenter().into(gifTarget);
            }
        }else{
            if(isO){
                OMangoProgressTarget<Bitmap> otherTarget = new OMangoProgressTarget<>(context, new MangoBitmapTarget(photoViewAttacher),progressView,image);
                otherTarget.setModel(model);
                GlideApp.with(context).asBitmap().load(model).fitCenter().into(otherTarget);
            }else{
                MangoProgressTarget<Bitmap> otherTarget = new MangoProgressTarget<>(context, new MangoBitmapTarget(photoViewAttacher),progressView);
                otherTarget.setModel(model);
                GlideApp.with(context).asBitmap().load(model).fitCenter().into(otherTarget);
            }
        }

    }

    /**
     * Start original image
     */
    public void loadOriginalPicture(){
        //If is not exists original image,return
        if(TextUtils.isEmpty(images.get(position).getOPath())) return;
        //If is loading,return
        if(images.get(position).isLoading()) return;
        images.get(position).setLoading(true);
        ((ImageBrowseActivity)context).hiddenOriginalButton(position);
        View view = cacheView.get(position) != null ? cacheView.get(position).get() : null;
        if(view != null) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            viewHolder.progressView.setVisibility(View.VISIBLE);
            viewHolder.photoViewAttacher = new PhotoViewAttacher(viewHolder.oImage);
            viewHolder.progressView.initProgress();

            glideLoadImage(viewHolder.photoViewAttacher, viewHolder.progressView, viewHolder.image, position, true);
        }
    }

    /**
     * Cache ViewPager ViewHolder
     */
    private class ViewHolder{
        ImageView image;
        ImageView oImage;
        RingProgressView progressView;
        PhotoViewAttacher photoViewAttacher;
    }

}
