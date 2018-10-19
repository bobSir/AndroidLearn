package com.bobLearn.audioWave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import com.bobLearn.util.DensityUtil;


/**
 * Created by cly on 18/9/21.
 */

public class WaveStaticView extends View {
    private Paint mCenPaint;
    private Paint mWavePaint;

    public final static int SPACE = DensityUtil.dip2px(9);//左右两边间距
    private int width;
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

    private void initData() {
        width = DensityUtil.getScreenWith(getContext()) - SPACE * 2;
        MAX = (int) (width / (BASEDIV + WAVEWIDTH));
    }

    public void setValues(ArrayList<Integer> integers) {
        ArrayList<Integer> values = new ArrayList<>();
        for (int i : integers) {
            values.add(i / 40 + 2);
        }
        this.mValues = values;
        int length = values.size();
        if (length > MAX) {
            doMoreValues();
        } else {
            BASEDIV = width / length - WAVEWIDTH;
            MAX = (int) (width / (BASEDIV + WAVEWIDTH));
        }
        invalidate();
    }

    private void doMoreValues() {
        int length = mValues.size();
        float clearNum = length - MAX;//共需清除的个数
        int temp = Math.round(length / clearNum);//从多少个数中清除一个
        temp = temp == 1 ? 2 : temp;
        ArrayList<Integer> newValues = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (i % temp != 0) {
                newValues.add(mValues.get(i));
            }
            if (newValues.size() == MAX) break;
        }
        mValues.clear();
        mValues.addAll(newValues);
        BASEDIV = width / mValues.size() - WAVEWIDTH;
        MAX = (int) (width / (BASEDIV + WAVEWIDTH));
    }

    private void initPaint() {
        mCenPaint = new Paint();
        mCenPaint.setColor(Color.parseColor("#D8D8D8"));
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
        int height = getMeasuredHeight();
//        canvas.drawARGB(255, 255, 255, 255);
        canvas.drawLine(SPACE, height * 0.5f, width, height * 0.5f, mCenPaint);
        for (int i = 0; i < mValues.size(); i++) {
            canvas.drawLine(i * (BASEDIV + WAVEWIDTH) + SPACE, height * 0.5f - mValues.get(i),
                    i * (BASEDIV + WAVEWIDTH) + SPACE, height * 0.5f + mValues.get(i), mWavePaint);
        }
    }

    public int getRealWidth() {
        return (int) ((BASEDIV + WAVEWIDTH) * mValues.size());
    }
}
