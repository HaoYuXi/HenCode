package com.xlh.a08_animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Date: 20-6-1
 * Time: 上午11:14
 * Author: dain
 */
class CircleView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float radius = Utils.dpToPixel(50);

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    {
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawCircle(getWidth()/2,getHeight()/2,radius,paint);

    }
}
