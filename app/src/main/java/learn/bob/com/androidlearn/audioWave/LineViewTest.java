package learn.bob.com.androidlearn.audioWave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by cly on 18/9/21.
 */

public class LineViewTest extends View {
    private Paint mLinePaint;
    private Paint mCirclePaint;

    private ValueAnimator mPlayAnim;

    public int minDis;//起始线距末尾线最短距离 根据录制最短时间5S计算
    private static final float RADIUS = 6;
    public static final int LINE_WIDTH = 10;
    private int startX = 0;//裁剪起始线X
    private int endX = getScreenWidth();//裁剪末尾线X

    private float downX;
    private int width;
    private int height;
    private LineEnum type;

    public OnMoveEvent mOnMoveEvent;

    public enum LineEnum {
        START, PLAY, END
    }

    public LineViewTest(Context context) {
        this(context, null);
    }

    public LineViewTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineViewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnMoveEvent(OnMoveEvent onMoveEvent) {
        mOnMoveEvent = onMoveEvent;
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) this.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public void newLine(LineEnum type, int height, int width) {
        this.width = width;
        this.height = height;
        this.type = type;
        switch (type) {
            case START:
            case END:
                mLinePaint = new Paint();
                mLinePaint.setColor(Color.parseColor("#3CB371"));
                mLinePaint.setStrokeWidth(LINE_WIDTH);
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
                mLinePaint.setStrokeWidth(LINE_WIDTH);
                mLinePaint.setAntiAlias(true);
                mLinePaint.setDither(true);
                mLinePaint.setFilterBitmap(true);
                mLinePaint.setStyle(Paint.Style.STROKE);
                break;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mLinePaint == null) return;
        switch (type) {
            case START:
                canvas.drawCircle(20, 20, RADIUS, mCirclePaint);
                canvas.drawLine(20, 0, 20, height, mLinePaint);
                break;
            case END:
                canvas.drawLine(width, 0,
                        width, height, mLinePaint);
                canvas.drawCircle(width, height, RADIUS, mCirclePaint);
                break;
            case PLAY:
                canvas.drawLine(0, 0, 0, height, mLinePaint);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        Log.d("@cly touch", "type = " + type + " x = " + event.getX());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float dX = event.getX() - downX;
                if (dX != 0) {
                    int l = (int) (getLeft() + dX);
                    int r = (int) (getRight() + dX);
                    if (type == LineEnum.START) {
                        if (r + minDis > endX) {
                            l = endX - LINE_WIDTH - minDis - LINE_WIDTH;
                            r = l + LINE_WIDTH;
                        }
                    } else if (type == LineEnum.END) {
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
        if (type != LineEnum.PLAY) return;
        mPlayAnim = ValueAnimator.ofInt((int) this.getX(), getScreenWidth());
        mPlayAnim.setDuration(time * 1000);
        mPlayAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                LineViewTest.this.layout(value, 0, value + LINE_WIDTH, height);
            }
        });
        mPlayAnim.start();
    }

    public void stop() {
        if (type != LineEnum.PLAY) return;
        mPlayAnim.cancel();
        this.clearAnimation();
    }

    public interface OnMoveEvent {
        void onMove(int x);
    }
}
