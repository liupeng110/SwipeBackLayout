package me.imid.swipebacklayout.demo;

import android.app.Application;

/**
 * 717219917@qq.com      2017/11/27  14:43
 */

public class MyApp extends Application{

    @Override  public void onCreate() {
        super.onCreate();
         registerActivityLifecycleCallbacks(new LifeCycleCallback_Activity());

    }
}
