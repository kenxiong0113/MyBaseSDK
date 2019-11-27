package com.cimcitech.base_utils_class.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cimcitech.base_utils_class.R;

/**
 * @author by ken
 * Blog:
 * Date:2019/11/27
 * Description:
 */
public class CustomToast {
    @SuppressLint("StaticFieldLeak")
    private static CustomToast sInstance;

    public static CustomToast getInstance() {
        if (sInstance == null) {
            synchronized (CustomToast.class) {
                if (sInstance == null) {

                    sInstance = new CustomToast();
                }
            }
        }
        return sInstance;
    }

    private CustomToast() {
    }

    private Toast mToast;
    private TextView mTvToast;

    protected void showToast(Context ctx, String content) {
        cancelToast();
        if (mToast == null) {
            mToast = new Toast(ctx);
            mToast.setGravity(Gravity.CENTER, 0, 0);//设置toast显示的位置，这是居中
            mToast.setDuration(Toast.LENGTH_SHORT);//设置toast显示的时长
            View _root = LayoutInflater.from(ctx).inflate(R.layout.toast_custom_common, null);//自定义样式，自定义布局文件
            mTvToast = (TextView) _root.findViewById(R.id.tvCustomToast);
            mToast.setView(_root);//设置自定义的view
        }
        mTvToast.setText(content);//设置文本
        mToast.show();//展示toast
    }

    protected void showToast(Context ctx, int stringId) {
        showToast(ctx, ctx.getString(stringId));
    }

    protected void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
            mTvToast = null;
        }
    }
}
