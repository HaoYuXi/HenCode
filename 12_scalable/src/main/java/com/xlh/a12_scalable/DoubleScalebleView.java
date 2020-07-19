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

public class DoubleScalebleView extends View
{
    private Paint print = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    float IMAGE_WIDGHT = Utils.dpToPixel(200);
    float offsetX, offsetY;

    float bigScale;
    float smallScale;

    private float scaleFraction;
    ObjectAnimator animator;
    float OVER_SCALE_FRACTION = 1.5F;



    boolean big;
    GestureDetector gestureDetector;
    HenGestureDetector henGestureDetector = new HenGestureDetector();
    HenDoubleGesture doubleGesture = new HenDoubleGesture();

    public DoubleScalebleView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDGHT);
        gestureDetector = new GestureDetector(context, henGestureDetector);
        gestureDetector.setOnDoubleTapListener(doubleGesture);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(scale, scale, getWidth()/2-200, getHeight()/2);
        canvas.drawBitmap(bitmap, offsetX, offsetY, print);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        offsetX = (getWidth() - bitmap.getWidth()) / 2;
        offsetY = (getHeight() - bitmap.getHeight()) / 2;

        //如果图片的宽高比 > View的宽高比，证明图片比较宽
       if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth()/ getHeight()){
           smallScale = (float) getWidth() / bitmap.getWidth();
           bigScale = (float) getHeight() / bitmap.getHeight();
       }else{
           bigScale = (float) getWidth() / bitmap.getWidth();
           smallScale = (float) getHeight() / bitmap.getHeight();
       }
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

    class HenGestureDetector implements GestureDetector.OnGestureListener
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
