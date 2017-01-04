package edu.njupt.liuq.zhbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sharePreference封装
 * Created by Administrator on 2017/1/4.
 */

public class PrefUtils {
    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);

    }
    public static void SetBoolean(Context ctx, String key, boolean value) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key,value).commit();

    }

    public static String  getString(Context ctx, String key, String defValue) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);

    }
    public static void SetString(Context ctx, String key, String  value) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key,value).commit();

    }

    public static int getInt(Context ctx, String key, int defValue) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defValue);

    }
    public static void SetInt(Context ctx, String key, int value) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key,value).commit();

    }
}
