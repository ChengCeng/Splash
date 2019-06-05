package com.example.splash.utils;

import android.util.Log;

import com.example.splash.BuildConfig;

//封装打印日志
//起到统一管理作用
public class L {

    private static final String TAG = "hyman";

    private static boolean sDebug = BuildConfig.DEBUG;
    public static void d(String msg, Object... args){
        if (!sDebug){
            return;
        }
        Log.d(TAG, String.format(msg,args));
    }
}
