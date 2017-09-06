package com.jelly.mango.progressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Jelly on 2017/8/24.
 */

public class TextImageButton extends AppCompatTextView{

    private static final String TAG = TextImageButton.class.getName();

    public TextImageButton(Context context) {
        super(context);
    }

    public TextImageButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        Paint paint = new Paint();
        paint.setARGB(50,100,100,100);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(0,0,width,height),13,13,paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(new RectF(0,0,width,height),13,13,paint);
        super.onDraw(canvas);

    }



}
