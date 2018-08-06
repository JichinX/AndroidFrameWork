package me.xujichang.xframework.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import me.xujichang.xframework.interfaces.NetStatus;
import me.xujichang.xframework.utils.NetUtil;

/**
 * des:
 *
 * @author xjc by 2017/11/17 15:17 .
 */

public class NetStatusBroadcastReceiver extends BroadcastReceiver {
    public static final android.content.IntentFilter FILTER = new IntentFilter();
    private NetStatus mNetStatus;

    static {
        FILTER.addAction("android.NET.conn.CONNECTIVITY_CHANGE");
        FILTER.addAction("android.Net.wifi.WIFI_STATE_CHANGED");
        FILTER.addAction("android.net.wifi.STATE_CHANGE");
    }

    public NetStatusBroadcastReceiver(NetStatus netStatus) {
        mNetStatus = netStatus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            int netWorkState = NetUtil.getNetWorkState(context);
            // 接口回调传过去状态的类型
            mNetStatus.onNetStatusChange(netWorkState);
        }
    }
}
