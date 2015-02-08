package com.kunboy.qweibo.util;

import android.app.Activity;
import android.content.SharedPreferences;

import com.kunboy.qweibo.constant.GlobalConstant;

/**
 * 这里用全局的 GlobalConstant.sApplicationContext
 */
public class SharedPreferencesFactory {

    private static SharedPreferences sp = GlobalConstant.sApplicationContext.getSharedPreferences(GlobalConstant.sApplicationContext.getPackageName(), Activity.MODE_PRIVATE);

    public static void putStringValue(String key,
                                      String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringValue(String key,
                                        String defValue) {
        String value = sp.getString(key, defValue);
        return value;
    }

}
