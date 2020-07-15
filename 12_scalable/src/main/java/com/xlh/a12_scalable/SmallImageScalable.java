package com.xlh.a12_scalable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

import java.util.PriorityQueue;

/**
 * Date: 20-7-15
 * Time: 上午9:41
 * Author: dain
 */
class SmallImageScalable extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    Bitmap bitmap;
    int BITMAP_WIDTH = (int) Utils.dpToPixel(150);

    float originOffsetX, originOffsetY;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    GestureDetectorCompat gestureDetector;

    float scaleFraction;
    ObjectAnimator animator;

    boolean big;
    float smallScale, bigScale;


    public SmallImageScalable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), BITMAP_WIDTH);
        gestureDetector = new GestureDetectorCompat(context, this);
        gestureDetector.setOnDoubleTapListener(this);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        originOffsetX = (getWidth() - bitmap.getWidth()) / 2;
        originOffsetY = (getHeight() - bitmap.getHeight()) / 2;

        //如果图片宽高比 > 屏幕的宽高比，smallScale取
        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float)getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight();
        } else {
            smallScale = (float)bitmap.getHeight()/ getHeight();
            bigScale = (float)bitmap.getWidth() / getWidth();
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float scale =  big ?  bigScale : smallScale;
        canvas.scale(scale, scale, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint);
    }

    public ObjectAnimator getAnimator() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(this, "scaleFraction", 0, 1);
        }
        return animator;
    }

    public float getScaleFraction() {
        return scaleFraction;
    }

    public void setScaleFraction(float scaleFraction) {
        this.scaleFraction = scaleFraction;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
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
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    /**
     * 双击
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {

        big = !big;
        if (big) {
            getAnimator().start();
        } else {
            getAnimator().reverse();
        }

        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
