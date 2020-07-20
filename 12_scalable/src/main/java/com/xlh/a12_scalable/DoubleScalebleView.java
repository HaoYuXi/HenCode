package com.xlh.a12_scalable;

import android.animation.ObjectAnimator;
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
import android.widget.Scroller;

import androidx.annotation.Nullable;

public class DoubleScalebleView extends View
{
    private Paint print = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    float IMAGE_WIDGHT = Utils.dpToPixel(200);
    float originOffsetX, originOffsetY;
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
    //overScroller scroller 两者的区别，scroller初始速度不明显，拖动速度会很慢。有两个额外参数，overX，overY，超出边界后回弹
    OverScroller overScroller;
    Scroller scroller;


    public DoubleScalebleView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDGHT);
        gestureDetector = new GestureDetector(context, henGestureDetector);
        gestureDetector.setOnDoubleTapListener(doubleGesture);
        overScroller = new OverScroller(context);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.translate(offsetX, offsetY);
        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(scale, scale, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, print);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        originOffsetX = (getWidth() - bitmap.getWidth()) / 2;
        originOffsetY = (getHeight() - bitmap.getHeight()) / 2;

        //如果图片的宽高比 > View的宽高比，证明图片比较宽
        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FRACTION;
        } else {
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FRACTION;
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
            if (big) {
                offsetX -= distanceX;
                offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
                offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
                offsetY -= distanceY;
                offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
                offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
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
            //以offsetX ，Y 为圆心。X,Y轴最大最小值，分别是，(图片放大后宽高 - view宽高) / 2
            //overScroller 只是一个计算工具，如果要更新view需要重新设置动画刷新
            overScroller.fling((int)offsetX, (int)offsetY, (int)velocityX, (int)velocityY,
                    -(int) (bitmap.getWidth() * bigScale- getWidth()) / 2,
                    (int) (bitmap.getWidth() * bigScale- getWidth()) / 2,
                    -(int) (bitmap.getHeight() * bigScale- getHeight()) / 2,
                    (int) (bitmap.getHeight()* bigScale - getHeight()) / 2);

            //每帧更新一次
            //下一帧前执行
            postOnAnimation(new HenRun());

            return true;
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

    class HenRun implements Runnable
    {

        @Override
        public void run()
        {
            Log.i("TAG","1232134run");
            //需要执行到动画结束。所以在此处判断然后继续调用run
            //这个动画是否还在进行中
            if (overScroller.computeScrollOffset()) {
                offsetX = overScroller.getCurrX();
                offsetY = overScroller.getCurrY();
                invalidate();
                postOnAnimation(this);

            }

        }
    }


}
