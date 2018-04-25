package com.jelly.mango.progressview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Jelly on 2017/8/31.
 */

public class RingProgressView extends View {

    private static final String TAG = RingProgressView.class.getName();
    private Paint paint;
    private Context context;
    private int progress = 0;
    /**
     * is play animator
     */
    private boolean isAnimPlay = false;
    /**
     * last waiting progress animator
     */
    private int lastProgress;

    public RingProgressView(Context context) {
        super(context);
    }

    public RingProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setDither(true);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawProgress(canvas,progress);
    }

    public void initProgress(){
        progress = 0;
        lastProgress = 0;
        setProgress(3);
    }

    public void setProgress(int progress) {
        if(progress < this.progress) return;
        //handle is playing
        if(isAnimPlay){
            lastProgress = progress;
            return;
        }

        playProgressAnimator(progress);
    }

    /**
     * play progress animator
     * @param progress
     */
    private void playProgressAnimator(int progress){
        ValueAnimator animator = ValueAnimator.ofInt(this.progress,progress);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                RingProgressView.this.progress = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                isAnimPlay = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimPlay = false;

                if(lastProgress > RingProgressView.this.progress){
                    playProgressAnimator(RingProgressView.this.lastProgress);
                } else if (RingProgressView.this.progress == 100) {
                    RingProgressView.this.setVisibility(GONE);
                }

            }
        });
        animator.start();
    }

    public void drawProgress(Canvas canvas, int progress){
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        //set inner circle radius
        int innerCircle = dip2px(context, 18);
        //set ring circle wight
        int ringWidth = dip2px(context, 5);
        //draw outer circle
        this.paint.setARGB(255, 255 ,255, 255);
        int left = centerX - innerCircle;
        int top = centerY - innerCircle;
        int right = centerX + innerCircle;
        int bottom = centerY + innerCircle;
        paint.setStrokeWidth(ringWidth);
        canvas.drawArc(new RectF(left,top,right,bottom),-90, (float) (progress * 3.6),false,paint);
    }

    /**
     * dip to px
     * @param context Context
     * @param dpValue dp value
     * @return px value
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
