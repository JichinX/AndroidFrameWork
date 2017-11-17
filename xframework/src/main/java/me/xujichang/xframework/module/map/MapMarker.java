package me.xujichang.xframework.module.map;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;

import me.xujichang.xframework.R;

/**
 * des:
 *
 * @author xjc by 2017/11/15 11:02 .
 */

public class MapMarker {

    public static BitmapDescriptor getNormalMarker() {
        return BitmapDescriptorFactory.fromResource(R.drawable.ic_location);
    }
}
