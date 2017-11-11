package me.xujichang.xframework.interfaces;

/**
 * @author xjc
 *         Created by xjc on 2017/5/23.
 */

public interface JsBridgeBaseListener {

    String getLocation();

    String getUserInfo();

    String getDeviceInfo();

    void finishActivity();

    void startAnotherActivity(String activityName);

    void showToast(String queryParameter);

    void startLoading(String queryParameter);

    void stopLoading();

    void showDialog(String queryParameter);

}
