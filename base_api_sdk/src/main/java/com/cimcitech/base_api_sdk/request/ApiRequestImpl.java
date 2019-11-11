package com.cimcitech.base_api_sdk.request;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.cimcitech.base_api_sdk.BaseApiSDK;
import com.cimcitech.base_api_sdk.base.BaseResponse;
import com.cimcitech.base_api_sdk.base.OnBaseCallback;
import com.cimcitech.base_api_sdk.base.ResultCode;
import com.cimcitech.base_api_sdk.bean.ApiRequestBean;
import com.cimcitech.base_api_sdk.request.inteface_if.BaseIF;
import com.cimcitech.base_api_sdk.utils.CIMCCommonUtils;
import com.cimcitech.base_api_sdk.utils.NetworkUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.cimcitech.base_api_sdk.base.ResultCode.REQUEST_SUCCESS;

/**
 * @author by ken
 * Blog:
 * Date:2019/9/18
 * Description: 接口请求实现类
 */
public class ApiRequestImpl implements BaseIF {
    private static final String TAG = "ApiRequestImpl";

    private final Handler mHandler;
    private OkHttpClient mOkHttpClient;

    public ApiRequestImpl() {

        okhttpclient();
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.mHandler = new Handler();
        } else {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
    }

    /**
     * 初始化okhttpclient.
     *
     * @return okhttpClient
     */
    private OkHttpClient okhttpclient() {
        if (mOkHttpClient == null) {
            HttpLoggingInterceptor logInterceptor;
            logInterceptor = new HttpLoggingInterceptor(new CIMCCommonUtils.HttpLogger());
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .addNetworkInterceptor(logInterceptor)
                    .build();

        }
        return mOkHttpClient;

    }


    /**
     * 开始post请求
     *
     * @param url
     * @param cimcRequestBean
     */
    public void startRequest(final OnBaseCallback callback, String url, final ApiRequestBean cimcRequestBean, final Class classOfT) {
        if (callback == null) {
            return;
        }

        if (!NetworkUtils.isNetworkAvailable(BaseApiSDK.getInstance().getContext())) {
            mHandler.post(() -> callback.onError(ResultCode.error_network_anomaly + "", "网络异常"));
            return;
        }

        String json;
        {
            json = CIMCCommonUtils.mGson.toJson(cimcRequestBean, ApiRequestBean.class).replace("\\n", "");
        }


        Log.e(TAG, "startRequest: fengle_request_body_"+json );
        RequestBody body;

        body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);//创建表单请求体


        final Request request = new Request.Builder()
                .url(url.trim())
                .addHeader("Content-Type", "application/json")
                .post(body)//传递请求体
                .build();


        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(() -> callback.onError(ResultCode.request_failure + "", e.toString()));

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String content = response.body().string();
                Log.e(TAG, "device_startRequest...onResponse_... 贝斯特" + content);

                disposeResultCode(callback, content, classOfT);
            }
        });


    }

    /**
     * 处理结果
     *
     * @param callback
     * @param content
     * @param classOfT
     */
    private void disposeResultCode(final OnBaseCallback callback, final String content, final Class classOfT) {
        mHandler.post(() -> {
            try {
//                            返回解析过的 json 实体类
                BaseResponse baseResponse;

                try {
                    JSONObject object = new JSONObject(content);
                    String status = object.getString("status");
                    Log.d(TAG, "disposeResultCode: status " + status);
                    BaseResponse.StatusBean statusBean = CIMCCommonUtils.mGson.fromJson(status, (Type) BaseResponse.StatusBean.class);
                    if (REQUEST_SUCCESS.equals(statusBean.getErrorCode())) {

                        baseResponse = CIMCCommonUtils.mGson.fromJson(content, (Type) classOfT);
                    } else {
                        callback.onError(statusBean.getErrorCode(), statusBean.getErrorMessage());
                        return;
                    }

                } catch (Exception e) {
                    Log.d(TAG, "disposeResultCode: json类型的数据有误" + e.getMessage());
                    callback.onError(ResultCode.error_not_json + "", "json类型的数据有误");
                    return;
                }

                if (baseResponse == null) {
                    callback.onError(ResultCode.error_not_json + "", "不是json类型的数据");
                    return;
                }


                switch (baseResponse.getStatus().getErrorCode()) {


                    default:
                        Log.e(TAG, "run: getErrorCode" + baseResponse.getStatus().getErrorCode() + "...getErrorMessage " + baseResponse.getStatus().getErrorMessage());
                        callback.onError(baseResponse.getStatus().getErrorCode(), baseResponse.getStatus().getErrorMessage());
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "run: " + e.getMessage());
                callback.onError("-1", "其他异常");
            }
        });
    }
}

