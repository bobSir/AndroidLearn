package bobLearn.audioWave;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class EndLineView extends RelativeLayout {
    private float downX;
    private int startX;
    private int realWidth;//画布宽度

    private OnMoveEvent mOnMoveEvent;

    public void setOnMoveEvent(OnMoveEvent onMoveEvent) {
        mOnMoveEvent = onMoveEvent;
    }

    public EndLineView(Context context) {
        super(context);
    }

    public EndLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EndLineView(Context context, AttributeSet attrs, int defStyleAttr) {
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
                    if (l <= startX + WaveStaticView.SPACE) {
                        l = startX + WaveStaticView.SPACE;
                        r = l + getMeasuredWidth();
                    }
                    if (r >= realWidth + WaveStaticView.SPACE * 2) {
                        r = realWidth + WaveStaticView.SPACE * 2;
                        l = r - getMeasuredWidth();
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

    public void setStartX(int x) {
        this.startX = x;
    }

    public void setRealWidth(int realWidth) {
        this.realWidth = realWidth;
    }

    public double getTimeScale() {
        return getX() / realWidth;
    }
}
