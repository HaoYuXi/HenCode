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
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

public class ScalableImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {
    private static final float IMAGE_WIDTH = Utils.dpToPixel(300);

    Bitmap bitmap;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    float originOffsetX;
    float originOffsetY;
    float offsetX;
    float offsetY;

    float smallScale, bigScale;
    boolean big;
    float scaleFraction;
    ObjectAnimator scaleAnimator;
    float OVER_SCALE_FRACTION = 1.5F;


    GestureDetectorCompat gestureDetector;
    OverScroller overScroller;

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
        gestureDetector = new GestureDetectorCompat(context, this);
//        gestureDetector.setOnDoubleTapListener(this);
        overScroller = new OverScroller(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originOffsetX = ((float) getWidth() - bitmap.getWidth()) / 2;
        originOffsetY = ((float) getHeight() - bitmap.getHeight()) / 2;

        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FRACTION;
        } else {
            bigScale = (float) getWidth() / bitmap.getWidth();
            smallScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FRACTION;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        float scale = big ? bigScale : smallScale;
        canvas.translate(offsetX,offsetY);
        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(scale, scale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint);
    }

    public ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "scaleFraction", 0, 1);
        }
        return scaleAnimator;
    }

    public float getScaleFraction() {
        return scaleFraction;
    }

    public void setScaleFraction(float scaleFraction) {
        this.scaleFraction = scaleFraction;
        invalidate();
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
//        if (big) {
//            offsetX -= distanceX;
//            offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) /2);
//            offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) /2);
//            offsetY -= distanceY;
//            offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) /2);
//            offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) /2);
//            invalidate();
//        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (big) {
            overScroller.fling((int)offsetX,(int)offsetY,(int)velocityX,(int)velocityY,
                    -(int)(bitmap.getWidth() * bigScale - getWidth())/2,
                    (int)(bitmap.getWidth() * bigScale - getWidth()) / 2,
                    -(int)(bitmap.getHeight() * bigScale - getWidth())/2,
                    (int)(bitmap.getHeight()* bigScale - getWidth()) / 2);

            postOnAnimation(this);
        }
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        big = !big;
        if (big) {
            getScaleAnimator().start();
        } else {
            getScaleAnimator().reverse();
        }
//        invalidate();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void run() {

        if (overScroller.computeScrollOffset()) {
            offsetX = overScroller.getCurrX();
            offsetY = overScroller.getCurrY ();
            invalidate();
            postOnAnimation(this);
        }
    }
}
