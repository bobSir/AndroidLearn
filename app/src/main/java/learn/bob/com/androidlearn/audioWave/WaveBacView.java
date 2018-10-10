package learn.bob.com.androidlearn.audioWave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class WaveBacView extends View {
    public static final String GRAY_BC = "#4DD8D8D8";
    private Paint mGrayPaint;
    private RectF startRect;
    private RectF endRect;
    private int endRight;

    public WaveBacView(Context context) {
        this(context, null);
    }

    public WaveBacView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveBacView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mGrayPaint = new Paint();
        mGrayPaint.setColor(Color.parseColor(GRAY_BC));
        mGrayPaint.setStyle(Paint.Style.FILL);
        startRect = new RectF();
        endRect = new RectF();
    }

    public void drawStart(float right) {
        startRect.left = 0;
        startRect.top = 0;
        startRect.right = right;
        startRect.bottom = getMeasuredHeight();
        invalidate();
    }

    public void drawEnd(float left) {
        endRect.left = left;
        endRect.top = 0;
        endRect.right = endRight > 0 ? endRight : getMeasuredWidth();
        endRect.bottom = getMeasuredHeight();
        invalidate();
    }

    public void setEndRight(int right) {
        endRight = right;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawColor(Color.WHITE);
        canvas.drawRect(startRect, mGrayPaint);
        canvas.drawRect(endRect, mGrayPaint);
    }
}
