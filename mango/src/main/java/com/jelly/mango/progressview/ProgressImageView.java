package com.jelly.mango.progressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Jelly on 2017/8/20.
 */

public class ProgressImageView extends AppCompatImageView{

    private Paint paint;
    private Context context;
    private int progress = 0;
    private boolean isProgress = true;

    public ProgressImageView(Context context) {
        super(context);
    }

    public ProgressImageView(Context context, AttributeSet attrs) {
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
        if(isProgress)
            drawProgress(canvas);
        super.onDraw(canvas);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }


    public void setFinish(){
        isProgress = false;
        invalidate();
    }

    public void drawProgress(Canvas canvas){
        drawProgress(canvas,progress);
    }

    public void drawProgress(Canvas canvas, int progress){
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        int innerCircle = dip2px(context, 25); //设置内圆半径
        int ringWidth = dip2px(context, 10); //设置圆环宽度

        //绘制外圆
        this.paint.setARGB(255, 255 ,255, 255);
        //计算圆弧的位置
        int left = centerX - innerCircle;
        int top = centerY - innerCircle;
        int right = centerX + innerCircle;
        int bottom = centerY + innerCircle;
        paint.setStrokeWidth(ringWidth);


        canvas.drawArc(new RectF(left,top,right,bottom),-90, (float) (progress * 3.6),false,paint);

    }

    /**
     * dip to px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
