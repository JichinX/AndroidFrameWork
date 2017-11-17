package me.xujichang.xframework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import me.xujichang.util.tool.LogTool;
import me.xujichang.util.tool.ViewTool;
import me.xujichang.xframework.abs.AbstractFrameworkActivity;
import me.xujichang.xframework.bean.FrameworkConst;
import me.xujichang.xframework.interfaces.NetStatus;

/**
 * des:
 *
 * @author xjc by 2017/11/11 15:00 .
 */

public class FrameworkBaseActivity extends AbstractFrameworkActivity implements NetStatus {
    private ViewTool mViewTool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewTool = ViewTool.getInstance(getLifecycle());
        getLifecycle().addObserver(createLifecycleObserver());
    }

    /**
     * 创建lifecycle监听器
     * 子类可在{@link FrameworkBaseLifecycleObserver}基础上进行扩展
     *
     * @return
     */
    private FrameworkBaseLifecycleObserver createLifecycleObserver() {
        return new FrameworkBaseLifecycleObserver(this);
    }

    @Override
    protected long getActivityExitDuration() {
        return FrameworkConst.EXIT_DURATION;
    }

    @Override
    protected String getMainActivityName() {
        return FrameworkConst.LAUNCH_ACTIVITY_NAME;
    }

    protected <T extends View> void proxyViewClick(T view, ViewTool.XOnClickListener<T> listener) {
        if (null != mViewTool) {
            mViewTool.proxyClickListener(view, listener);
        }
    }

    @Deprecated
    protected <T extends View> void proxyViewClickListener(T view, ViewTool.XOnClickListener<T> listener) {
        proxyViewClick(view, listener);
    }

    @Override
    public void onNetStatusChange(int status) {
        LogTool.d("网络状态发生变化");
    }
}
