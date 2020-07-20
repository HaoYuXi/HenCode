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

public class TestView extends View
{
    Bitmap bitmap;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    float IMAGE_WEIGHT = Utils.dpToPixel(200);

    float originOffsetX, originOffsetY;
    float offsetX, offsetY;
    float bigScale, smallScale;
    boolean big;
    float scaleFraction;
    ObjectAnimator animator;


    GestureDetector gestureDetector;
    GestureDetector.OnGestureListener gestureListene = new HenGestureListener();
    HenDoubleGesture henDoubleGesture = new HenDoubleGesture();


    public TestView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WEIGHT);

        gestureDetector = new GestureDetector(context, gestureListene);
        gestureDetector.setOnDoubleTapListener(henDoubleGesture);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        originOffsetX = (getWidth() - bitmap.getWidth()) / 2;
        originOffsetY = (getHeight() - bitmap.getHeight()) / 2;

        if ((float) (bitmap.getWidth() / bitmap.getHeight()) > (float) (getWidth() / getHeight())) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight();
        } else {
            bigScale = (float) getWidth() / bitmap.getWidth();
            smallScale = (float) getHeight() / bitmap.getHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.translate(offsetX, offsetY);
        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(scale, scale, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint);

    }

    public ObjectAnimator getAnimator()
    {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(this, "scaleFraction", 0, 1);
        }
        return animator;
    }

    public float getScaleFraction()
    {
        return scaleFraction;
    }

    public void setScaleFraction(float scaleFraction)
    {
        this.scaleFraction = scaleFraction;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return gestureDetector.onTouchEvent(event);
    }

    class HenGestureListener implements GestureDetector.OnGestureListener
    {

        @Override
        public boolean onDown(MotionEvent e)
        {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e)
        {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            if (big) {
                offsetX -= distanceX;
                offsetX = Math.min(offsetX, (float) (bitmap.getWidth() * bigScale - getWidth()) / 2);
                offsetX = Math.max(offsetX, -(float) (bitmap.getWidth() * bigScale - getWidth()) / 2);

                offsetY -= distanceY;
                offsetY = Math.min(offsetY, (float) (bitmap.getHeight() * bigScale - getHeight()) / 2);
                offsetY = Math.max(offsetY, -(float) (bitmap.getHeight() * bigScale - getHeight()) / 2);

                invalidate();
            }


            return false;
        }

        @Override
        public void onLongPress(MotionEvent e)
        {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            return false;
        }
    }

    class HenDoubleGesture implements GestureDetector.OnDoubleTapListener
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
                getAnimator().start();
            } else {
                getAnimator().reverse();
            }
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e)
        {
            return false;
        }
    }
}
