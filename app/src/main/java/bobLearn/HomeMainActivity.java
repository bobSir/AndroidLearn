package bobLearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import bobLearn.aop.AOPDoubleClickActivity;
import bobLearn.audioWave.AudioWaveActivity;
import bobLearn.fragment.FragmentLearnActivity;
import bobLearn.viewpagerOptimization.FragmentVpActivity;
import learn.bob.com.androidlearn.R;

public class HomeMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void aopDoubleClick(View view) {
        startActivity(new Intent(this, AOPDoubleClickActivity.class));
    }

    public void audioWave(View view) {
        startActivity(new Intent(this, AudioWaveActivity.class));
    }

    public void fragmentTest(View view) {
        startActivity(new Intent(this, FragmentLearnActivity.class));
    }

    public void fragmentLazy(View view) {
        startActivity(new Intent(this, FragmentVpActivity.class));
    }
}
