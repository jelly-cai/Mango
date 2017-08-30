package com.jelly.mango.progressGlide;

import android.view.ViewGroup;
import android.view.WindowManager;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Jelly on 2017/8/20.
 */

public class MangoGIFDrawableTarget extends ImageViewTarget<GifDrawable> {

    private PhotoViewAttacher photoViewAttacher;

    public MangoGIFDrawableTarget(PhotoViewAttacher photoViewAttacher) {
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
    protected void setResource(GifDrawable resource) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(params);
        view.setImageDrawable(resource);
        photoViewAttacher.update();
    }

}
