package me.xujichang.xframework.interfaces;

import android.content.SharedPreferences;

/**
 * des:
 *
 * @author xjc by 2017/11/16 11:10 .
 */

public interface Preferencable {
    void saveInSP(SharedPreferences.Editor editor);

    void initFromSP(SharedPreferences preferences);
}
