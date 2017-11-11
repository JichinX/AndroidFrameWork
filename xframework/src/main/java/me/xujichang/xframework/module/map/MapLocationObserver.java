package me.xujichang.xframework.module.map;

import com.baidu.mapapi.map.MapView;

import me.xujichang.util.tool.ViewTool;

/**
 * des:
 *
 * @author xjc by 2017/11/11 16:56 .
 */

public class MapLocationObserver extends ViewTool.XLifeCycleObserver {
    private FrameworkMapLocationActivity mActivity;
    private MapView mMapView;

    public MapLocationObserver(FrameworkMapLocationActivity activity, MapView mapView) {
        mActivity = activity;
        mMapView = mapView;
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
    }

    @Override
    public void onPause() {
        if (null != mMapView) {
            mMapView.onPause();
        }

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
