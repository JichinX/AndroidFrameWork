package me.xujichang.xframework.utils;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.xujichang.utils.tool.LogTool;

import me.xujichang.hybirdbase.router.XRouter;
import me.xujichang.xframework.interfaces.JsBridgeBaseListener;

/**
 * 管理基本的方法
 * toast
 * loading
 * dialog
 * deviceInfo
 * selfInfo
 * location
 * <p>
 *
 * @author xjc
 *         Created by xjc on 2017/5/23.
 */

public class JsBridgeManager {
    private static JsBridgeManager manager = null;

    private JsBridgeManager() {

    }

    protected JsBridgeManager(String args) {

    }

    public static JsBridgeManager getInstance() {
        if (null == manager) {
            manager = new JsBridgeManager();
        }
        return manager;
    }


    public void addDefaultHandler(BridgeWebView webView, final JsBridgeBaseListener baseListener) {
        webView.setDefaultHandler(new BridgeHandler() {
            @Override
            public void handler(String s, CallBackFunction callBackFunction) {
                LogTool.d(s);
                XRouter.getInstance().parseURL(s, baseListener, callBackFunction);
            }
        });

    }

}
