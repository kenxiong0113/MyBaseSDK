package com.cimcitech.base_utils_class.base;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.cimcitech.base_utils_class.R;
import com.cimcitech.base_utils_class.dialog.CommonHintDialog;
import com.cimcitech.base_utils_class.progress_dialog.PromptDialog;
import com.cimcitech.base_utils_class.thread_pool.ThreadPoolProxyFactory;
import com.cimcitech.base_utils_class.utils.DensityUtils;
import com.cimcitech.base_utils_class.utils.LogUtils;
import com.cimcitech.base_utils_class.utils.OpenCameraUtils;
import com.cimcitech.base_utils_class.utils.ToastUtil;
import com.cimcitech.base_utils_class.view.BaseViewIF;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.ButterKnife;

import static java.lang.Thread.sleep;


public abstract class BaseToolBarActivity extends AppCompatActivity implements BaseViewIF {

    public static final int REQUEST_CAMERA_CODE = 100;
    protected static final String TAG = "BaseToolBarActivity";
    protected Activity mActivity;
    protected Bundle savedInstanceState;
    protected Toolbar sToolbar;
    private TextView tvTitle;
    private TextView tvH;
    private FrameLayout viewContent;
    private OnClickListener onClickListenerTopLeft;
    private OnClickListener onClickListenerTopRight;
    private int menuResId;
    private String menuStr;
    MenuItem menuItem;

    CommonHintDialog rCommonHintDialog;

