package com.dain.a12;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

public class ScalableImageView extends View
{
    private static final float IMAGE_WIDTH = Utils.dpToPixel(300);
    private static final float OVER_SCALE_FRACTION = 1.5F;

    Bitmap bitmap;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    float originOffsetX;
    float originOffsetY;
    float offsetX;
    float offsetY;

    float smallScale, bigScale;
    boolean big;
//    float scaleFraction;
    ObjectAnimator scaleAnimator;


    GestureDetectorCompat gestureDetector;
    GestureDetector.OnGestureListener gestureListener = new HenGestureListener();
    GestureDetector.OnDoubleTapListener doubleTapListener = new HenDoubleTapListener();
    OverScroller overScroller;
    FlingRunner flingRunner = new FlingRunner();

    ScaleGestureDetector scaleGestureDetector;
    ScaleGestureDetector.OnScaleGestureListener scaleGestureListener = new HenScaleGestureListener();
    private float currentScale;
    private float oldCurrentScale;

    public ScalableImageView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
        gestureDetector = new GestureDetectorCompat(context, gestureListener);
        gestureDetector.setOnDoubleTapListener(doubleTapListener);
        scaleGestureDetector = new ScaleGestureDetector(context,scaleGestureListener);
        overScroller = new OverScroller(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        originOffsetX = ((float) getWidth() - bitmap.getWidth()) / 2;
        originOffsetY = ((float) getHeight() - bitmap.getHeight()) / 2;

        Log.i("TAG", "getWidth = " + getWidth() + "  getHeight = " + getHeight());
        Log.i("TAG", "bitmapWidth = " + bitmap.getWidth() + "  bitmapHeight = " + bitmap.getHeight());
        Log.i("TAG", "originOffsetX = " + originOffsetX + "  originOffsetY = " + originOffsetY);

        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FRACTION;
        } else {
            bigScale = (float) getWidth() / bitmap.getWidth();
            smallScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FRACTION;
        }
        currentScale = smallScale;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return scaleGestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //(currentScale - smallScale) 当前缩放值 - 初始缩放值 = 目前缩放值
        //bigScale - smallScale 最大缩放值
        //
        float scaleFraction = (currentScale - smallScale) / (bigScale - smallScale);
        canvas.translate(offsetX * scaleFraction, offsetY* scaleFraction);
//        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint);
    }

    public ObjectAnimator getScaleAnimator()
    {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale",
                    smallScale, bigScale);
            scaleAnimator.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation, boolean isReverse)
                {
                    if (isReverse) {
                        offsetX = offsetY = 0;
                    }
                }
            });
        }
        return scaleAnimator;
    }

    public float getCurrentScale()
    {
        return currentScale;
    }

    public void setCurrentScale(float currentScale)
    {
        this.currentScale = currentScale;
        invalidate();
    }


    class HenGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e)
        {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            if (big) {
                offsetX -= distanceX;
                offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
                offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
                offsetY -= distanceY;
                offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
                offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
                Log.e("onScroll_TAG", "offsetX = " + offsetX + "       offsetY = " + offsetY);

                invalidate();
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            if (big) {
                overScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        -(int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        -(int) (bitmap.getHeight() * bigScale - getWidth()) / 2,
                        (int) (bitmap.getHeight() * bigScale - getWidth()) / 2);

                Log.i("TAG", "offsetX = " + offsetX + "offsetY = " + offsetY);
                Log.i("TAG",
                        "minX = " + (-(int) (bitmap.getWidth() * bigScale - getWidth()) / 2) +
                                "  maxX = " + ((int) (bitmap.getWidth() * bigScale - getWidth()) / 2) +
                                "  minY = " + (-(int) (bitmap.getHeight() * bigScale - getWidth()) / 2) +
                                "  maxY = " + ((int) (bitmap.getHeight() * bigScale - getWidth()) / 2));

                postOnAnimation(flingRunner);
            }
            return false;
        }
    }

    class HenDoubleTapListener implements GestureDetector.OnDoubleTapListener
    {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e)
        {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e)
        {
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
        public boolean onDoubleTapEvent(MotionEvent e)
        {
            return false;
        }
    }

    class FlingRunner implements Runnable{

        @Override
        public void run()
        {
            if (overScroller.computeScrollOffset()) {
                offsetX = overScroller.getCurrX();
                offsetY = overScroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

    private class HenScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector)
        {
            //detector.getScaleFactor() 获得到的是一个倍数
            currentScale = oldCurrentScale * detector.getScaleFactor();
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector)
        {
            oldCurrentScale = currentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector)
        {

        }
    }
}
