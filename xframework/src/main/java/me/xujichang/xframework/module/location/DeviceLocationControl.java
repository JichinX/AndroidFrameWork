package me.xujichang.xframework.module.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.lang.ref.WeakReference;

import me.xujichang.util.tool.BaiduTransform;
import me.xujichang.util.tool.LogTool;
import me.xujichang.util.tool.Transform;

/**
 * Des:
 *
 * @author xjc
 *         Created on 2017/12/25 17:39.
 */

public class DeviceLocationControl {
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private WeakReference<Context> contextWeakReference;

    private static DeviceLocationControl instance;
    private LocationManager locationManager;
    private DeviceLocationListener localizationListener;
    private SelfLocationListener locationListener;

    public static DeviceLocationControl getInstance() {
        if (null == instance) {
            instance = new DeviceLocationControl();
        }
        return instance;
    }

    /**
     * 开启定位
     *
     * @param context
     */
    public void startGetLocation(Context context, String provider, @NonNull DeviceLocationListener localizationListener) {
        contextWeakReference = new WeakReference<>(context);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.localizationListener = localizationListener;
        getLocationForProvider(provider);
    }

    public void startGetLocation(Context context, @NonNull DeviceLocationListener localizationListener) {
        contextWeakReference = new WeakReference<>(context);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.localizationListener = localizationListener;
        getLocationForProvider(LocationManager.GPS_PROVIDER);
        getLocationForProvider(LocationManager.NETWORK_PROVIDER);
    }

    private void getLocationForProvider(String provider) {
        boolean isEnabled = locationManager.isProviderEnabled(provider);
        if (!isEnabled) {
            localizationListener.onProviderDisable();
            return;
        }
        Context context = contextWeakReference.get();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            localizationListener.onLocationPermissionDenied(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        updateToNewLocation(location);
        locationListener = new SelfLocationListener();
        // 设置监听*器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
        locationManager.requestLocationUpdates(provider, 2 * 1000, 20,
                locationListener);
    }

    private void updateToNewLocation(Location location) {
        LogTool.d("Last Known Location:" + (null == location ? "location is null" : location.toString()));
        localizationListener.onGotLocationWithProvider(location);
    }

    public boolean compare(Object tempTarget, Object target) {
        return false;
    }

    public static double[] convertBaidu2Gps(double[] target) {
        double[] mars = BaiduTransform.TransBaidu2Mars(target[0], target[1]);
        return Transform.Mars2WGS(mars[1], mars[0]);
    }

    private class SelfLocationListener implements android.location.LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            LogTool.d("LocationListener:" + location.toString());
            localizationListener.onGotLocationWithProvider(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LogTool.d("LocationListener:Status Changed:");
            localizationListener.onLocationStatusChanged(status, extras);
        }

        @Override
        public void onProviderEnabled(String provider) {
            LogTool.d("LocationListener: enable");

            localizationListener.onProviderEnable();
        }

        @Override
        public void onProviderDisabled(String provider) {
            LogTool.d("LocationListener: disable");

            localizationListener.onProviderDisable();
        }
    }


    public static class SimpleLocalizationListener implements DeviceLocationListener {

        @Override
        public void onProviderDisable() {

        }

        @Override
        public void onLocationPermissionDenied(String[] permissions) {

        }

        @Override
        public void onGotLocationWithProvider(Location location) {

        }

        @Override
        public void onProviderEnable() {

        }


        @Override
        public void onLocationStatusChanged(int status, Bundle extras) {

        }
    }

    public static boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        //检查最新的位置是比较新还是比较旧
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        //如果当前的位置信息来源于两分钟前，使用最新位置，
        // 因为用户可能移动了
        if (isSignificantlyNewer) {
            return true;
            //如果最新的位置也来源于两分钟前，那么此位置会更加的不准确。
        } else if (isSignificantlyOlder) {
            return false;
        }

        //检查最新的位置信息是更加的准确还是不准确
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        //检查旧的位置和新的位置是否来自同一个provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        //结合及时性和精确度，决定位置信息的质量
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /*** 检查两个提供者是否是同一个*/
    private static boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

}
