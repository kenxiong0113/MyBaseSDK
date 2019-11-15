package com.cimcitech.base_api_sdk.base;


import android.util.Log;

/**
 * 错误码释义
 *
 * @author by ken on 2019.4.19
 */
public class ErrorCode {
    private static final String TAG = "ErrorCode";

    /**
     * 解析错误码
     *
     * @param code  错误码
     * @param error 错误信息
     */
    public static String toast(String code, String error) {
        Log.e(TAG, "toast: " + code + "..." + error);
        String err = "";
        switch (code) {
            case "00":
//                请求成功
                break;
            case "-3":
                err = "条码不正确";
                break;
            case "-2":
                err = error;
                break;
            case "-1":
                err = "异常错误！";
                break;
            case "-4":
                err = "数据空！";
                break;
            case "202":
                err = "json格式解析有误";
                break;
            case "400":
                err = "连接服务器超时！";
                break;
            case "54":
                err = "网络异常！";
                break;

            case "ParaError001":
                err = "输入参数不能为空";
                break;
            case "ParaError002":
                err = "表中暂无记录";
                break;
            case "ParaError003":
                err = "您输入的用户名或密码错误,请重新输入";
                break;
            case "ParaError004":
                err = "您的帐号无权限";
                break;
            case "ParaError005":
                err = "客户无此钢瓶信息";
                break;
            case "ParaError006":
                err = "该路线名字已存在";
                break;
            case "ParaError007":
                err = "您的帐号已停用";
                break;
            case "ParaError008":
                err = "您的用户信息不存在";
                break;
            case "ParaError009":
                err = "您的账号暂无权限,请联系管理员";
                break;
            case "ParaError010":
                err = "该用户没有对应组织";
                break;
            case "ParaError011":
                err = "极光推送失败";
                break;
            case "ParaError012":
                err = "找不到运单司机";
                break;
            case "ParaError013":
                err = "车牌号找不到";
                break;
            case "ParaError014":
                err = "请接最早的运单";
                break;
            case "ParaError015":
                err = "该订单所属的运单已经完成，无法取消";
                break;
            case "ParaError016":
                err = "请先取消该订单所属的运单";
                break;
            case "ParaError017":
                err = "登录失效，请重新登录";
                break;
            case "ParaError018":
                err = "请购单未通过请购审核，无法生成采购单";
                break;
            case "ParaError019":
                err = "新增失败";
                break;
            case "ResultError001":
                err = "提交失败";
                break;
            case "ParaError020":
                err = "排队完成数量超出当前充装站排队数量";
                break;
            case "ParaError021":
                err = "修改失败";
                break;
            case "ParaError022":
                err = "删除失败";
                break;
            case "ParaError023":
                err = "生成采购单失败";
                break;
            case "ParaError024":
                err = "选择、填空题数量不足";
                break;
            case "ParaError025":
                err = "选择题数量不足";
                break;
            case "RecodeError001":
                err = "该运单当前不允许上传图片";
                break;
            case "ParaError026":
                err = "填空题数量不足";
                break;
            case "ParaError027":
                err = "总分与题目数量不符";
                break;

            case "UploadError001":
                err = "上传图片失败";
                break;
            case "ParaError028":
                err = "输入数量已经超出了天际";
                break;
            case "ParaError029":
                err = "入库清单不能为空！";
                break;
            case "ParaError030":
                err = "您的账号暂无权限，请联系管理员处理";
                break;
            case "ParaError031":
                err = "该公司库存不足,无法出库！";
                break;
            case "ParaError032":
                err = "操作成功！";
                break;
            case "ParaError033":
                err = "上传图片格式不正确！";
                break;
            case "ParaError034":
                err = "请先拍照上传！";
                break;
            case "ParaError035":
                err = "该运单已完成！";
                break;
            case "ParaError036":
                err = "该状态下不允许操作！";
                break;
            case "ParaError037":
                err = "文件不存在！";
                break;
            case "ParaError038":
                err = "当前钢瓶状态已完成,不可初始化";
                break;
            case "ParaError039":
                err = "钢瓶信息不存在，请审查扫码钢瓶";
                break;
            case "ParaError040":
                err = "钢瓶信息重复存在，请检查钢瓶档案";
                break;
            case "ParaError041":
                err = "该车辆不满足取消条件";
                break;

            case "ParaError042":
                err = "卸货数量太大无法卸货";
                break;
            case "ParaError043":
                err = "不符合排队时间段设置要求";
                break;
            case "ParaError044":
                err = "未接单不允许添加耗油信息,请重新输入";
                break;
            case "ParaError045":
                err = "已配送的运单不能撤销";
                break;
            case "ParaError046":
                err = "不在允许的时间段内，不能下提气计划";
                break;
            case "ParaError047":
                err = "该状态下不允许申请调度变更";
                break;
            case "ParaError048":
                err = "该单不允许修改";
                break;
            case "ParaError049":
                err = "该承运商在该时间段的安检数量已满";
                break;
            case "ParaError050":
                err = "该状态下运单不允许操作";
                break;
            case "ParaError051":
                err = "已操作，请稍等";
                break;
            case "ParaError052":
                err = "该操作已被限制";
                break;
            case "ParaError053":
                err = "该车辆已被拉黑";
                break;
            case "ParaError054":
                err = "网络异常，请稍后再试";
                break;
            case "ParaError055":
                err = "等待审批中";
                break;
            case "ParaError056":
                err = "该驾驶员已停用";
                break;
            case "ParaError057":
                err = "该车辆在该提气日期的安排量已到最大值";
                break;
            case "ParaError058":
                err = "该车辆安排的提气时间距离上一次过近";
                break;
            case "ParaError059":
                err = "暂未开放此功能，请以线下邮件变更";
                break;
            case "ParaError060":
                err = "请输入正确的运单编号";
                break;
            case "ParaError061":
                err = "同一个执行日能只能存在唯一设置表";
                break;
            case "ParaError062":
                err = "已存在，不允许重复操作";
                break;

            default:
                err = error;
                break;
        }

        return err;

    }

}
