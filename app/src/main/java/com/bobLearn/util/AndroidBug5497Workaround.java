package com.bobLearn.util;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class AndroidBug5497Workaround {

    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    public static void assistActivity(Activity activity) {
        //4.4以下不可以设置状态栏，设置fitsystemwindows没有效果 所以在4.4以下不用进行该处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            new AndroidBug5497Workaround(activity);
        }
    }

    public static void assistView(ViewGroup viewGroup) {
        new AndroidBug5497Workaround(viewGroup);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private ViewGroup.LayoutParams frameLayoutParams;

    private AndroidBug5497Workaround(Activity activity) {
        FrameLayout content = activity.findViewById(android.R.id.content);
        mChildOfContent = content;//content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = mChildOfContent.getLayoutParams();
    }

    private AndroidBug5497Workaround(ViewGroup viewGroup) {
        mChildOfContent = viewGroup;
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = mChildOfContent.getLayoutParams();
    }

    /*
    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard/4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference +
                        StatusBarUtil.getStatusBarHeight();
                LogUtil.d("height 1 : " +usableHeightSansKeyboard + " .. "  + heightDifference
                        + " .. " + StatusBarUtil.getStatusBarHeight() + " .. " +
                        frameLayoutParams.height + " ... " + SystemBarUtil.getNavigationBarHeightOrNo());

            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard
                        - SystemBarUtil.getNavigationBarHeightOrNo();
                LogUtil.d("height 2 : " +usableHeightSansKeyboard + " .. "  + heightDifference
                        + " .. " + StatusBarUtil.getStatusBarHeight() + " .. " +
                        frameLayoutParams.height + " ... " + SystemBarUtil.getNavigationBarHeightOrNo());
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }
    */

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            //如果两次高度不一致
            //将计算的可视高度设置成视图的高度
            frameLayoutParams.height = usableHeightNow;
            mChildOfContent.requestLayout();//请求重新布局
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return r.bottom;//(r.bottom - r.top);
    }

}


