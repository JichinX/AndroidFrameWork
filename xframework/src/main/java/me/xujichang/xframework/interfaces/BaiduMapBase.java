package me.xujichang.xframework.interfaces;

import com.baidu.location.BDLocation;

/**
 * des:
 *
 * @author xjc by 2017/11/15 13:57 .
 */

public interface BaiduMapBase {
    /**
     * 只定位一次
     */
    int MAP_MODE_SINGLE = 11;
    /**
     * 连续定位
     */
    int MAP_MODE_ALWAYS = 12;
    /**
     * 用来获取中心位置的坐标
     */
    int MAP_MODE_CENTER = 13;
    /**
     * 离线模式
     */
    int MAP_MODE_OFFLINE = 14;
    int MAP_NET_NONE = 0;
    int MAP_NET_WIFI = 1;
    int MAP_NET_GPRS = 4;

    /**
     * 获得定位信息
     *
     * @param location
     */
    void onReceiveLocation(BDLocation location);

    /**
     * 使用方式
     */
    int getUseMode();
}
