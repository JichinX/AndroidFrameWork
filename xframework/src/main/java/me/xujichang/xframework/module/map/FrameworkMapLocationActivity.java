package me.xujichang.xframework.module.map;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

import me.xujichang.xframework.R;
import me.xujichang.xframework.base.FrameworkBaseActivity;

/**
 * des:
 *
 * @author xjc by 2017/11/11 16:02 .
 */

public class FrameworkMapLocationActivity extends FrameworkBaseActivity {
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        mMapView = findViewById(R.id.mv_map_view);
        initBaiduMap();
        getLifecycle().addObserver(new MapLocationObserver(this, mMapView));
    }

    private void initBaiduMap() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }
}
