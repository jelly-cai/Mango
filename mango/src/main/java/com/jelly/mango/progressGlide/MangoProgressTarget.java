package com.jelly.mango.progressGlide;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.request.target.Target;
import com.jelly.mango.progressview.RingProgressView;

/**
 * show thumbnails image target
 * Created by Jelly on 2017/9/2.
 */

public class MangoProgressTarget<Z> extends ProgressTarget<String, Z> {

    private static final String TAG = MangoProgressTarget.class.getName();
    private RingProgressView progressView;

    public MangoProgressTarget(Context context, Target<Z> target, RingProgressView progressView) {
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

    /**
     * hidden {@link com.jelly.mango.progressview.RingProgressView}
     * and set {@link com.jelly.mango.progressview.RingProgressView} to zero
     */
    @Override
    protected void onDelivered() {
        progressView.setProgress(100);
        progressView.setVisibility(View.INVISIBLE);
    }
}
