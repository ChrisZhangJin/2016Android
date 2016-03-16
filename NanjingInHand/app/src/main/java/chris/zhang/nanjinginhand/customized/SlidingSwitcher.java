package chris.zhang.nanjinginhand.customized;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

import chris.zhang.nanjinginhand.R;
import chris.zhang.nanjinginhand.util.Constants;

/**
 * Created by Administrator on 2016/3/5.
 */
public class SlidingSwitcher extends ViewGroup {

    public static final int SNAP_VELOCITY = 200;
    public static float xDown;
    public static float xUp;
    public static float xMove;

    /***
     * 该组件的宽度
     */
    private int width;
    private int currentItemIndex;
    /***
     * 边界数组，用于记录各个图片显示的边界。
     * 假设组件width=4，第一个临界点为0，那么第二个为-4，第二个为-8，第三个为-12
     *    pic4      pic3      pic2     pic1
     *    -12        -8        -4       0
     */
    private int[] borders;
    /***
     * 边界数组中最左边的边界点
     */
    private int leftEdge = 0;
    /***
     * 边界数组中最右边的边界点
     */
    private int rightEdge = 0;
    /***
     * 内部Layout，包含了内部子组件
     */
    private ViewGroup imageGroup;
    private int itemCount;
    private LinearLayout dotsLayout;
    private View firstItem;
    private MarginLayoutParams firstItemParams;
    private VelocityTracker velocityTracker;
    private Handler handler = new Handler();

    public SlidingSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlidingSwitcher);
        boolean isAutoPlay = array.getBoolean(R.styleable.SlidingSwitcher_auto_play, false);

        if (isAutoPlay) {
            startAutoPlay();
        }

        array.recycle();
    }

    public void startAutoPlay() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (currentItemIndex == itemCount - 1) {
                    currentItemIndex = 0;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            Log.i(Constants.TAG, "timer triggered==> Scroll to First.");

                            new ScrollToFirstTask().execute(20 * itemCount);
                            refreshDotsLayout();
                        }
                    });
                } else {
                    currentItemIndex++;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(Constants.TAG, "timer triggered==> Scroll -20.");

                            new ScrollTask().execute(-20);
                            refreshDotsLayout();
                        }
                    });
                }
            }
        }, 3000, 3000);
    }

    /***
     * 初始化菜单元素，为每一个子元素增加监听事件，并且改变所有子元素的宽度，让它们等于父元素的宽度。
     */
    private void initializeItems(){

        imageGroup = (ViewGroup)getChildAt(0);  // the first one should be a Layout!!
        if(imageGroup == null) return;

        itemCount = imageGroup.getChildCount();
        width = getWidth();
        borders = new int[itemCount];
        for (int i = 0; i < itemCount; i++) {
            borders[i] = -i * width; // 初始化边界点。

            /***
             *  set every pic's width as wide as parent
             */
            View item = imageGroup.getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) item.getLayoutParams();
            params.width = width;
            item.setLayoutParams(params);
        }

        leftEdge = borders[itemCount - 1];
        firstItem = imageGroup.getChildAt(0);
        firstItemParams = (MarginLayoutParams) firstItem.getLayoutParams();
    }

    private void initializeDots() {
        dotsLayout = (LinearLayout) getChildAt(1);
        refreshDotsLayout();
    }


    private void refreshDotsLayout() {
        dotsLayout.removeAllViews();
        for (int i = 0; i < itemCount; i++) {

            ImageView imageView = new ImageView(getContext());
            if (i == currentItemIndex) {
                imageView.setBackgroundResource(R.drawable.dot_selected);
            } else {
                imageView.setBackgroundResource(R.drawable.dot_unselected);
            }

            RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            relativeParams.addRule(RelativeLayout.CENTER_IN_PARENT);

            RelativeLayout relativeLayout = new RelativeLayout(getContext());
            relativeLayout.addView(imageView, relativeParams);

            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            linearParams.weight = 1;
            dotsLayout.addView(relativeLayout, linearParams);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //super.onLayout(changed, l, t, r, b);

        Log.i(Constants.TAG, "onLayout event.");

        if (changed) {
            initializeItems();
            initializeDots();
        }
    }

    /**
     *
     *
     * */
    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            int leftMargin = firstItemParams.leftMargin;
            while (true) {
                int speed = params[0];
                leftMargin += speed;
                if (isCrossBorder(leftMargin, speed)) {
                    leftMargin = findClosestBorder(leftMargin);
                    break;
                }

                publishProgress(leftMargin);
                sleep(10);
            }

            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            firstItemParams.leftMargin = values[0];
            firstItem.setLayoutParams(firstItemParams);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            firstItemParams.leftMargin = integer;
            firstItem.setLayoutParams(firstItemParams);
        }

        private void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private boolean isCrossBorder(int leftMargin, int speed) {
            for (int border : borders) {
                if (speed > 0) {
                    if (leftMargin >= border && leftMargin - speed < border) {
                        return true;
                    } else {
                        if (leftMargin <= border && leftMargin - speed > border) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }


        private int findClosestBorder(int leftMargin) {
            int absLeftMargin = Math.abs(leftMargin);
            int closestBorder = borders[0];
            int closestMargin = Math.abs(Math.abs(closestBorder) - absLeftMargin);
            for (int border : borders) {
                int margin = Math.abs(Math.abs(border) - absLeftMargin);
                if (margin < closestMargin) {
                    closestBorder = border;
                    closestMargin = margin;
                }
            }
            return closestBorder;
        }
    }

    class ScrollToFirstTask extends AsyncTask<Integer, Integer, Integer> {


        @Override
        protected Integer doInBackground(Integer... params) {
            int leftMargin = firstItemParams.leftMargin;
            while (true) {
                leftMargin += params[0];
                if (leftMargin > 0) {
                    leftMargin = 0;
                    break;
                }
                publishProgress(leftMargin);
                sleep(20);
            }

            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            firstItemParams.leftMargin = values[0];
            firstItem.setLayoutParams(firstItemParams);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            firstItemParams.leftMargin = integer;
            firstItem.setLayoutParams(firstItemParams);
        }


        private void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

