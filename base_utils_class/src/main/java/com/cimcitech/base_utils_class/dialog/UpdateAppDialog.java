package com.cimcitech.base_utils_class.dialog;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.cimcitech.base_utils_class.R;
import com.cimcitech.base_utils_class.base.OnBaseCommonCallback;

/**
 * Copyright (C) 2019-2020, by 中集智能, All rights reserved.
 * -----------------------------------------------------------------
 * File: UpdateAppDialog.java
 *
 * @author by ken
 * Create: 2019/8/22 16:17
 * @description： APP升级提示对话框
 * -----------------------------------------------------------------
 */
public class UpdateAppDialog {

    private static UpdateAppDialog sInstance;
    AlertDialog mDialog;

    public static UpdateAppDialog getInstance() {
        if (sInstance == null) {
            sInstance = new UpdateAppDialog();
        }
        return sInstance;
    }

    private UpdateAppDialog() {
    }


    /**
     * 显示升级对话框
     */
    public void showUpdateDialog(Activity activity, String content, OnBaseCommonCallback callback) {
        if (mDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View view = View.inflate(activity, R.layout.dialog_update_prompt, null);

            builder.setView(view).setCancelable(false);
            TextView tvUpdatePrompt = view.findViewById(R.id.tv_update_prompt);
            tvUpdatePrompt.setText((content.isEmpty()) ? "修复部分已知的bug" : content);

            TextView tvInstallNow = view.findViewById(R.id.tv_install_now);
            TextView tvLater = view.findViewById(R.id.tv_later);
//            LinearLayout layout = view.findViewById(R.id.ll_update_dialog);
//
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
//            params.height = DensityUtils.deviceHeightPX(activity) / 2;
//
//            layout.setLayoutParams(params);

            activity.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            mDialog = builder.show();

//
//            final WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
//            params.width = (DensityUtils.deviceWidthPX(activity) * 4) / 5;
//            params.height = (DensityUtils.deviceHeightPX(activity)*3) / 5;
//            mDialog.getWindow().setAttributes(params);
//            mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            tvInstallNow.setOnClickListener(v -> {
                dismissUpddateDialog();
                callback.onSuccess("升级");
            });

            tvLater.setOnClickListener(v -> dismissUpddateDialog());


        }

    }

    private void dismissUpddateDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }


}
