package me.xujichang.xframework.module.map;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapView;

import me.xujichang.util.tool.LogTool;
import me.xujichang.util.tool.ViewTool;
import me.xujichang.xframework.interfaces.BaiduMapBase;
import me.xujichang.xframework.module.location.LocationControl;

/**
 * des:
 *
 * @author xjc by 2017/11/11 16:56 .
 */

public class MapLocationObserver extends ViewTool.XLifeCycleObserver {
    private BaiduMapBase mBase;
    private MapView mMapView;
    private BDAbstractLocationListener mA;
    private boolean locationOnceOnly = false;

    public MapLocationObserver(final BaiduMapBase base, MapView mapView) {
        this.mBase = base;
        mMapView = mapView;
        locationOnceOnly = base.getUseMode() != BaiduMapBase.MAP_MODE_ALWAYS;
        mA = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                LogTool.d("activity:------------");
                base.onReceiveLocation(bdLocation);
            }
        };
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {
        if (null != mMapView) {
            mMapView.onResume();
        }
        LocationControl.getClient().registerLocationListener(mA);
        if (locationOnceOnly) {
            LocationControl.changeScanSpan(0);
        } else {
            LocationControl.changeScanSpan(1000);
        }
    }

    @Override
    public void onPause() {
        if (null != mMapView) {
            mMapView.onPause();
        }
        LocationControl.changeScanSpan(LocationControl.SCAN_SPAN_DEFAULT);
        LocationControl.getClient().unRegisterLocationListener(mA);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        if (mMapView != null) {
            mMapView.onDestroy();
        }

    }

    @Override
    public void onChange() {

    }
}
