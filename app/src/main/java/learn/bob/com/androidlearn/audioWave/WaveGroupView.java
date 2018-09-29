package learn.bob.com.androidlearn.audioWave;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import learn.bob.com.androidlearn.R;

/**
 * Created by cly on 18/9/21.
 */

public class WaveGroupView extends RelativeLayout {
    private int mWavaSize;//波纹个数

    private final float MIN_TIME = 5;
    private LineView mStartLineView;
    private LineView mEndLineView;
    private LineView mPlayLineView;
    private WaveStaticView mWaveView;

    public WaveGroupView(Context context) {
        this(context, null);
    }

    public WaveGroupView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) this.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private void initView() {
        mWaveView = new WaveStaticView(getContext());
        mWaveView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        this.addView(mWaveView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initLine();
    }

    private void initLine() {
        mStartLineView = findViewById(R.id.line_start);
        mPlayLineView = findViewById(R.id.line_play);
        mEndLineView = findViewById(R.id.line_end);
        mStartLineView.setOnMoveEvent(new LineView.OnMoveEvent() {
            @Override
            public void onMove(int x) {
                mEndLineView.setStartX(x);
            }
        });
        mEndLineView.setOnMoveEvent(new LineView.OnMoveEvent() {
            @Override
            public void onMove(int x) {
                mStartLineView.setEndX(x);
            }
        });
    }

    public void showWave(ArrayList<Integer> integers) {
        mWavaSize = integers.size();
        mWaveView.setValues(integers);
    }

    public void playAudio() {
        mPlayLineView.setMinDis((int) (MIN_TIME / 10f * getScreenWidth()));
        mStartLineView.setMinDis((int) (MIN_TIME / 10f * getScreenWidth()));
        mEndLineView.setMinDis((int) (MIN_TIME / 10f * getScreenWidth()));
        mPlayLineView.play(10);
    }

    public void stopAudio() {
        mPlayLineView.stop();
    }
}