    PromptDialog promptDialog;
    public View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;
        //标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.base_toolbar);
        sToolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tvTitle);
        tvH = findViewById(R.id.tv_h);
        viewContent = findViewById(R.id.viewContent);
        //将继承 TopBarBaseActivity 的布局解析到 FrameLayout 里面
        view = LayoutInflater.from(this).inflate(getLayoutId(), viewContent);
        //初始化设置 Toolbar
        setSupportActionBar(sToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        mActivity = this;
        setHideToolbar(false);
//        StatusBarColorUtil.setStatusBarLightMode(this, ContextCompat.getColor(this, R.color.white));

        initBase();
        initView();
        initListener();
        initData();

    }


    protected void setTopTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
    }

    protected void setHideToolbar(boolean hide) {
        if (hide) {
            sToolbar.setVisibility(View.GONE);
            tvH.setVisibility(View.GONE);
        } else {
            sToolbar.setVisibility(View.VISIBLE);
            tvH.setVisibility(View.VISIBLE);
        }
    }


    protected void setTopLeftButton(int iconResId, OnClickListener onClickListener) {
        sToolbar.setNavigationIcon(iconResId);
        this.onClickListenerTopLeft = onClickListener;
    }

    protected void setTopRightButton(int menuResId, OnClickListener onClickListener) {
        this.onClickListenerTopRight = onClickListener;
        this.menuResId = menuResId;
    }

    protected void setTopRightButton(String menuStr, OnClickListener onClickListener) {
        this.onClickListenerTopRight = onClickListener;
        this.menuStr = menuStr;

    }

    protected void setTopRightButton(String menuStr, int menuResId, OnClickListener onClickListener) {
        this.menuResId = menuResId;
        this.menuStr = menuStr;
        this.onClickListenerTopRight = onClickListener;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuResId != 0 || !TextUtils.isEmpty(menuStr)) {

            getMenuInflater().inflate(R.menu.menu_activity_base_top_bar, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menuResId != 0) {
            menu.findItem(R.id.menu_1).setIcon(menuResId);
        }
        if (!TextUtils.isEmpty(menuStr)) {
            menuItem = menu.findItem(R.id.menu_1).setTitle(menuStr);
//            menuItem.setVisible(visible);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    boolean visible;

    protected void setMenuItemVisible(boolean visible) {
        this.visible = visible;
        if (menuItem != null) {
            menuItem.setVisible(visible);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onClickListenerTopLeft.onClick();
        } else if (item.getItemId() == R.id.menu_1) {
            onClickListenerTopRight.onClick();
        }
        // true 告诉系统我们自己处理了点击事件
        return true;
    }

    protected interface OnClickListener {
        /**
         * toolBar 监听接口重写
         */
        void onClick();
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initBase();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    public void startActivity(Class target, boolean isFinish) {
        Intent intent = new Intent(this, target);
        startActivity(intent);
        if (isFinish) {
            finish();
            //overridePendingTransition( R.anim.activity_left_in,R.anim.activity_right_out);
        } else {
            //overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
        }

    }

    /**
     * 启动activity 并设置动画
     *
     * @param target
     * @param isFinish
     * @param orientation -1:左 1:右
     */
    public void startActivity(Class target, boolean isFinish, int orientation) {
        Intent intent = new Intent(this, target);
        startActivity(intent);
        if (isFinish) {
            finish();
            //overridePendingTransition( R.anim.activity_left_in,R.anim.activity_right_out);
        } else {
            //overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
        }
        if (orientation == -1) {
            overridePendingTransition(R.anim.activity_left_in, R.anim.activity_right_out);
        } else {
            overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
        }
    }


    public void startActivity(Class target, boolean isFinish, boolean isTopping) {
        Intent intent = new Intent(this, target);
        if (isTopping) {

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            LogUtils.e(TAG, "startActivity: 所有活动出栈");
        }
        startActivity(intent);
        if (isFinish) {
            finish();
            //overridePendingTransition( R.anim.activity_left_in,R.anim.activity_right_out);
        } else {
            // overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
        }

    }

    public Context getContext() {
        return this;
    }


    @Override
    public void showToast(final String msg) {
        //hideAwaitDialog();
        //LogUtils.e(TAG, msg);
        try {
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }


    }


    @Override
    public void finish() {
        super.finish();
//        关闭dialog
        dismissImmediatelyAwaitDialog();
        dismissSelectDialog();
        //overridePendingTransition( R.anim.activity_left_in,R.anim.activity_right_out);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    /**
     * 返回键
     */
    public final void setLeftBack() {
        setTopLeftButton(R.drawable.icon_return, this::finish);

    }

    public void showSelectDialog(String content,String rightStr, View.OnClickListener onOKListener) {
        dismissSelectDialog();
        CommonHintDialog.Builder builder = new CommonHintDialog.Builder(this).setContent(content);
        builder.setNegativeButton(rightStr, onOKListener);


        builder.setPositiveButton(getResources().getString(R.string.cancel), v -> {
            dismissSelectDialog();
        });
        rCommonHintDialog = builder.create();

        rCommonHintDialog.show();
        Window win = rCommonHintDialog.getWindow();
        win.getDecorView().setPadding(DensityUtils.dip2px(this, 20), 0, DensityUtils.dip2px(this, 20), 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

    }


    public void dismissSelectDialog() {
        if (rCommonHintDialog != null) {
            rCommonHintDialog.dismiss();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e(TAG, "onRequestPermissionsResult: " + requestCode);
        switch (requestCode) {
            case REQUEST_CAMERA_CODE:
                boolean isGo = true;
                if (permissions.length > 0) {
                    for (int result : grantResults) {
                        if (result == PackageManager.PERMISSION_DENIED) {
                            isGo = false;
                        }
                    }
                }
                if (!isGo) {
                    ToastUtil.showToast(this, "权限不足");
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(intent);
                } else {
                    OpenCameraUtils.getInstance().openAlbum();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onSuccess(String value) {
        showSuccessDialog();
    }

    @Override
    public void onFailure(String code, String error) {
        showErrorDialog();
        dismissAwaitDialog();
    }


    @Override
    public void setAdapter() {
    }


    @Override
    public void showAwaitDialog(int id) {
        if (isFinishing()) {
            return;
        }
        if (promptDialog == null) {
            promptDialog = new PromptDialog(mActivity);
        }
        promptDialog.dismissImmediately();

        promptDialog.showLoading(getResources().getString(id), false);

    }

    @Override
    public void showAwaitDialog() {
        if (isFinishing()) {
            return;
        }
        if (promptDialog == null) {
            promptDialog = new PromptDialog(mActivity);
        }
        promptDialog.dismissImmediately();
        promptDialog.showLoading(getResources().getString(R.string.dialog_loading), false);

    }


    @Override
    public void dismissAwaitDialog() {
        Runnable task = () -> {
            try {
                sleep(1000);
                if (mActivity != null) {
                    mActivity.runOnUiThread(() -> {

                        if (promptDialog != null) {
                            promptDialog.dismissImmediately();
                        }
                    });
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        };

        ThreadPoolProxyFactory.getNormalThreadPoolProxy().execute(task);


    }

    @Override
    public void dismissImmediatelyAwaitDialog() {
        if (promptDialog != null) {
            promptDialog.dismissImmediately();
        }

    }

    @Override
    public void showSuccessDialog() {
        if (isFinishing()) {
            return;
        }
        if (promptDialog == null) {
            promptDialog = new PromptDialog(mActivity);
        }
        promptDialog.dismissImmediately();
        promptDialog.showSuccess(getResources().getString(R.string.finish), true);
    }

    @Override
    public void showWarningDialog() {
        if (isFinishing()) {
            return;
        }
        if (promptDialog == null) {
            promptDialog = new PromptDialog(mActivity);
        }
        promptDialog.dismissImmediately();
        promptDialog.showWarn(getResources().getString(R.string.dialog_warning), true);
    }

    @Override
    public void showErrorDialog() {
        if (isFinishing()) {
            return;
        }
        if (promptDialog == null) {
            promptDialog = new PromptDialog(mActivity);
        }
        promptDialog.dismissImmediately();
        promptDialog.showError(getResources().getString(R.string.dialog_error), true);
    }


    @Override
    public void showPromptDialog() {
        if (isFinishing()) {
            return;
        }
        if (promptDialog == null) {
            promptDialog = new PromptDialog(mActivity);
        }
        promptDialog.dismissImmediately();
        promptDialog.showInfo(getResources().getString(R.string.dialog_prompt), true);
    }



    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    public void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, ev)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(ev);

    }
}
