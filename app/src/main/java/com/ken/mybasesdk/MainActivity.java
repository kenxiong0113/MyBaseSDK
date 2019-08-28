package com.ken.mybasesdk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cimcitech.base_utils_class.base.BaseToolBarActivity;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.ken.mybasesdk.activity.UpdateAppVersionActivity;
import com.ken.mybasesdk.adapter.MenuGridViewAdapter;
import com.ken.mybasesdk.bean.MenuBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseToolBarActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.gv_menu)
    GridView gvMenu;
    MenuGridViewAdapter adapter;
    List<MenuBean> beanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initBase() {
        setAdapter();
    }

    @Override
    protected void initView() {
        setTopTitle("主页");

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
//       请求权限
        XXPermissions.with(this)
                // 可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //.constantRequest()
                // 支持请求6.0悬浮窗权限8.0请求安装权限
                .permission(Permission.REQUEST_INSTALL_PACKAGES)
                // 不指定权限则自动获取清单中的危险权限
                .permission(Permission.Group.STORAGE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {

                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                    }
                });
    }

    @Override
    public void setAdapter() {
        super.setAdapter();
        beanList.add(new MenuBean("APP版本更新", 0));
        beanList.add(new MenuBean("个性化Toast", 1));


        adapter = new MenuGridViewAdapter(this, beanList);
        gvMenu.setAdapter(adapter);
    }
}
