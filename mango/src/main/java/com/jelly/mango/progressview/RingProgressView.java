package com.jelly.mango.progressview;

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
    private boolean isScroller = false;

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
        setProgress(3);
    }

    public void setProgress(final int progress) {

        if(progress < this.progress) return;
        if(progress - this.progress < 5){
            this.progress = progress;
            invalidate();
            return;
        }
        if(isScroller) return;

        final int curProgress = this.progress;
        new Thread(new Runnable() {
            @Override
            public void run() {
                isScroller = true;
                for(int i=curProgress;i<progress;i++){
                    try {
                        Thread.sleep(20);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Log.d(TAG, "run: "+i);
                    RingProgressView.this.progress = i;
                    postInvalidate();
                }
                isScroller = false;
            }
        }).start();
    }

    public void drawProgress(Canvas canvas, int progress){
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        int innerCircle = dip2px(context, 18); //设置内圆半径
        int ringWidth = dip2px(context, 5); //设置圆环宽度
        //绘制外圆
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
