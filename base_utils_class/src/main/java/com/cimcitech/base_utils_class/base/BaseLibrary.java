package com.cimcitech.base_utils_class.base;

import android.content.Context;

import es.dmoral.toasty.Toasty;

public class BaseLibrary {
    public static Context mContext;
    public static int mIcon;
    public static String packageName;

    public static void initBaseLibrary(Context context) {
        mContext = context;

        Toasty.Config.reset();
    }


    /**
     * 设置应用图标
     * 通知栏用到
     */
    public static void setAppIcon(int icon) {
        mIcon = icon;
    }

    public static void setPackageName(String name){
        packageName = name;
    }


}
