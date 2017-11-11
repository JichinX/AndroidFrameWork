package me.xujichang.xframework.base;

import me.xujichang.xframework.abs.AbstractFrameworkActivity;
import me.xujichang.xframework.bean.FrameworkConst;

/**
 * des:
 *
 * @author xjc by 2017/11/11 15:00 .
 */

public class FrameworkBaseActivity extends AbstractFrameworkActivity {
    @Override
    protected long getActivityExitDuration() {
        return FrameworkConst.EXIT_DURATION;
    }

    @Override
    protected String getMainActivityName() {
        return FrameworkConst.LAUNCH_ACTIVITY_NAME;
    }
}
