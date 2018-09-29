package learn.bob.com.androidlearn.audioWave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * Created by cly on 18/9/20.
 */

public class WaveView extends View {
    private Paint mCenLinePaint;
    private Paint mWavePaint;

    private ArrayList<Integer> values = new ArrayList<>();
    private int MAX;
    private int i;
    private int width;
    private int height;
    private float BASEDIV = 0.5f;
    private float WAVEWIDTH = 0.5f;
    private boolean isDrawing = true;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
        initPaint();
    }

    private void initData() {
        int width = getScreenWith();
        MAX = (int) (width / (BASEDIV + WAVEWIDTH));
    }

    private int getScreenWith() {
        WindowManager wm = (WindowManager) this.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private void initPaint() {
        mCenLinePaint = new Paint();
        mCenLinePaint.setColor(Color.rgb(39, 199, 175));
        mCenLinePaint.setStrokeWidth(1);
        mCenLinePaint.setAntiAlias(true);
        mCenLinePaint.setFilterBitmap(true);
        mCenLinePaint.setStyle(Paint.Style.FILL);

        mWavePaint = new Paint();
        mWavePaint.setColor(Color.parseColor("#000000"));
        mWavePaint.setStrokeWidth(WAVEWIDTH);
        mWavePaint.setAntiAlias(true);
        mWavePaint.setDither(true);
        mWavePaint.setFilterBitmap(true);
        mWavePaint.setStyle(Paint.Style.STROKE);
    }

    public void startWave(int value) {
        isDrawing = true;
        if (i <= MAX - 1) {
            values.add(value);
            i++;
        } else {
            values.remove(0);
            values.add(value);
        }
        invalidate();

    }

    public void stopWave() {
//        isDrawing = false;
//        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isDrawing) return;
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        canvas.drawARGB(255, 239, 239, 239);
        canvas.drawLine(0, height * 0.5f, width, height * 0.5f, mCenLinePaint);
        for (int j = 0; j < values.size(); j++) {
            canvas.drawLine(j * (BASEDIV + WAVEWIDTH), height * 0.5f - values.get(j),
                    j * (BASEDIV + WAVEWIDTH), height * 0.5f + values.get(j), mWavePaint);
            Log.d("@cly", "x==" + j * (BASEDIV + WAVEWIDTH));
        }
    }
}
