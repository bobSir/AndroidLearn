package com.bobLearn.audioWave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

public class PlayLineView extends RelativeLayout {
    private float downX;
    private int realWidth;//画布宽度
    private ValueAnimator mPlayAnim;

    private OnPlayLineTouchListener mPlayLineTouchListener;

    public void setPlayLineTouchListener(OnPlayLineTouchListener playLineTouchListener) {
        mPlayLineTouchListener = playLineTouchListener;
    }

    public PlayLineView(Context context) {
        this(context, null);
    }

    public PlayLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_CANCEL) return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                if (mPlayLineTouchListener != null) mPlayLineTouchListener.onDown();
                break;
            case MotionEvent.ACTION_MOVE:
                float dX = event.getX() - downX;
                if (dX != 0) {
                    int l = (int) (getLeft() + dX);
                    int r = (int) (getRight() + dX);
                    if (r >= realWidth + WaveStaticView.SPACE * 2) {
                        r = realWidth + WaveStaticView.SPACE * 2;
                        l = r - getMeasuredWidth();
                    }
                    if (l <= 0) {
                        l = 0;
                        r = l + getMeasuredWidth();
                    }

                    this.layout(l, getTop(), r, getBottom());
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mPlayLineTouchListener != null) {
                    mPlayLineTouchListener.onUp(getTimeScale());
                }
                break;
        }
        return true;
    }

    /**
     * 控制播放音频线
     *
     * @param time 音频时长 单位秒
     */
    public void play(long time) {
        mPlayAnim.setIntValues((int) this.getX(), realWidth);
        mPlayAnim.setDuration(time);
        mPlayAnim.setInterpolator(new LinearInterpolator());
        mPlayAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                PlayLineView.this.layout(value, 0,
                        value + PlayLineView.this.getMeasuredWidth(), getMeasuredHeight());
            }
        });
        mPlayAnim.start();
    }

    public void stop() {
        if (mPlayAnim == null) return;
        mPlayAnim.cancel();
        this.clearAnimation();
    }

    public void playFinish() {
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    public void setRealWidth(int realWidth) {
        this.realWidth = realWidth;
        mPlayAnim = new ValueAnimator();
    }

    public double getTimeScale() {
        return getX() / realWidth;
    }

    public interface OnPlayLineTouchListener {
        void onDown();

        void onUp(double timeScale);
    }
}
