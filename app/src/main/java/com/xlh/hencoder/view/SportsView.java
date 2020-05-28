package com.xlh.hencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.PathInterpolator;

import androidx.annotation.Nullable;

import com.xlh.hencoder.Utils;

/**
 * Date: 20-5-26
 * Time: 上午9:56
 * Author: dain
 */
public class SportsView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public static final float RING_WIDTH = Utils.dpToPixel(20);
    public static final float RADIUS = Utils.dpToPixel(150);
    public static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    public static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");

    Rect rect = new Rect();
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();


    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {

        paint.setTextSize(Utils.dpToPixel(100));
//        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Quicksand-Regular.ttf"));
        paint.setTextAlign(Paint.Align.CENTER);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制圆
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(CIRCLE_COLOR);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, paint);

        //绘制进度条
        paint.setColor(HIGHLIGHT_COLOR);
        paint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawArc(getWidth() / 2 - RADIUS,
                getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS,
                getHeight() / 2 + RADIUS,
                0,
                255,
                false, paint);

        String text = "ababg";
        paint.setStyle(Paint.Style.FILL);
//        canvas.drawText("abab",getWidth()/2,getHeight()/2,paint);
        //通过此方法获取文字信息，文字位置信息放入到Rect中，基于BaseLine
//        paint.getTextBounds(text,0,"abab".length() ,rect);
//        int offset = (rect.top + rect.bottom ) / 2;
//        //在Y坐标上绘制偏移量
//        canvas.drawText(text,getWidth()/2,getHeight()/2 - offset,paint);


        //通过此方法获取文字信息，文字位置信息放入到fontMetrics中，基于ascent & descent
        paint.getFontMetrics(fontMetrics);

        float offset = (fontMetrics.ascent + fontMetrics.descent) / 2;
        //在Y坐标上绘制偏移量
        canvas.drawText(text, getWidth() / 2, getHeight() / 2 - offset, paint);


        paint.setTextSize(Utils.dpToPixel(100));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, 0, 200+paint.getFontSpacing(), paint);

        paint.setTextSize(Utils.dpToPixel(15));
        canvas.drawText(text, 0, 200 + paint.getFontSpacing(), paint);


    }
}
