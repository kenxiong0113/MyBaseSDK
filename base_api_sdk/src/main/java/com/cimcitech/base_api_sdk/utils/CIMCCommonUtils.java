package com.cimcitech.base_api_sdk.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.logging.HttpLoggingInterceptor;


public class CIMCCommonUtils {
    private static final String TAG = "CIMCCommonUtils";
    public static Gson mGson = new GsonBuilder().registerTypeAdapterFactory(new NullStringEmptyTypeAdapterFactory()).create();

    public static class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Log.d("HttpLogInfo", message);
        }
    }
}
