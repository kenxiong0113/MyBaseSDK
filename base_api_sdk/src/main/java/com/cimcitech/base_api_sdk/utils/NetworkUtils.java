package com.cimcitech.base_api_sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;


public class NetworkUtils {

    /**
     * 检测是否有网络
     *
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            /*
             * ConnectivityManager mConnectivityManager = (ConnectivityManager)
			 * mContext .getSystemService(Context.CONNECTIVITY_SERVICE);
			 * NetworkInfo mNetworkInfo =
			 * mConnectivityManager.getActiveNetworkInfo(); if (mNetworkInfo !=
			 * null) { return mNetworkInfo.isAvailable(); }
			 */
            if (isWifiAvailable(context)) {
                return true;
            } else if (is3GAvailable(context)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    /**
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: isWifiAvailable
     * @Description: TODO(检测wifi网络是否可用)
     */
    private static boolean isWifiAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnected();
    }

    /**
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: isWifiAvailable
     * @Description: TODO(检测wifi网络是否可用)
     */
    private static boolean is3GAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity==null||connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)==null){
            return false;
        }

        return connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnected();
    }
}
