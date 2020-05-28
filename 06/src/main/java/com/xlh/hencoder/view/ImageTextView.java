package com.xlh.hencoder.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.xlh.hencoder.R;
import com.xlh.hencoder.Utils;

/**
 * Date: 20-5-26
 * Time: 上午9:56
 * Author: dain
 */
public class ImageTextView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public static final float RING_WIDTH = Utils.dpToPixel(20);
    public static final float RADIUS = Utils.dpToPixel(150);
    public static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    public static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");

    TextPaint textPaint = new TextPaint();
    Bitmap bitmap;
    float[] cutWidth = new float[1];


    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        textPaint.setTextSize(Utils.dpToPixel(20));
        bitmap = getAvatar((int) Utils.dpToPixel(100));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        StaticLayout staticLayout = new StaticLayout("LFU实现详解 缓存的大小都是有限的，当缓存满时有新元素需要添加，就需要一种方式从缓存中删除一些元素，删除的策略就是缓存的淘汰算法。 LFU有个兄弟LRU，他们两都是常用的缓存淘汰算法。 LRU(Least Recently Used) 最近最少使用算法，它是根据时间维度来选择将要淘汰的元素，即删除掉最长时间没被访问的,LFU实现详解 缓存的大小都是有限的，当缓存满时有新元素需要添加，就需要一种方式从缓存中删除一些元素，删除的策略就是缓存的淘汰算法。 LFU有个兄弟LRU，他们两都是常用的缓存淘汰算法。 LRU(Least Recently Used) 最近最少使用算法，它是根据时间维度来选择将要淘汰的元素，即删除掉最长时间没被访问的.LFU实现详解 缓存的大小都是有限的，当缓存满时有新元素需要添加，就需要一种方式从缓存中删除一些元素，删除的策略就是缓存的淘汰算法。 LFU有个兄弟LRU，他们两都是常用的缓存淘汰算法。 LRU(Least Recently Used) 最近最少使用算法，它是根据时间维度来选择将要淘汰的元素，即删除掉最长时间没被访问的",
//                textPaint,getWidth(), Layout.Alignment.ALIGN_NORMAL,1,0,false);
//        staticLayout.draw(canvas);

        String text = "LFU实现详解 缓存的大小都是有限的，当缓存满时有新元素需要添加，就需要一种方式从缓存中删除一些元素，删除的策略就是缓存的淘汰算法。 LFU有个兄弟LRU，他们两都是常用的缓存淘汰算法。 LRU(Least Recently Used) 最近最少使用算法，它是根据时间维度来选择将要淘汰的元素，即删除掉最长时间没被访问的,LFU实现详解 缓存的大小都是有限的，当缓存满时有新元素需要添加，就需要一种方式从缓存中删除一些元素，删除的策略就是缓存的淘汰算法。 LFU有个兄弟LRU，他们两都是常用的缓存淘汰算法。 LRU(Least Recently Used) 最近最少使用算法，它是根据时间维度来选择将要淘汰的元素，即删除掉最长时间没被访问的.LFU实现详解 缓存的大小都是有限的，当缓存满时有新元素需要添加，就需要一种方式从缓存中删除一些元素，删除的策略就是缓存的淘汰算法。 LFU有个兄弟LRU，他们两都是常用的缓存淘汰算法。 LRU(Least Recently Used) 最近最少使用算法，它是根据时间维度来选择将要淘汰的元素，即删除掉最长时间没被访问的";


        canvas.drawBitmap(bitmap, getWidth() - (int) Utils.dpToPixel(100), 100, paint);
        int index = textPaint.breakText(text, true, getWidth(), cutWidth);
        canvas.drawText(text, 0, index, 0, 50, textPaint);


        int oldIndex = index;
        index = textPaint.breakText(text, true, getWidth(), cutWidth);
        canvas.drawText(text, oldIndex, index + oldIndex, 0, 50 + textPaint.getFontSpacing(), textPaint);
    }

    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.icon_img, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.icon_img, options);

    }

}
