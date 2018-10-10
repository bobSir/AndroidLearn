package learn.bob.com.androidlearn.audioWave;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Pair;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import learn.bob.com.androidlearn.R;
import learn.bob.com.androidlearn.util.DensityUtil;

/**
 * Created by cly on 18/9/21.
 */

public class WaveGroupView extends RelativeLayout {
    private final float MIN_TIME = 5;
    private StartLineView mStartLineView;
    private EndLineView mEndLineView;
    private PlayLineView mPlayLineView;
    private WaveStaticView mWaveView;
    private WaveBacView mWaveBacView;
    private OnSeekListener mOnSeekListener;
    private onCropSeekListener mCropSeekListener;

    public WaveGroupView(Context context) {
        this(context, null);
    }

    public WaveGroupView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnSeekListener(OnSeekListener onSeekListener) {
        mOnSeekListener = onSeekListener;
    }

    public void setCropSeekListener(onCropSeekListener cropSeekListener) {
        mCropSeekListener = cropSeekListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initLine();
    }

    private void initLine() {
        mWaveView = findViewById(R.id.wv);
        mStartLineView = findViewById(R.id.line_start);
        mPlayLineView = findViewById(R.id.line_play);
        mEndLineView = findViewById(R.id.line_end);
        mWaveBacView = findViewById(R.id.wv_bac);
        mPlayLineView.setPlayLineTouchListener(new PlayLineView.OnPlayLineTouchListener() {
            @Override
            public void onDown() {
                if (mOnSeekListener != null) mOnSeekListener.onDown();
            }

            @Override
            public void onUp(double timeScale) {
                if (mOnSeekListener != null) mOnSeekListener.onUp(timeScale);
            }
        });
        mStartLineView.setOnMoveEvent(new OnMoveEvent() {
            @Override
            public void onMove(int x, double scale) {
                mEndLineView.setStartX(x);
                mWaveBacView.drawStart(x);
                if (mCropSeekListener != null) mCropSeekListener.onStartMove(scale);
            }
        });
        mEndLineView.setOnMoveEvent(new OnMoveEvent() {
            @Override
            public void onMove(int x, double scale) {
                mStartLineView.setEndX(x);
                mWaveBacView.drawEnd(x);
                if (mCropSeekListener != null) mCropSeekListener.onEndMove(scale);
            }
        });
    }

    public void showWave(ArrayList<Integer> integers) {
        mWaveView.setValues(integers);
        mPlayLineView.setRealWidth(mWaveView.getRealWidth());
        mStartLineView.setRealWidth(mWaveView.getRealWidth());
        mEndLineView.setRealWidth(mWaveView.getRealWidth());
        LayoutParams layoutParams = (LayoutParams) mEndLineView.getLayoutParams();
        int right = DensityUtil.getScreenWith(getContext()) - mWaveView.getRealWidth() - 2 * WaveStaticView.SPACE;
        layoutParams.setMargins(0, 0, right, 0);
        mEndLineView.setLayoutParams(layoutParams);
        mWaveBacView.setEndRight(mWaveView.getRealWidth() + WaveStaticView.SPACE);
    }

    public void hideCrop() {
        mStartLineView.setVisibility(GONE);
        mEndLineView.setVisibility(GONE);
    }

    /**
     * @param time 单位ms
     */
    public void playAudio(long time) {
        mPlayLineView.play(time);
    }

    public void stopAudio() {
        mPlayLineView.stop();
    }

    public void playFinish() {
        mPlayLineView.playFinish();
    }

    public Pair<Long, Long> getBeginAndEnd(long totalTime) {
        long beginTime = (long) (mStartLineView.getTimeScale() * totalTime);
        long endTime = (long) (mEndLineView.getTimeScale() * totalTime);
        return new Pair<>(beginTime, endTime);
    }

    public interface OnSeekListener {
        void onDown();

        void onUp(double timeScale);
    }

    public interface onCropSeekListener {
        void onStartMove(double scale);

        void onEndMove(double scale);
    }
}
