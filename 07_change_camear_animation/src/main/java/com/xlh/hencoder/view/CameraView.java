package com.xlh.hencoder.view;


import android.content.Context;
import android.graphics.Camera;
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
public class CameraView extends View {

    private static final int ANGLE = 120;
    private static final float RADIUS = Utils.dpToPixel(20);
    private static final float LENGTH = Utils.dpToPixel(100);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Camera camera = new Camera();

    public CameraView(Context context) {
        super(context);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {

        camera.rotateX(45);
        camera.setLocation(0,0,Utils.getCameraZ());
//        camera.setLocation(0,0,3);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //上半部分绘制
        canvas.save();
        canvas.translate(100+600/2,100+600/2);
        canvas.rotate(-20);
        canvas.clipRect(-600,-600,600,0);
        canvas.rotate(20);
        canvas.translate(-(100+600/2),-(100+600/2));
        canvas.drawBitmap(Utils.getAvatar(getResources(),600),100,100,paint);
        canvas.restore();

        //下半部分绘制
        canvas.save();
        canvas.translate(100+600/2,100+600/2);
        canvas.rotate(-20);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-600,0,600,600);
        canvas.rotate(20);
        canvas.translate(-(100+600/2),-(100+600/2));
        canvas.drawBitmap(Utils.getAvatar(getResources(),600),100,100,paint);
        canvas.restore();

    }

}
