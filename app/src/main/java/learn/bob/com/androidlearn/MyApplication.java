package learn.bob.com.androidlearn;

import android.app.Application;

import learn.bob.com.androidlearn.util.ContextUtil;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtil.init(this);
    }
}
