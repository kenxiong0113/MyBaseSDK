package com.cimcitech.base_utils_class.view;

/**
 * baseView 接口基类
 *
 * @author by ken on 2019.4.16
 */
public interface BaseViewIF {

    /**
     * @param value
     */
    void onSuccess(String value);


    /**
     * @param code  错误码
     * @param error 错误信息
     */
    void onFailure(String code, String error);


    void showAwaitDialog(int id);


    /**
     * 自定义加载对话框文字
     * @param value 加载文字
     * */
    void showLoadingContent(String value);

    void showAwaitDialog();

    void dismissAwaitDialog();

    void dismissImmediatelyAwaitDialog();

    /**
     * 加载完成dialog
     */
    void showSuccessDialog();

    /**
     * 警告dialog
     * */
    void showWarningDialog();

    /**
     * 提示dialog
     * */
    void showPromptDialog();

    /**
     * 错误dialog
     * */
    void showErrorDialog();


    /**
     * 吐司描述文字
     */
    void showToast(String content);

    void setAdapter();






}
