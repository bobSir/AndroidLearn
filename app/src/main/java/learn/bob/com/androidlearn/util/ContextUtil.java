package learn.bob.com.androidlearn.util;

import android.content.Context;

public final class ContextUtil {
    private static Context context;

    public static void init(Context appContext) {
        context = appContext.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
