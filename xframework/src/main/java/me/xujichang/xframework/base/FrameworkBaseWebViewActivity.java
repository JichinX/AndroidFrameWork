package me.xujichang.xframework.base;

import me.xujichang.xframework.abs.AbstractFrameworkWebViewActivity;

/**
 * des:
 *
 * @author xjc by 2017/11/11 15:10 .
 */

public class FrameworkBaseWebViewActivity extends AbstractFrameworkWebViewActivity {
    @Override
    protected void initHandler() {

    }

    @Override
    protected long getActivityExitDuration() {
        return 0;
    }

    @Override
    protected String getMainActivityName() {
        return null;
    }

    @Override
    public String getLocation() {
        return null;
    }

    @Override
    public String getUserInfo() {
        return null;
    }

    @Override
    public String getDeviceInfo() {
        return null;
    }
}
