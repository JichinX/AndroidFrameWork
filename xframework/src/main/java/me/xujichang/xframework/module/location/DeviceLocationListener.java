package me.xujichang.xframework.module.location;

import android.location.*;
import android.os.Bundle;

/**
 * Des:
 * Android 本身的定位回调
 *
 * @author xjc
 *         Created on 2017/12/25 17:38.
 */

public interface DeviceLocationListener {

    /**
     * GPS未开启
     */
    void onProviderDisable();

    /**
     * GPS权限 被拒绝
     *
     * @param permissions 权限
     */
    void onLocationPermissionDenied(String[] permissions);

    /**
     * Gps 定位成功
     *
     * @param location
     */
    void onGotLocationWithProvider(Location location);

    /**
     * Gps 可用
     */
    void onProviderEnable();

    /**
     * 状态改变
     *
     * @param status 状态
     * @param extras 数据
     */
    void onLocationStatusChanged(int status, Bundle extras);

}
