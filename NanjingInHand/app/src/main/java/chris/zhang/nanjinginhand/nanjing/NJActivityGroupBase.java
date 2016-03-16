package chris.zhang.nanjinginhand.nanjing;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/2/28.
 */
public abstract class NJActivityGroupBase extends ActivityGroup implements CompoundButton.OnCheckedChangeListener {

    protected static final String CONTENT_ACTIVITY_HOME = "HOME";
    protected static final String CONTENT_ACTIVITY_SEARCH = "SEARCH";
    protected static final String CONTENT_ACTIVITY_SETTINGS = "SETTINGS";

    private LocalActivityManager localActivityManager;
    private ViewGroup container;

    protected abstract ViewGroup getContainer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private Intent initIntent(Class<?> cls) {
        return new Intent(this, cls).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    protected void setContainerView(String activityName, Class<?> classType) {
        if (null == localActivityManager) {
            localActivityManager = getLocalActivityManager();
        }

        if (null == container) {
            container = getContainer();
        }

        container.removeAllViews();
        Activity contentActivity = localActivityManager.getActivity(activityName);
        if (activityName.equals("xx")) {
            localActivityManager.startActivity(activityName, initIntent(classType));
        } else if (null == contentActivity) {
            localActivityManager.startActivity(activityName, initIntent(classType));
        } else {
            container.addView(localActivityManager.getActivity(activityName).getWindow().getDecorView(),
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
    }
}
