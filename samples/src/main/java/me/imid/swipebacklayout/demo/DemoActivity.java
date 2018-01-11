
package me.imid.swipebacklayout.demo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Issac on 8/11/13.
 */
public class DemoActivity extends SwipeBackActivity implements View.OnClickListener {
    private static final int VIBRATE_DURATION = 0;
    private int[] mBgColors;
    private static int mBgIndex = 0;
    private String mKeyTrackingMode;
    private RadioGroup mRadioGroup;
    private SwipeBackLayout mSwipeBackLayout;
    private Toolbar mToolbar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        findViews();
        changeActionBarColor();
        mKeyTrackingMode = getString(R.string.key_tracking_mode);
        mSwipeBackLayout  = getSwipeBackLayout();

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int edgeFlag;
                switch (checkedId) {
                    case R.id.mode_left:
                        edgeFlag = SwipeBackLayout.EDGE_LEFT;
                        break;
                    case R.id.mode_right:
                        edgeFlag = SwipeBackLayout.EDGE_RIGHT;
                        break;
                    case R.id.mode_bottom:
                        edgeFlag = SwipeBackLayout.EDGE_BOTTOM;
                        break;
                    default:
//                        edgeFlag = SwipeBackLayout.EDGE_ALL;
                        edgeFlag = SwipeBackLayout.EDGE_TOP;
                }
                mSwipeBackLayout.setEdgeTrackingEnabled(edgeFlag);
            }
        });

//        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
//            @Override public void onScrollStateChange(int state, float scrollPercent) { }
//            @Override public void onEdgeTouch(int edgeFlag) {
//                vibrate(VIBRATE_DURATION);
//            }
//            @Override public void onScrollOverThreshold() {
//                vibrate(VIBRATE_DURATION);
//            }
//        });
        isTopActivity(this);
    }

//需要依赖activity栈
    private boolean isTopActivity(Activity activity)
    {
        Log.i("top","当前："+activity.getClass().getSimpleName());
        ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        Log.i("top","最上层："+cn.getClassName());
        Log.i("top","最上层0："+cn.getShortClassName());
        Log.i("top","最上层1："+cn.getPackageName());
        return cn.getClassName().contains(activity.getClass().getSimpleName());
    }

    private void changeActionBarColor() {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColors()[mBgIndex]));
        mBgIndex++;
        if (mBgIndex >= getColors().length) {
            mBgIndex = 0;
        }
    }

    private void findViews() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_finish).setOnClickListener(this);
        mRadioGroup = findViewById(R.id.tracking_mode);
    }

    private int[] getColors() {
        if (mBgColors == null) {
            Resources resource = getResources();
            mBgColors = new int[] {
                    resource.getColor(R.color.androidColorA),
                    resource.getColor(R.color.androidColorB),
                    resource.getColor(R.color.androidColorC),
                    resource.getColor(R.color.androidColorD),
                    resource.getColor(R.color.androidColorE),
            };
        }
        return mBgColors;
    }

    private void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }//震动

    @Override  public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startActivity(new Intent(DemoActivity.this, DemoActivity.class));
                break;
            case R.id.btn_finish:
                scrollToFinishActivity();
                break;
        }
    }



}
