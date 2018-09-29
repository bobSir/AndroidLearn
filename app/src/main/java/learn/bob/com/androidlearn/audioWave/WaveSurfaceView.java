package learn.bob.com.androidlearn.audioWave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by cly on 18/9/19.
 */

public class WaveSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private float divider = 0.2f;
    private float start;
    private int line_off;//上下边距距离
    private Paint mPaint;
    private Paint cenPaint;

    public WaveSurfaceView(Context context) {
        this(context, null);
    }

    public WaveSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initSurfaceView(this);
    }

    private void initSurfaceView(final SurfaceView sfv) {
        new Thread() {
            @Override
            public void run() {
                Canvas canvas = sfv.getHolder().lockCanvas(new Rect(0, 0, sfv.getWidth(), sfv.getHeight()));
                if (canvas == null) return;
                canvas.drawARGB(255, 239, 239, 239);
                int height = sfv.getHeight();
                cenPaint = new Paint();
                cenPaint.setColor(Color.rgb(39, 199, 175));
                cenPaint.setStrokeWidth(1);
                cenPaint.setAntiAlias(true);
                cenPaint.setFilterBitmap(true);
                cenPaint.setStyle(Paint.Style.FILL);

                mPaint = new Paint();
                mPaint.setColor(Color.parseColor("#000000"));
                mPaint.setStrokeWidth(1);
                mPaint.setAntiAlias(true);
                mPaint.setFilterBitmap(true);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawLine(0, height * 0.5f, sfv.getWidth(), height * 0.5f, cenPaint);//中心线
                sfv.getHolder().unlockCanvasAndPost(canvas);
            }
        }.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void drawWave(int y) {
        int height = this.getHeight();
        int width = this.getWidth();
        Canvas canvas = this.getHolder().lockCanvas(new Rect(0, 0, width, height));
        if (canvas == null) return;
        canvas.drawARGB(255, 239, 239, 239);
        canvas.drawLine(0, height * 0.5f, width, height * 0.5f, cenPaint);//中心线
        canvas.drawLine(start, height / 2 - y, start, height / 2 + y, mPaint);
        start = start + divider;
        this.getHolder().unlockCanvasAndPost(canvas);
    }
}
