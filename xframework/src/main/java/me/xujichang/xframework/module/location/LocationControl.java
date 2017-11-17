package me.xujichang.xframework.module.location;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.a;

/**
 * des:
 * 定位SDK能够返回三种坐标类型的经纬度（国内），分别是gcj02（国测局坐标）、bd09（百度墨卡托坐标）和bd09ll（百度经纬度坐标）
 * 。如果开发者想利用定位SDK获得的经纬度直接在百度地图上标注，请选择坐标类型bd09ll。
 *
 * @author xjc by 2017/11/15 11:16 .
 */

public class LocationControl {
    private static LocationControl instance = null;
    private static LocationClient mClient = null;
    public static final int SCAN_SPAN_DEFAULT = 30 * 1000;

    public static LocationControl getInstance(Context context) {
        if (null == instance) {
            instance = ClassHolder.instance;
            mClient = new LocationClient(context);
            mClient.setLocOption(initLocationClient());
        }
        return instance;
    }

    private static LocationClientOption initLocationClient() {
        LocationClientOption option = new LocationClientOption();
        //定位模式
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        ////可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        //
        option.setCoorType("bd09ll");
        //设置请求间隔 0单次定位 如需连续周期请求 需>1000
        option.setScanSpan(SCAN_SPAN_DEFAULT);
        //是否使用GPS
        option.setOpenGps(false);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setLocationNotify(false);
        //设置进程是否可被杀死
        option.setIgnoreKillProcess(false);
        //是否手机Crash 信息
        option.SetIgnoreCacheException(false);
        //WIfi有效期
        option.setWifiCacheTimeOut(0);
        //设置过滤掉GPS仿真结果
        option.setEnableSimulateGps(false);
        //设置是否需要地址信息
        option.setIsNeedAddress(true);
        //位置描述信息
        option.setIsNeedLocationDescribe(true);
        return option;
    }

    private LocationControl() {

    }

    public LocationClient client(a locationListener) {
        mClient.registerLocationListener(locationListener);
        return mClient;
    }

    public static void registerLocationListener(a a) {
        mClient.registerLocationListener(a);
    }

    public static LocationClient getClient() {
        return mClient;
    }

    public static void changeScanSpan(int s) {
        LocationClientOption option = mClient.getLocOption();
        option.setScanSpan(s);
        mClient.setLocOption(option);
        mClient.restart();
    }

    public static void requestLocation() {
        mClient.requestLocation();
    }


    private static class ClassHolder {
        private static LocationControl instance = new LocationControl();
    }
}
