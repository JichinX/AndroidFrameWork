package me.xujichang.xframework.module.location;

import com.baidu.location.BDLocation;
import com.baidu.location.a;

/**
 * des:
 *
 * @author xjc by 2017/11/15 11:17 .
 */

public class LocationListener extends a {
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {

    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {
        super.onConnectHotSpotMessage(s, i);
    }

    @Override
    public void onLocDiagnosticMessage(int i, int i1, String s) {
        super.onLocDiagnosticMessage(i, i1, s);
    }

    public LocationListener() {
        super();
    }
}
