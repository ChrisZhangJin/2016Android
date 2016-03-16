package chris.zhang.nanjinginhand.customized;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import chris.zhang.nanjinginhand.R;

/**
 * Created by Administrator on 2016/2/26.
 */
public class WaitSurfaceView extends View {

    private final float UNIT = 80;

    private Paint paint;
    private float distance = 0;
    private float width = 0;
    private float height = 0;
    private Bitmap bitmap;
    private float picWidth = 0;
    private float picHeight = 0;

    public WaitSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);
        width = getWidth();
        height = getHeight();
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.star);
        picHeight = bitmap.getHeight();
        picWidth = bitmap.getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        paint.setColor(Color.WHITE);

        float left = (width - picWidth) / 2 + UNIT;
        float top = (height - picHeight) / 2 + UNIT;
        Matrix m1 = new Matrix();
        m1.setTranslate(left, top);
        //Matrix m3 = new Matrix();
        //m3.setRotate(90, width / 2 + UNIT, height / 2 + UNIT);
        //Matrix mzz = new Matrix();
        //mzz.setConcat(m3, m1);

        canvas.drawBitmap(bitmap, m1, paint);
    }

    public void repaint() {
        invalidate();
    }
}
