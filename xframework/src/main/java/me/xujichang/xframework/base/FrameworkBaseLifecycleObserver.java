package me.xujichang.xframework.base;

import android.content.Context;

import me.xujichang.util.simple.SimpleLifeCycleObserver;
import me.xujichang.xframework.interfaces.NetStatus;
import me.xujichang.xframework.receiver.NetStatusBroadcastReceiver;

/**
 * des:
 *
 * @author xjc by 2017/11/17 15:43 .
 */

public class FrameworkBaseLifecycleObserver extends SimpleLifeCycleObserver {
    protected FrameworkBaseActivity mContext;
    protected NetStatusBroadcastReceiver mNetStatusBroadcastReceiver;

    public FrameworkBaseLifecycleObserver(FrameworkBaseActivity frameworkBaseActivity) {
        mContext = frameworkBaseActivity;
        mNetStatusBroadcastReceiver = new NetStatusBroadcastReceiver(mContext);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        //注册广播接受者
        mContext.registerReceiver(mNetStatusBroadcastReceiver, NetStatusBroadcastReceiver.FILTER);
    }

    @Override
    public void onPause() {
        super.onPause();
        mContext.unregisterReceiver(mNetStatusBroadcastReceiver);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onChange() {
        super.onChange();
    }
}
