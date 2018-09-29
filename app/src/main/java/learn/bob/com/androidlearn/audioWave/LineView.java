package learn.bob.com.androidlearn.audioWave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import learn.bob.com.androidlearn.R;

/**
 * Created by cly on 18/9/28.
 */

public class LineView extends View {
    public static final int START = 1;
    public static final int PLAY = 2;
    public static final int END = 3;

    private Paint mLinePaint;
    private Paint mCirclePaint;

    private int color;
    private int lineWidth = 10;
    private int type;
    private ValueAnimator mPlayAnim;

    private float downX;
    public int minDis;//起始线距末尾线最短距离 根据录制最短时间5S计算
    private static final float RADIUS = 6;
    public static final int LINE_WIDTH = 10;
    private int startX = 0;//裁剪起始线X
    private int endX = getScreenWidth();//裁剪末尾线X
    public OnMoveEvent mOnMoveEvent;

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) this.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributeSet(context, attrs);
        initPaint();
    }

    public void setOnMoveEvent(OnMoveEvent onMoveEvent) {
        mOnMoveEvent = onMoveEvent;
    }

    private void obtainAttributeSet(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineView);
        type = typedArray.getInt(R.styleable.LineView_line_type, 1);
        typedArray.recycle();
    }

    private void initPaint() {
        switch (type) {
            case START:
            case END:
                mLinePaint = new Paint();
                mLinePaint.setColor(Color.parseColor("#3CB371"));
                mLinePaint.setStrokeWidth(lineWidth);
                mLinePaint.setAntiAlias(true);
                mLinePaint.setDither(true);
                mLinePaint.setFilterBitmap(true);
                mLinePaint.setStyle(Paint.Style.STROKE);

                mCirclePaint = new Paint();
                mCirclePaint.setColor(Color.parseColor("#3CB371"));
                break;
            case PLAY:
                mLinePaint = new Paint();
                mLinePaint.setColor(Color.parseColor("#1E90FF"));
                mLinePaint.setStrokeWidth(lineWidth);
                mLinePaint.setAntiAlias(true);
                mLinePaint.setDither(true);
                mLinePaint.setFilterBitmap(true);
                mLinePaint.setStyle(Paint.Style.STROKE);
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mLinePaint == null) return;
        switch (type) {
            case START:
                canvas.drawCircle(0, 0, RADIUS, mCirclePaint);
                canvas.drawLine(0, 0, 0, getMeasuredHeight(), mLinePaint);
                break;
            case END:
                canvas.drawLine(0, 0, 0, getMeasuredHeight(), mLinePaint);
                canvas.drawCircle(0, 0, RADIUS, mCirclePaint);
                break;
            case PLAY:
                canvas.drawLine(0, 0, 0, getMeasuredHeight(), mLinePaint);
                break;
        }
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
                    if (type == START) {
                        if (r + minDis > endX) {
                            l = endX - LINE_WIDTH - minDis - LINE_WIDTH;
                            r = l + LINE_WIDTH;
                        }
                    } else if (type == END) {
                        if (l - minDis < startX) {
                            l = startX + minDis;
                            r = l + LINE_WIDTH;
                        }
                    }
                    this.layout(l, getTop(), r, getBottom());
                    if (mOnMoveEvent != null) {
                        mOnMoveEvent.onMove((int) getX());
                    }
                }
                break;
        }
        return true;
    }

    public void setMinDis(int minDis) {
        this.minDis = minDis;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    /**
     * 获取当前时间与总时长的比例
     *
     * @return
     */
    public double getScale() {
        return getX() / getScreenWidth();
    }

    /**
     * 控制播放音频线
     *
     * @param time 音频时长 单位秒
     */
    public void play(int time) {
        if (type != PLAY) return;
        mPlayAnim = ValueAnimator.ofInt((int) this.getX(), getScreenWidth());
        mPlayAnim.setDuration(time * 1000);
        mPlayAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                LineView.this.layout(value, 0, value + LINE_WIDTH, getMeasuredHeight());
            }
        });
        mPlayAnim.start();
    }

    public void stop() {
        if (type != PLAY) return;
        mPlayAnim.cancel();
        this.clearAnimation();
    }

    public interface OnMoveEvent {
        void onMove(int x);
    }
}
