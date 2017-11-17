package me.xujichang.xframework;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;

import com.baidu.location.BDLocation;
import com.baidu.location.a;
import com.baidu.mapapi.SDKInitializer;
import com.xujichang.utils.retrofit.RetrofitManager;

import me.xujichang.hybirdbase.router.XRouter;
import me.xujichang.util.tool.LogTool;
import me.xujichang.xframework.bean.Location;
import me.xujichang.xframework.module.location.LocationControl;
import me.xujichang.xframework.utils.PreferenceUtils;

/**
 * @author xjc
 *         Created by xjc on 2017/6/29.
 */

public abstract class FrameworkApplication extends Application {
    public static Location sLocation = new Location();

    @Override
    public void onCreate() {
        super.onCreate();
//        initMetaData();
        PreferenceUtils.init(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        LocationControl
                .getInstance(getApplicationContext())
                .client(new a() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        sLocation.init(bdLocation);
                        PreferenceUtils.saveData(sLocation);
                        LogTool.d("application:-------------:" + sLocation);
                    }
                })
                .restart();

        //XRoute初始化
        new XRouter.Builder().basePackage(getBasePackageName()).htmlDir(getWebDir()).withContext(getBaseContext()).build();
        //RetrofitManager
        new RetrofitManager.Builder().baseUrl(getAppBaseUrl()).token(getInitToken()).build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
