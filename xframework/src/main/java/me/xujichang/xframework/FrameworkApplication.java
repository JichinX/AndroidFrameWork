package me.xujichang.xframework;

import android.app.Application;

import com.xujichang.utils.retrofit.RetrofitManager;

import me.xujichang.hybirdbase.router.XRouter;

/**
 * @author xjc
 *         Created by xjc on 2017/6/29.
 */

public abstract class FrameworkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //XRoute初始化
        new XRouter.Builder().basePackage(getBasePackageName()).htmlDir(getWebDir()).withContext(getBaseContext()).build();
        //RetrofitManager
        new RetrofitManager.Builder().baseUrl(getAppBaseUrl()).token(getInitToken()).build();
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
