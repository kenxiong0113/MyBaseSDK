package com.cimcitech.base_api_sdk;

import android.content.Context;

import com.cimcitech.base_api_sdk.request.BaseApiRequestImpl;

/**
 * @author by ken
 * Blog:
 * Date:2019/9/18
 * Description:
 */
public class BaseApiSDK {
    private Context mContext;

    private static BaseApiSDK sInstance;

    public static BaseApiSDK getInstance() {
        if (sInstance == null) {
            synchronized (BaseApiSDK.class) {
                if (sInstance == null) {

                    sInstance = new BaseApiSDK();
                }
            }
        }
        return sInstance;
    }

    private BaseApiSDK() {
    }


    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public BaseApiRequestImpl getApiInstance() {

        return new BaseApiRequestImpl();
    }
}
