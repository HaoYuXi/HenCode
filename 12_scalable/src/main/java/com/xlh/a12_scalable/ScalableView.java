package com.xlh.a12_scalable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

/**
 * Date: 20-7-14
 * Time: 下午4:15
 * Author: dain
 */
class ScalableView extends View implements GestureDetector.OnGestureListener, Runnable {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    float IMAGE_WIDTH = Utils.dpToPixel(200);

    float originOffsetX;
    float originOffsetY;

    float offsetX;
    float offsetY;


    GestureDetectorCompat gestureDetectorCompat;

    OverScroller overScroller;

    public ScalableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);

        gestureDetectorCompat = new GestureDetectorCompat(context, this);
        overScroller = new OverScroller(context);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        originOffsetX = (getWidth() - bitmap.getWidth()) / 2f;
        originOffsetY = (getHeight() - bitmap.getHeight()) / 2f;


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.translate(offsetX, offsetY);
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetectorCompat.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent donw, MotionEvent event, float distanceX, float distanceY) {
        offsetX -= distanceX;
        offsetX = Math.min(offsetX, (getWidth() - bitmap.getWidth()) / 2);
        offsetX = Math.max(offsetX, -(getWidth() - bitmap.getWidth()) / 2);
        offsetY -= distanceY;
        offsetY = Math.min(offsetY, (getHeight() - bitmap.getHeight()) / 2);
        offsetY = Math.max(offsetY, -(getHeight() - bitmap.getHeight()) / 2);

        Log.i("TAG", "offsetX = " + offsetX + "       offsetY = " + offsetY);

        invalidate();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        overScroller.fling((int) originOffsetX, (int) originOffsetY, (int) velocityX, (int) velocityY,
//                -(int) (bitmap.getWidth() - getWidth()) / 2,
//                (int) (bitmap.getWidth() - getWidth()) / 2,
//                -(int) (bitmap.getHeight() - getWidth()) / 2,
//                (int) (bitmap.getHeight() - getWidth()) / 2);
//
//        postOnAnimation(this);

        return false;
    }

    @Override
    public void run() {
        if (overScroller.computeScrollOffset()) {
            originOffsetX = overScroller.getCurrX();
            originOffsetY = overScroller.getCurrY();
            invalidate();
            postOnAnimation(this);
        }
    }
}
