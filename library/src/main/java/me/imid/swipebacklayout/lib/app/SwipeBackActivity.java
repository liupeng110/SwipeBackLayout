
package me.imid.swipebacklayout.lib.app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Method;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

public class SwipeBackActivity extends AppCompatActivity{//implements SwipeLayoutCallback {
    private SwipeBackLayout mSwipeBackLayout;
    public final String tag ="swip";

    @Override  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(tag,"进入base中");
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(this).inflate( me.imid.swipebacklayout.lib.R.layout.swipeback_layout, null);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override  public void onScrollStateChange(int state, float scrollPercent) {  }
            @Override  public void onEdgeTouch(int edgeFlag) { convertActivityToTranslucent(SwipeBackActivity.this);  }
            @Override  public void onScrollOverThreshold() {Log.i(tag,"进入onScrollOverThreshold");}
        });
        mSwipeBackLayout.attachToActivity(this);
    }

//    public View findViewById(int id) {
//         return mSwipeBackLayout.findViewById(id);
//    }
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }
    public void scrollToFinishActivity() {
         convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    //activity背景设为透明 兼容
    public   void convertActivityFromTranslucent(Activity activity) {
        try {
            @SuppressLint("PrivateApi") Method method = Activity.class.getDeclaredMethod("convertFromTranslucent");
            method.setAccessible(true);
            method.invoke(activity);
        } catch (Throwable t) { }
    }//转为全屏不透明
    public   void convertActivityToTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            convertActivityToTranslucentAfterL(activity);
        } else {
            convertActivityToTranslucentBeforeL(activity);
        }
    }    //转换activity为透明
    private   void convertActivityToTranslucentBeforeL(Activity activity) {
        try {
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            @SuppressLint("PrivateApi") Method method = Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerClazz);
            method.setAccessible(true);
            method.invoke(activity, new Object[] {
                    null
            });
        } catch (Throwable t) { }
    }//在Android 5.0之前的平台上调用convertToTranslucent方法
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private   void convertActivityToTranslucentAfterL(Activity activity) {
        try {
            @SuppressLint("PrivateApi") Method getActivityOptions = Activity.class.getDeclaredMethod("getActivityOptions");
            getActivityOptions.setAccessible(true);
            Object options = getActivityOptions.invoke(activity);

            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            @SuppressLint("PrivateApi") Method convertToTranslucent =
                    Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerClazz, ActivityOptions.class);
            convertToTranslucent.setAccessible(true);
            convertToTranslucent.invoke(activity, null, options);
        } catch (Throwable t) {  }
    }//在Android 5.0之后的平台上调用convertToTranslucent方法
   //activity背景设为兼容



}
