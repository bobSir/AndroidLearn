package learn.bob.com.androidlearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import learn.bob.com.androidlearn.aop.AOPDoubleClickActivity;
import learn.bob.com.androidlearn.audioWave.AudioWaveActivity;
import learn.bob.com.androidlearn.fragment.FragmentLearnActivity;

public class MainActivity extends AppCompatActivity {

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
}
