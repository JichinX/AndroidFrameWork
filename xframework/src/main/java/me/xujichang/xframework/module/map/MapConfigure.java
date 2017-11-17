package me.xujichang.xframework.module.map;

import com.baidu.mapapi.map.MyLocationConfiguration;

/**
 * des:
 * 定位模式：
 * mCurrentMode = LocationMode.FOLLOWING;//定位跟随态
 * mCurrentMode = LocationMode.NORMAL;   //默认为 LocationMode.NORMAL 普通态
 * mCurrentMode = LocationMode.COMPASS;  //定位罗盘态
 *
 * @author xjc by 2017/11/15 10:55 .
 */

public class MapConfigure {
    public static final int LOCATION_CONFIGURE_NORMAL = 1;
    private static final int COLOR_ACCURACY_CIRCLE_FILL = 0xAAFFFF88;
    private static final int COLOR_ACCURACY_CIRCLE_STROKE = 0xAA00FF00;

    public static MyLocationConfiguration createLocationConfigure(int type) {
        if (type == LOCATION_CONFIGURE_NORMAL) {
            return createNormalLocationConfigure();
        }
        return createDefaultLocationConfigure();
    }

    /**
     * 创建默认的配置
     *
     * @return
     */
    private static MyLocationConfiguration createDefaultLocationConfigure() {
        return new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,
                true, MapMarker.getNormalMarker());
    }

    /**
     * 创建最基本的配置
     *
     * @return
     */
    private static MyLocationConfiguration createNormalLocationConfigure() {
        return new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,
                true, MapMarker.getNormalMarker(), COLOR_ACCURACY_CIRCLE_FILL,
                COLOR_ACCURACY_CIRCLE_STROKE);
    }
}
