package me.xujichang.androidframework;

import me.xujichang.xframework.FrameworkApplication;

/**
 * des:
 *
 * @author xjc by 2017/11/13 11:58 .
 */

public class App extends FrameworkApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected String getBaiduApiKey() {
        return "go0ohg28cXaLAT5br9wHteWv";
    }

    @Override
    protected String getInitToken() {
        return "";
    }

    @Override
    protected String getAppBaseUrl() {
        return "http://10.10.100.6:9987/restful/";
    }

    @Override
    protected String getWebDir() {
        return "";
    }

    @Override
    protected String getBasePackageName() {
        return "";
    }
}
