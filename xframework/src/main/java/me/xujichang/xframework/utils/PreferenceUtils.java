package me.xujichang.xframework.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;

import me.xujichang.xframework.interfaces.Preferencable;

/**
 * des:
 *
 * @author xjc by 2017/11/16 11:17 .
 */

public class PreferenceUtils {
    private static WeakReference<Context> mWeakReference;
    private static PreferenceUtils instance;

    public static void init(Context context) {
        mWeakReference = new WeakReference<Context>(context);
        initInstance();
    }

    private static void initInstance() {
        if (null == instance) {
            instance = ClassHolder.instance;
        }
    }

    public static PreferenceUtils getInstance() {
        initInstance();
        return instance;
    }

    private PreferenceUtils() {

    }

    /**
     * 从SP文件中初始化 对象
     *
     * @param t
     * @param <T>
     */
    public static <T extends Preferencable> void initDataFromSP(T t) {
        t.initFromSP(getDefaultPreference());
    }

    private static class ClassHolder {
        private static PreferenceUtils instance = new PreferenceUtils();
    }

    //--------------------------------------------------------------------------------------------------

    /**
     * 存储数据到默认的SP文件
     */
    public static <T extends Preferencable> void saveData(T t) {
        SharedPreferences.Editor editor = getDefaultPreferenceEditor();
        t.saveInSP(editor);
        editor.apply();
    }

    private static boolean checkContext() {
        return !(null == mWeakReference || null == mWeakReference.get());
    }

    private static SharedPreferences.Editor getDefaultPreferenceEditor() {
        return getDefaultPreference().edit();
    }

    /**
     * 获取默认的SP文件
     *
     * @return
     */
    private static SharedPreferences getDefaultPreference() {
        Context context = mWeakReference.get();
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
