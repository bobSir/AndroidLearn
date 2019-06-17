package com.bobLearn.backFragment;

import com.bobLearn.util.ContextUtil;

import java.lang.reflect.Field;

/**
 * created by cly on 2019-04-30
 */
public class StatusBarUtil {

    public static int getStatusBarHeight() {
        Class<?> c;
        Object obj;
        Field field;
        int x;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = ContextUtil.getContext().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
