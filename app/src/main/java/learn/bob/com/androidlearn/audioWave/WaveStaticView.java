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
 * Created by cly on 18/9/21.
 */

public class WaveStaticView extends View {
    private Paint mCenPaint;
    private Paint mWavePaint;

    private float BASEDIV = 0.5f;//间隔 取值范围大于0.5
    private float WAVEWIDTH = 0.5f;
    private int MAX;//最大线数
    private ArrayList<Integer> mValues = new ArrayList<>();

    public WaveStaticView(Context context) {
        this(context, null);
    }

    public WaveStaticView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveStaticView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
        initPaint();
    }

    private int getScreenWith() {
        WindowManager wm = (WindowManager) this.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private void initData() {
        MAX = (int) (getScreenWith() / (BASEDIV + WAVEWIDTH));
    }

    public void setValues(ArrayList<Integer> values) {
        this.mValues = values;
        int length = values.size();
        if (length > MAX) {
            doMoreValues();
        } else {
            BASEDIV = getScreenWith() / length - WAVEWIDTH;
            MAX = (int) (getScreenWith() / (BASEDIV + WAVEWIDTH));
        }
        invalidate();
    }

    private void doMoreValues() {
        int length = mValues.size();
        float clearNum = length - MAX;//共需清除的个数
        int temp = Math.round(length / clearNum);//从多少个数中清除一个

        ArrayList<Integer> newValues = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (i % temp == 0) {
                newValues.add(mValues.get(i));
            }
            if (newValues.size() == MAX) break;
        }
        mValues.clear();
        mValues.addAll(newValues);
        BASEDIV = getScreenWith() / mValues.size() - WAVEWIDTH;
        MAX = (int) (getScreenWith() / (BASEDIV + WAVEWIDTH));
    }

    private void initPaint() {
        mCenPaint = new Paint();
        mCenPaint = new Paint();
        mCenPaint.setColor(Color.rgb(39, 199, 175));
        mCenPaint.setStrokeWidth(1);
        mCenPaint.setAntiAlias(true);
        mCenPaint.setFilterBitmap(true);
        mCenPaint.setStyle(Paint.Style.FILL);

        mWavePaint = new Paint();
        mWavePaint.setColor(Color.parseColor("#000000"));
        mWavePaint.setStrokeWidth(WAVEWIDTH);
        mWavePaint.setAntiAlias(true);
        mWavePaint.setDither(true);
        mWavePaint.setFilterBitmap(true);
        mWavePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        canvas.drawARGB(255, 239, 239, 239);
        canvas.drawLine(0, height * 0.5f, width, height * 0.5f, mCenPaint);
        for (int i = 0; i < mValues.size(); i++) {
            canvas.drawLine(i * (BASEDIV + WAVEWIDTH), height * 0.5f - mValues.get(i),
                    i * (BASEDIV + WAVEWIDTH), height * 0.5f + mValues.get(i), mWavePaint);
            Log.d("@cly", "x==" + i * (BASEDIV + WAVEWIDTH));
        }
    }
}
