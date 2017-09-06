package com.jelly.mango.progressGlide;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;
import com.jelly.mango.progressview.RingProgressView;

/**
 * show original image target
 * Created by Jelly on 2017/9/2.
 */

public class OMangoProgressTarget<Z> extends ProgressTarget<String, Z> {
    private static final String TAG = OMangoProgressTarget.class.getName();
    private RingProgressView progressView;
    private ImageView image;

    public OMangoProgressTarget(Context context, Target<Z> target, RingProgressView progressView, ImageView image) {
        super(context,target);
        this.progressView = progressView;
        this.image = image;
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

    /**
     * hidden {@link com.jelly.mango.progressview.RingProgressView},
     * set {@link com.jelly.mango.progressview.RingProgressView} to zero
     * and hidden show original ImageView
     *
     */
    @Override
    protected void onDelivered() {
        progressView.setProgress(100);
        progressView.setVisibility(View.INVISIBLE);
        image.setVisibility(View.INVISIBLE);
    }
}
