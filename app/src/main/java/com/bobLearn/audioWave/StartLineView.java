package com.bobLearn.audioWave;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class StartLineView extends RelativeLayout {
    private float downX;
    private int endX;
    private int realWidth;//画布宽度

    private OnMoveEvent mOnMoveEvent;

    public void setOnMoveEvent(OnMoveEvent onMoveEvent) {
        mOnMoveEvent = onMoveEvent;
    }

    public StartLineView(Context context) {
        this(context, null);
    }

    public StartLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StartLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float dX = event.getX() - downX;
                if (dX != 0) {
                    int l = (int) (getLeft() + dX);
                    int r = (int) (getRight() + dX);
                    if (r >= endX - WaveStaticView.SPACE) {
                        l = endX - WaveStaticView.SPACE;
                        r = l + getMeasuredWidth();
                    }
                    if (l <= 0) {
                        l = 0;
                        r = l + getMeasuredWidth();
                    }
                    this.layout(l, getTop(), r, getBottom());
                    if (mOnMoveEvent != null) {
                        mOnMoveEvent.onMove((int) getX(), getTimeScale());
                    }
                }
                break;
        }
        return true;
    }

    public void setEndX(int x) {
        this.endX = x;
    }

    public void setRealWidth(int realWidth) {
        this.realWidth = realWidth;
        endX = realWidth;
    }

    public double getTimeScale() {
        return getX() / realWidth;
    }
}

