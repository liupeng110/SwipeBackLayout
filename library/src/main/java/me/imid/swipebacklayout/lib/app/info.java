package me.imid.swipebacklayout.lib.app;

/**
 * 717219917@qq.com      2017/11/24  16:10
 */

public class info {
    //1.在BaseActivity中增加一层根布局（FrameLayout）并附加到baseactivity中     mSwipeBackLayout.attachToActivity(this);
    //2.如何解决 滑动冲突
    //2.1最下层  滑动返回
    //2.2中间嵌套层
    //2.3嵌套层与最下层 解耦和
   //最下层根据fragment个数 判定是否需要子fragment处理(需要处理fragment生命周期事件)
   //最下层根据fragment个数 判定是否需要子fragment处理(需要处理fragment生命周期事件)
  //事件传到子fragment ,单独解决嵌套问题 因为子fragment最终需要滑动结束 为止
  //

    //3.重构
    //每个activity一个SwipeBackLayout  map保存layout实体类
    //有当前layout就可以



}
