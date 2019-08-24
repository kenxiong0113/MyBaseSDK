package com.ken.mybasesdk.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cimcitech.base_utils_class.base.BaseToolBarActivity;
import com.cimcitech.base_utils_class.utils.UpdateAppVersionUtils;
import com.ken.mybasesdk.R;

/**
 * APP版本更新示例：
 * <p>
 * 1、进入蒲公英官网 （https://www.pgyer.com/）
 * 2、发布应用后 获取到APP key
 * 3、在清单文件中 集成(把APP key换成你的应用的key)
 * <!-- 必填 -->
 * <meta-data
 * android:name="PGYER_APPID"
 * android:value="你的key"/>
 *
 * @author by ken on 2019.8.24
 */
public class UpdateAppVersionActivity extends BaseToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_app_version;
    }

    @Override
    protected void initBase() {
        UpdateAppVersionUtils.getInstance().checkAppVersion(this);
    }

    @Override
    protected void initView() {
        setLeftBack();
        setTopTitle("APP版本更新示例");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
