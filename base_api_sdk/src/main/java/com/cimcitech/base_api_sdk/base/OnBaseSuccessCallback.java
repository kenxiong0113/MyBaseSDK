package com.cimcitech.base_api_sdk.base;

/**
 * 获取请求成功内容 的基类回调
 *
 * @author by ken on 2019.4.23
 */
public interface OnBaseSuccessCallback extends OnBaseCallback {


    void onSuccess(String value);
}
