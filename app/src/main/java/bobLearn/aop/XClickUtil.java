package bobLearn.aop;

import android.util.Log;
import android.view.View;

/**
 * Created by cly on 18/9/17.
 */

public class XClickUtil {
    private static long mLastClickTime;
    private static int mLastClickViewId;

    public static boolean isFastDoubleClick(View v, long intervalMillis) {
        Log.d("@cly", "1111");
        int viewId = v.getId();
        long time = System.currentTimeMillis();
        long timeInterval = Math.abs(time - mLastClickTime);
        if (timeInterval < intervalMillis && viewId == mLastClickViewId) {
            return true;
        } else {
            mLastClickTime = time;
            mLastClickViewId = viewId;
            return false;
        }
    }
}
