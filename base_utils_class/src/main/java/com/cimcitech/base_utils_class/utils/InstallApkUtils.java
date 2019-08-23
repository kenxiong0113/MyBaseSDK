package com.cimcitech.base_utils_class.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * Copyright (C) 2019-2020, by 中集智能, All rights reserved.
 * -----------------------------------------------------------------
 * File: InstallApkUtils.java
 *
 * @author by ken
 * Create: 2019/8/12 10:15
 * @description： 安装apk 工具
 * -----------------------------------------------------------------
 */
public class InstallApkUtils {
    private static final String TAG = "InstallApkUtils";
    private static InstallApkUtils downloadUtil;

    public static InstallApkUtils getInstance() {
        if (downloadUtil == null) {
            downloadUtil = new InstallApkUtils();
        }
        return downloadUtil;
    }


    public void installApk(Activity activity, File file, String packageName) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
//            File file = new File(path);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String authority = packageName + ".fileProvider";
                Uri fileUri = FileProvider.getUriForFile(activity, authority, file);
                intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            activity.startActivityForResult(intent, 0);
        } catch (Exception e) {
            // todo 安装失败的操作
            Log.e(TAG, "installApk: " + e.getMessage());
        }

    }
}
