package me.xujichang.xframework.interfaces;

import com.xujichang.utils.base.SuperView;

/**
 * @author xjc
 *         Created by xjc on 2017/5/23.
 */

public interface FrameworkBaseView<T> extends SuperView<T> {
    /**
     * 数据加载失败
     *
     * @param code
     * @param msg
     */
    void loadFail(int code, String msg);
}
