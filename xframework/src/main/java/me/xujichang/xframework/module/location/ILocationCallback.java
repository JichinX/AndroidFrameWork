package me.xujichang.xframework.module.location;

import android.arch.lifecycle.Lifecycle;
import android.location.Location;

/**
 * Des:
 *
 * @author xjc
 *         Created on 2017/12/25 17:52.
 */

public interface ILocationCallback {
    /**
     * 需要获取权限
     *
     * @param strings
     */
    void shouldObtainPermission(String[] strings);

    /**
     * 获取到位置
     *
     * @param location
     */
    void obtainLocation(Location location);

    /**
     * 获取生命周期
     *
     * @return
     */
    Lifecycle getLifecycle();

}
