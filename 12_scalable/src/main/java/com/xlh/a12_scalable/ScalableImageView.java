package com.xlh.a12_scalable;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ScalableImageView extends View
{
    private static final float IMAGE_WIDTH = Utils.dpToPixel(300);

    Bitmap bitmap;
    Paint paint  = new Paint(Paint.ANTI_ALIAS_FLAG);

    float offsetX;
    float offsetY;
    public ScalableImageView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        offsetX = ((float)getWidth()- bitmap.getWidth()) / 2;
        offsetY = ((float)getHeight()- bitmap.getHeight()) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,offsetX,offsetY,paint);
    }
}
