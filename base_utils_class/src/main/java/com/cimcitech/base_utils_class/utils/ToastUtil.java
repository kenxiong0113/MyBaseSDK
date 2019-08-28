package com.cimcitech.base_utils_class.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.cimcitech.base_utils_class.base.BaseLibrary;

import es.dmoral.toasty.Toasty;

public class ToastUtil {
    private static Toast toast;

    @SuppressLint("ShowToast")
    public static void showToast(Context context, String content) {

        if ("com.cimctech.bestscancode_android".equals(BaseLibrary.packageName) ||
                "com.cimcitech.best_waybill".equals(BaseLibrary.packageName)) {
            Toasty.error(context, content, Toast.LENGTH_SHORT, true).show();
        } else {
            if (toast == null) {
                toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            } else {
                toast.setText(content);
            }
            toast.show();
        }
    }
}
