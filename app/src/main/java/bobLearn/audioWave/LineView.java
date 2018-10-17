package bobLearn.audioWave;

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
import android.view.animation.LinearInterpolator;

import bobLearn.util.DensityUtil;
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
    private Paint mRectPaint;

    private final static int SPACE_DOWN = DensityUtil.dip2px(5);//上下留边5dp
    private final static int SPACE_LEFT = DensityUtil.dip2px(10);//左右留边
    private int lineWidth = DensityUtil.dip2px(3);
    private int type;
    private ValueAnimator mPlayAnim;

    private int realWidth;//画布实际绘画宽度
    private float downX;
    public int minDis;//起始线距末尾线最短距离 根据录制最短时间5S计算
    private static final float RADIUS = DensityUtil.dip2px(2.5f);
    private int startX = 0;//裁剪起始线X
    private int endX;//裁剪末尾线X
    public OnMoveEvent mOnMoveEvent;
    public OnPlayLineTouchListener mPlayLineTouchListener;

    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        realWidth = DensityUtil.getScreenWith(context);
        obtainAttributeSet(context, attrs);
        initPaint();
    }

    public void setOnMoveEvent(OnMoveEvent onMoveEvent) {
        mOnMoveEvent = onMoveEvent;
    }

    public void setPlayLineTouchListener(OnPlayLineTouchListener playLineTouchListener) {
        mPlayLineTouchListener = playLineTouchListener;
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

                mRectPaint = new Paint();
                mRectPaint.setColor(Color.BLACK);
                mRectPaint.setAntiAlias(true);
                mRectPaint.setDither(true);
                mRectPaint.setFilterBitmap(true);
                mRectPaint.setStyle(Paint.Style.STROKE);
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mLinePaint == null) return;
        switch (type) {
            case START:
                canvas.drawCircle(SPACE_LEFT, RADIUS, RADIUS, mCirclePaint);
                canvas.drawLine(SPACE_LEFT, 0, SPACE_LEFT,
                        getMeasuredHeight() - SPACE_DOWN, mLinePaint);
                break;
            case END:
                canvas.drawLine(SPACE_LEFT, SPACE_DOWN, SPACE_LEFT, getMeasuredHeight(), mLinePaint);
                canvas.drawCircle(SPACE_LEFT, SPACE_DOWN + getMeasuredHeight() + RADIUS,
                        RADIUS, mCirclePaint);
                break;
            case PLAY:
                canvas.drawRect(0, SPACE_DOWN, 2 * SPACE_LEFT,
                        getMeasuredHeight() + SPACE_DOWN, mRectPaint);
                canvas.drawLine(SPACE_LEFT, SPACE_DOWN, SPACE_LEFT,
                        getMeasuredHeight() + SPACE_DOWN, mLinePaint);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                if (type == PLAY) {
                    stop();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dX = event.getX() - downX;
                if (dX != 0) {
                    int l = (int) (getLeft() + dX);
                    int r = (int) (getRight() + dX);
                    if (type == START) {
                        if (r + minDis > endX) {
                            l = endX - lineWidth - minDis - lineWidth;
                            r = l + lineWidth;
                        }
                    } else if (type == END) {
                        if (l - minDis < startX) {
                            l = startX + minDis;
                            r = l + lineWidth;
                        }
                    }
                    this.layout(l, getTop(), r, getBottom());
                    if (mOnMoveEvent != null) {
                        mOnMoveEvent.onMove((int) getX());
                    }
                }
            case MotionEvent.ACTION_UP:
                if (mPlayLineTouchListener != null) {
                    mPlayLineTouchListener.onUp(getTimeScale());
                }
                break;
        }
        return true;
    }

    public void setRealWidth(int realWidth) {
        this.realWidth = realWidth;
        this.endX = realWidth;
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
    public double getTimeScale() {
        return getX() / realWidth;
    }

    public void setCurrentPosition(long time) {

    }

    /**
     * 控制播放音频线
     *
     * @param time 音频时长 单位秒
     */
    public void play(long time) {
        if (type != PLAY) return;
        mPlayAnim = ValueAnimator.ofInt((int) this.getX(), realWidth);
        mPlayAnim.setDuration(time);
        mPlayAnim.setInterpolator(new LinearInterpolator());
        mPlayAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                LineView.this.layout(value, 0, value + lineWidth, getMeasuredHeight());
            }
        });
        mPlayAnim.start();
    }

    public void stop() {
        if (type != PLAY || mPlayAnim == null) return;
        mPlayAnim.cancel();
        this.clearAnimation();
    }

    public void playFinish() {
        layout(0, 0, lineWidth, getMeasuredHeight());
    }

    public interface OnMoveEvent {
        void onMove(int x);
    }

    public interface OnPlayLineTouchListener {
        void onUp(double timeScale);
    }
}
