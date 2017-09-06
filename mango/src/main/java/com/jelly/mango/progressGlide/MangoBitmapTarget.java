package com.jelly.mango.progressGlide;

import android.graphics.Bitmap;

import com.bumptech.glide.request.target.ImageViewTarget;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Jelly on 2017/8/20.
 */

public class MangoBitmapTarget extends ImageViewTarget<Bitmap> {

    private static final String TAG = MangoBitmapTarget.class.getName();

    private PhotoViewAttacher photoViewAttacher;

    public MangoBitmapTarget(PhotoViewAttacher photoViewAttacher) {
        super(photoViewAttacher.getImageView());
        this.photoViewAttacher = photoViewAttacher;
    }

    /**
     * Sets the {@link android.graphics.Bitmap} on the view using {@link
     * android.widget.ImageView#setImageBitmap(android.graphics.Bitmap)}.
     *
     * @param resource The bitmap to display.
     */
    @Override
    protected void setResource(Bitmap resource) {
        view.setImageBitmap(resource);
        photoViewAttacher.update();
    }

}
