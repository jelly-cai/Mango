package com.jelly.mango.progressGlide;

import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bumptech.glide.request.target.ImageViewTarget;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Jelly on 2017/8/20.
 */

public class MangoBitmapTarget extends ImageViewTarget<Bitmap> {

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
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(params);
        view.setImageBitmap(resource);
        photoViewAttacher.update();
    }

}
