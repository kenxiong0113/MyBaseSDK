package com.cimcitech.base_utils_class.utils;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cimcitech.base_utils_class.R;
import com.cimcitech.base_utils_class.base.OnBaseCommonCallback;
import com.cimcitech.base_utils_class.dialog.UpdateAppDialog;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;

/**
 * Copyright (C) 2019-2020, by 中集智能, All rights reserved.
 * -----------------------------------------------------------------
 * File: UpdateAppVersionUtils.java
 *
 * @author by ken
 * Create: 2019/8/24 8:40
 * @description： 集成蒲公英在线更新APP工具类
 * -----------------------------------------------------------------
 */
public class UpdateAppVersionUtils {
    private static final String TAG = "UpdateAppVersionUtils";
    private Activity mActivity;
    private static UpdateAppVersionUtils sInstance;

    public static UpdateAppVersionUtils getInstance() {
        if (sInstance == null) {
            sInstance = new UpdateAppVersionUtils();
        }
        return sInstance;
    }

    private UpdateAppVersionUtils() {
    }

    public void checkAppVersion(Activity activity) {
        this.mActivity = activity;
        /** 新版本 **/
        new PgyUpdateManager.Builder()
                .setForced(true)                //设置是否强制提示更新,非自定义回调更新接口此方法有用
                .setUserCanRetry(true)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setDeleteHistroyApk(true)     // 检查更新前是否删除本地历史 Apk， 默认为true
                .setUpdateManagerListener(new UpdateManagerListener() {
                    @Override
                    public void onNoUpdateAvailable() {
                        //没有更新是回调此方法
                        Log.d("pgyer", "there is no new version");
                    }

                    @Override
                    public void onUpdateAvailable(AppBean appBean) {
                        UpdateAppDialog.getInstance().showUpdateDialog(activity, appBean.getReleaseNote(), new OnBaseCommonCallback() {
                            @Override
                            public void onSuccess(String value) {

                                //有更新回调此方法
                                Log.d("pgyer", "there is new version can update"
                                        + "new versionCode is " + appBean.getVersionCode());
                                //调用以下方法，DownloadFileListener 才有效；
                                //如果完全使用自己的下载方法，不需要设置DownloadFileListener
                                Log.e(TAG, "onUpdateAvailable: " + appBean.getDownloadURL());
                                PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
                            }

                            @Override
                            public void onError(String code, String error) {
                                ToastUtil.showToast(mActivity, error);
                            }
                        });

                    }

                    @Override
                    public void checkUpdateFailed(Exception e) {
                        //更新检测失败回调
                        //更新拒绝（应用被下架，过期，不在安装有效期，下载次数用尽）以及无网络情况会调用此接口
                        Log.e("pgyer", "check update failed ", e);
                    }
                })
                //注意 ：
                //下载方法调用 PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); 此回调才有效
                //此方法是方便用户自己实现下载进度和状态的 UI 提供的回调
                //想要使用蒲公英的默认下载进度的UI则不设置此方法
                .setDownloadFileListener(new DownloadFileListener() {
                    @Override
                    public void downloadFailed() {
                        //下载失败
                        Log.e("pgyer", "download apk failed");
                    }

                    @Override
                    public void downloadSuccessful(Uri uri) {
                        Log.e("pgyer", "download apk failed");
                        // 使用蒲公英提供的安装方法提示用户 安装apk
                        PgyUpdateManager.installApk(uri);
                    }

                    @Override
                    public void onProgressUpdate(Integer... integers) {
                        Log.e("pgyer", "update download apk progress" + integers.length);

                        for (int i = 0; i < integers.length; i++) {
                            Log.e(TAG, "onProgressUpdate: -----" + integers[i]);
                        }
                        activity.runOnUiThread(() -> {
                            showProgressDialog();
                            progressBar.setProgress(integers[0]);
                            tvProgress.setText("下载进度：" + (int) integers[0] + "%");
                            if ((int) integers[0] == 100) {
                                dismissProgressDialog();
                            }
                        });
                    }
                })
                .register();
    }


    private AlertDialog mDialog;

    private ProgressBar progressBar;
    private TextView tvProgress;

    private void showProgressDialog() {
        if (mDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            View view = View.inflate(mActivity, R.layout.progress_dialog, null);
            builder.setView(view).setCancelable(false);
            progressBar = view.findViewById(R.id.progressBar);
            tvProgress = view.findViewById(R.id.tv_progress);
            mDialog = builder.show();
        }
    }

    private void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
