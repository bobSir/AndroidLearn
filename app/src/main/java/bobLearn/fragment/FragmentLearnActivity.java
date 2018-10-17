package bobLearn.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import learn.bob.com.androidlearn.R;

/**
 * 单个Activity 多个fragment 测试
 * fragment 实现onBackPress
 *
 * @author cly
 */
public class FragmentLearnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_learn);
        AFragment aFragment = new AFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, aFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this, R.id.container)) {
            super.onBackPressed();
        }
    }
}
