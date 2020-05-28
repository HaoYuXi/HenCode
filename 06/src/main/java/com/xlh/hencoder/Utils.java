package com.xlh.hencoder;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Date: 20-5-25
 * Time: 上午11:32
 * Author: dain
 */
public class Utils {
    public static float dpToPixel(float dp) {
        return  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
}
