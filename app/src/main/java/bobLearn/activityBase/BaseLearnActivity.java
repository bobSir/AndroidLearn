package bobLearn.activityBase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import learn.bob.com.androidlearn.R;

public class BaseLearnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //activity正在创建，常做初始化工作
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
    }

    @Override
    protected void onStart() {
        //activity正在启动，可见但不在前台，无法和用户交互
        super.onStart();
    }

    @Override
    protected void onResume() {
        //activity 获得焦点 可见在前台并开始活动
        super.onResume();
    }

    @Override
    protected void onPause() {
        //Activity正在停止，可做数据存储 停止动画等操作
        super.onPause();
    }

    @Override
    protected void onStop() {
        //Activity即将停止 可做稍微重量级回收工作，取消网络连接 注销广播接收器等
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //即将销毁 常做回收工作 资源释放
        super.onDestroy();
    }

    //Activity由不可见到可见会调用onResume()

//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        //非人为结束Activity时调用，在onStop之前，适用于临时性状态的保存。
//        super.onSaveInstanceState(outState, outPersistentState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        //activity因内存不足被回收后，从Bundle中取出数据恢复状态.
//        super.onRestoreInstanceState(savedInstanceState);
//    }
}
