package me.xujichang.xframework.module.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.NetworkUtil;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

import me.xujichang.util.tool.LogTool;
import me.xujichang.util.tool.ViewTool;
import me.xujichang.xframework.FrameworkApplication;
import me.xujichang.xframework.R;
import me.xujichang.xframework.base.FrameworkBaseActivity;
import me.xujichang.xframework.bean.FrameworkConst;
import me.xujichang.xframework.bean.Location;
import me.xujichang.xframework.interfaces.BaiduMapBase;
import me.xujichang.xframework.module.location.LocationControl;
import me.xujichang.xframework.utils.PreferenceUtils;

/**
 * des:
 * 120.304636,30.413805
 * 坐标系说明：
 * 在海外地区，只能获得WGS84坐标。
 * 请开发者在使用过程中注意坐标选择。
 * 定位SDK默认输出GCJ02坐标，
 * 地图SDK默认输出BD09ll
 *
 * @author xjc by 2017/11/11 16:02 .
 */

public class FrameworkMapLocationActivity extends FrameworkBaseActivity implements BaiduMapBase {
    /**
     * MapView
     */
    private MapView mMapView;
    /**
     * BaiduMap
     */
    private BaiduMap mBaiduMap;
    /**
     * 当前的地图中心坐标
     */
    private LatLng currentLatLng;
    /**
     *
     */
    private MapStatusUpdate statusUpdate;
    private float currentZoom;
    private boolean isFirstLocation = true;
    private int locationMode = MAP_MODE_ALWAYS;
    private TextView mTvTypeCode;
    private TextView mTvLatlngData;
    private TextView mTvLocTxt;
    private FloatingActionButton mFabBackLocation;
    private int netMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initForIntent(getIntent());
        setContentView(R.layout.activity_map);
        if (!checkMapEnvironment()) {
            return;
        }
        initView();
        initBaiduMap();
        getLifecycle().addObserver(new MapLocationObserver(this, mMapView));
    }

    /**
     * 检测地图使用环境
     * 联网 或者离线地图
     * 否则 无法正确显示图层
     *
     * @return true 使用环境正常 false 不能正确显示地图图层。
     * <p>
     * 对于具体的APP使用环境需要重写此方法。
     */
    private boolean checkMapEnvironment() {
        String netModeStr = NetworkUtil.getCurrentNetMode(this);
        LogTool.d(netModeStr);
        netMode = Integer.parseInt(netModeStr);
//        return !"0".equals(netMode);
        if (MAP_NET_NONE == netMode && !setIsIgnoreNetStatus()) {
            return false;
        }
        return true;
    }

    private boolean setIsIgnoreNetStatus() {
        return true;
    }

    private void initView() {
        showBackArrow();
        setActionBarTitle("地图位置选择");
        setRightText("完成");
        mMapView = findViewById(R.id.mv_map_view);
        mTvTypeCode = findViewById(R.id.tv_type_code);
        mTvLatlngData = findViewById(R.id.tv_latlng_data);
        mTvLocTxt = findViewById(R.id.tv_loc_txt);
        mFabBackLocation = findViewById(R.id.fab_back_location);
        proxyViewClick(mFabBackLocation, new ViewTool.XOnClickListener<FloatingActionButton>() {
            @Override
            public void onClick(FloatingActionButton view) {
                isFirstLocation = true;
                LocationControl.getClient().requestLocation();
            }
        });
        if (netMode == MAP_NET_NONE) {
            //隐藏地址解析 与地址信息描述
            mTvLocTxt.setVisibility(View.GONE);
        }
    }

    /**
     * 从Intent 中获取配置信息
     *
     * @param intent
     */
    private void initForIntent(Intent intent) {
        locationMode = intent.getIntExtra(FrameworkConst.Intent.LOCATION_MODE, MAP_MODE_ALWAYS);
    }

    /**
     * 配置百度地图信息
     */
    private void initBaiduMap() {
        mMapView.showZoomControls(true);
        // 不显示百度地图Logo
        mMapView.removeViewAt(1);
        //获取map对象
        mBaiduMap = mMapView.getMap();
        //设置地图类型
        //MAP_TYPE_NORMAL	普通地图（包含3D地图）
        //MAP_TYPE_SATELLITE	卫星图
        //MAP_TYPE_NONE 空白地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        if (locationMode == MAP_MODE_ALWAYS) {
            initForModeAlways();
        } else if (locationMode == MAP_MODE_CENTER) {
            initForModeCenter();
        } else if (locationMode == MAP_MODE_SINGLE) {
            initForModeSingle();
        }
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                LogTool.d("onMapStatusChangeStart:---------");
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
                LogTool.d("onMapStatusChangeStart:---------");

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                LogTool.d("onMapStatusChange:---------");
                LatLng latLng = mapStatus.target;
                mTvLatlngData.setText(getString(R.string.string_lat_lng, latLng.latitude, latLng.longitude));
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
//                currentZoom = mapStatus.zoom;
                LogTool.d("onMapStatusChangeFinish:---------" + mapStatus.target);
            }
        });
        setMapUiSetting(mBaiduMap.getUiSettings());
        currentLatLng = getTempLocation();
        if (null == statusUpdate) {
            statusUpdate = MapStatusUpdateFactory.newLatLngZoom(currentLatLng, 16.0f);
        }
        mBaiduMap.setMapStatus(statusUpdate);
        mTvLocTxt.setText("正在查询...");
    }

    /**
     * 单次定位 初始化Bitmap
     */
    private void initForModeSingle() {
        mBaiduMap.setMyLocationEnabled(true);
        //设置定位内容 定位模式、是否启用方向、marker、以及颜色等
        mBaiduMap.setMyLocationConfiguration(MapConfigure.createLocationConfigure(MapConfigure.LOCATION_CONFIGURE_NORMAL));

    }

    /**
     * 取中心 位置经纬度坐标
     */
    private void initForModeCenter() {
        mBaiduMap.setMyLocationEnabled(false);

    }

    /**
     * 连续定位的设置
     */
    private void initForModeAlways() {
        mBaiduMap.setMyLocationEnabled(true);
        //设置定位内容 定位模式、是否启用方向、marker、以及颜色等
        mBaiduMap.setMyLocationConfiguration(MapConfigure.createLocationConfigure(MapConfigure.LOCATION_CONFIGURE_NORMAL));

    }

    private void setMapUiSetting(UiSettings uiSettings) {
        uiSettings.setOverlookingGesturesEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
    }

    /**
     * 获取缓存中的位置信息
     *
     * @return
     */
    protected LatLng getTempLocation() {
        LatLng latLng = null;
        //从Application 取
        latLng = FrameworkApplication.sLocation.transToLatLng();
        if (null != latLng) {
            return latLng;
        }
        //从SP取
        Location location = new Location();
        PreferenceUtils.initDataFromSP(location);
        latLng = location.transToLatLng();
        if (null != latLng) {
            return latLng;
        }
        //最后 返回默认
        return setDefaultLocation();
    }

    private LatLng setDefaultLocation() {
        return new LatLng(30.413805, 120.304636);
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        if (!isFirstLocation) {
            return;
        }
        String addr = location.getAddrStr();
        String des = location.getLocationDescribe();
        String locDes = location.getLocTypeDescription();
        LogTool.d("位置信息：" + addr + "");
        LogTool.d("位置信息描述：" + des + "");
        LogTool.d("定位信息描述信息：" + locDes + "");
        if (mTvLocTxt.getVisibility() == View.VISIBLE) {
            mTvLocTxt.setText(addr + "\n(" + des + ")");
        }
        mTvTypeCode.setText("(" + location.getCoorType() + ")");
        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (locationMode != MAP_MODE_CENTER) {
            mBaiduMap.setMyLocationData(getLocationData(location));
        }
        statusUpdate = MapStatusUpdateFactory.newLatLngZoom(currentLatLng, 17f);
        mBaiduMap.setMapStatus(statusUpdate);
        isFirstLocation = false;
    }

    @Override
    public int getUseMode() {
        return locationMode;
    }

    /**
     * 获取位置数据
     *
     * @param location
     * @return
     */
    private MyLocationData getLocationData(BDLocation location) {
        return new MyLocationData
                .Builder()
                .accuracy(location.getRadius())
                .direction(location.getDirection())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .satellitesNum(location.getSatelliteNumber())
                .speed(location.getSpeed())
                .build();
    }

    @Override
    protected void onLeftAreaClick() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onRightAreaClick() {
        Location location = new Location();
        location.init(currentLatLng);
        Intent intent = new Intent();
        intent.putExtra(FrameworkConst.FLAG.LOCATION, location);
        setResult(RESULT_OK, intent);
        finish();
    }
}
