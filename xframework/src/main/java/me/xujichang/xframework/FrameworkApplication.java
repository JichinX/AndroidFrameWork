package me.xujichang.xframework;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.xujichang.utils.retrofit.RetrofitManager;

import me.xujichang.hybirdbase.router.XRouter;
import me.xujichang.util.tool.LogTool;
import me.xujichang.xframework.bean.Location;
import me.xujichang.xframework.module.location.DeviceLocationControl;
import me.xujichang.xframework.module.location.LocationControl;
import me.xujichang.xframework.utils.PreferenceUtils;

/**
 * @author xjc
 *         Created by xjc on 2017/6/29.
 */

public abstract class FrameworkApplication extends Application {
    public static Location sLocation = new Location();
    private static android.location.Location cachedLocation;

    @Override
    public void onCreate() {
        super.onCreate();
//        initMetaData();
        PreferenceUtils.init(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        if (isIntranet()) {
            //内网
            LogTool.d("定位环境：内网-------------");
            DeviceLocationControl.getInstance().startGetLocation(getApplicationContext(), new DeviceLocationControl.SimpleLocalizationListener() {
                @Override
                public void onGotLocationWithProvider(android.location.Location location) {
                    if (null == location) {
                        return;
                    }
                    if (null == cachedLocation) {
                        cachedLocation = location;
                    } else {
                        if (DeviceLocationControl.isBetterLocation(location, cachedLocation)) {
                            cachedLocation = location;
                        }
                    }
                    sLocation.init(cachedLocation);
                    PreferenceUtils.saveData(sLocation);
                    LogTool.d("内网:-------------:" + sLocation);
                }
            });
        } else {
            //外网
            LogTool.d("定位环境：外网-------------");
            LocationControl
                    .getInstance(getApplicationContext())
                    .baiduClient(new BDAbstractLocationListener() {
                        @Override
                        public void onReceiveLocation(BDLocation bdLocation) {
                            sLocation.init(bdLocation);
                            PreferenceUtils.saveData(sLocation);
                            LogTool.d("外网:-------------:" + sLocation);
                        }
                    })
                    .restart();
        }

        //XRoute初始化
        new XRouter.Builder().basePackage(getBasePackageName()).htmlDir(getWebDir()).withContext(getBaseContext()).build();
        //RetrofitManager
        new RetrofitManager.Builder().baseUrl(getAppBaseUrl()).token(getInitToken()).build();
    }

    protected abstract boolean isIntranet();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    private void initMetaData() {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            applicationInfo.metaData.putString("com.baidu.lbsapi.API_KEY", getBaiduApiKey());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected String getBaiduApiKey() {
        return "";
    }

    /**
     * 请求时链接携带的Token
     *
     * @return token
     */
    protected abstract String getInitToken();

    /**
     * 请求链接的BaseUrl
     *
     * @return baseUrl
     */
    protected abstract String getAppBaseUrl();

    protected abstract String getWebDir();

    protected abstract String getBasePackageName();
}
