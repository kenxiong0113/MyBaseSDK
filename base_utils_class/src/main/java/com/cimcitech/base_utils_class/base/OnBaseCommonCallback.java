package com.cimcitech.base_utils_class.base;

/**
 * Copyright (C) 2019-2020, by 中集智能, All rights reserved.
 * -----------------------------------------------------------------
 * File: OnBaseCommonCallback.java
 *
 * @author by ken
 * Create: 2019/8/9 13:38
 * @description： -----------------------------------------------------------------
 */
public interface OnBaseCommonCallback {
    /**
     * 返回成功
     *
     * @param value 返回值
     */
    void onSuccess(String value);

    /**
     * 返回失败
     * @param code
     * @param error
     */
    void onError(String code, String error);
}
