package com.tim.yixin.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Ryan on 13/1/2017.
 */

public class CommonUtil {

    public static int containerWidth(Activity ba) {
        DisplayMetrics dm = new DisplayMetrics();
        ba.getWindowManager().getDefaultDisplay().getMetrics(dm);

        //get predefined value
        double ratio = 2.2;

        return (int) (dm.widthPixels / ratio);
    }

    public static int containerHeight(Activity ba, double ratio) {
        DisplayMetrics dm = new DisplayMetrics();
        ba.getWindowManager().getDefaultDisplay().getMetrics(dm);

        //get predefined value
        return (int) (dm.heightPixels / ratio);
    }
}
