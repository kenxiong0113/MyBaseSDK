package com.ken.mybasesdk;

import android.app.Application;

import com.cimcitech.base_utils_class.base.BaseLibrary;
import com.pgyersdk.crash.PgyCrashManager;

/**
 * Copyright (C) 2019-2020, by 中集智能, All rights reserved.
 * -----------------------------------------------------------------
 * File: MyApplication.java
 *
 * @author by ken
 * Create: 2019/8/23 15:36
 * @description： -----------------------------------------------------------------
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        初始化base_utils_class
        BaseLibrary.initBaseLibrary(this);
//        设置通知栏应用logo
        BaseLibrary.setAppIcon(R.mipmap.ic_launcher);

//        蒲公英 crash 异常捕获，注册sdk
        PgyCrashManager.register();
    }
}
