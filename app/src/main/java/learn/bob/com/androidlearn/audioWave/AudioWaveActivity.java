package learn.bob.com.androidlearn.audioWave;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import learn.bob.com.androidlearn.R;

public class AudioWaveActivity extends AppCompatActivity {

    private WaveStaticView mStaticView;
    private WaveView mWaveView;
    private WaveGroupView mWaveGroupView;

    private Handler mHandler = new Handler();
    private int j = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_wave_avtivity);
        mStaticView = findViewById(R.id.wavesfv);
        mWaveView = findViewById(R.id.wv);
        mWaveGroupView = findViewById(R.id.wave_group);
    }

    public void start(View view) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 11230; i++) {
            integers.add(new Random().nextInt(j));
        }
        mStaticView.setValues(integers);
    }

    public void startView(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mWaveView.startWave(new Random().nextInt(j));
                mHandler.postDelayed(this, 100);
            }
        };
        mHandler.postDelayed(runnable, 100);
    }

    public void testView(View view) {
//        mHandler.removeCallbacksAndMessages(null);
//        mWaveView.stopWave();
        mWaveGroupView.stopAudio();
    }

    public void testViewGroup(View view) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 11230; i++) {
            integers.add(new Random().nextInt(j));
        }
        mWaveGroupView.showWave(integers);
        mWaveGroupView.playAudio();
    }
}
