package com.xlh.hencoder.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.xlh.hencoder.Utils;

/**
 * Date: 20-5-25
 * Time: 上午10:55
 * Author: dain
 */
public class DashBoard extends View {

    private static final int ANGLE = 120;
    private static final float RADIUS = Utils.dpToPixel(150);
    private static final float LENGTH = Utils.dpToPixel(100);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path pathArc = new Path();
    Path dash = new Path();
    PathDashPathEffect effect;

    public DashBoard(Context context) {
        super(context);
    }

    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dpToPixel(2));
        dash.addRect(0, 0, Utils.dpToPixel(2), Utils.dpToPixel(10), Path.Direction.CW);
        Path arc = new Path();
        arc.addArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS, 90 + ANGLE / 2, 360 - ANGLE);

        PathMeasure arcMeasure = new PathMeasure(arc, false);
        effect = new PathDashPathEffect(dash, (arcMeasure.getLength() - Utils.dpToPixel(2)) / 20, 0, PathDashPathEffect.Style.ROTATE);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画线
        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS, 90 + ANGLE / 2, 360 - ANGLE, false, paint);
        //画刻度
        paint.setPathEffect(effect);
        //画第二遍
        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS, 90 + ANGLE / 2, 360 - ANGLE, false, paint);
        paint.setPathEffect(null);
        //画指针

        canvas.drawLine(getWidth() / 2, getHeight() / 2,
                (float) Math.cos(Math.toRadians(getAngleFromMark(5))) * LENGTH + getWidth() / 2,
                (float) Math.cos(Math.toRadians(getAngleFromMark(5))) * LENGTH + getHeight() / 2,
                paint);


    }

    int getAngleFromMark(int mark) {
        return (int) (90 + (float) ANGLE / 2 + (360 - (float) ANGLE) / 20 * mark);
    }
}
