package com.cimcitech.base_utils_class.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.cimcitech.base_utils_class.base.BaseLibrary;

import es.dmoral.toasty.Toasty;

public class ToastUtil {

    @SuppressLint("ShowToast")
    public static void showToast(Context context, String content) {

        if ("com.cimctech.bestscancode_android".equals(BaseLibrary.packageName) ||
                "com.cimcitech.best_waybill".equals(BaseLibrary.packageName)) {
            Toasty.error(context, content, Toast.LENGTH_SHORT, true).show();
        } else {

            CustomToast.getInstance().showToast(context, content);
        }
    }

    @SuppressLint("ShowToast")
    public static void showToast(Context context, int stringId) {

        if ("com.cimctech.bestscancode_android".equals(BaseLibrary.packageName) ||
                "com.cimcitech.best_waybill".equals(BaseLibrary.packageName)) {
            Toasty.error(context, context.getResources().getString(stringId), Toast.LENGTH_SHORT, true).show();
        } else {

            CustomToast.getInstance().showToast(context, stringId);
        }
    }

    public static void cancelToast() {
        CustomToast.getInstance().cancelToast();

    }

}
