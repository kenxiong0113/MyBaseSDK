package com.cimcitech.base_utils_class.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.cimcitech.base_utils_class.R;
import com.cimcitech.base_utils_class.dialog.CommonHintDialog;
import com.cimcitech.base_utils_class.progress_dialog.PromptDialog;
import com.cimcitech.base_utils_class.thread_pool.ThreadPoolProxyFactory;
import com.cimcitech.base_utils_class.utils.DensityUtils;
import com.cimcitech.base_utils_class.view.BaseViewIF;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static java.lang.Thread.sleep;


/**
 * Fragment+ViewPager 懒加载
 *
 * @author by ken on 2019.5.8
 */
public abstract class BaseFragment extends Fragment implements BaseViewIF {
    public Context mContext;
    public Activity mActivity;

    public View view;
    Unbinder unbinder;

    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    /**
     * 是否开始加载
     */
    protected boolean isLoad = false;

    CommonHintDialog rCommonHintDialog;
    private PromptDialog promptDialog;


    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view, savedInstanceState);
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();

        initListener();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void startActivity(Class target) {
        startActivity(new Intent(mContext, target));
        //getActivity().overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 该抽象方法就是 初始化view
     *
     * @param view
     * @param savedInstanceState
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    public abstract void initListener();

    /**
     * 返回
     */
    public abstract boolean onBack();


    @Override
    public void showToast(final String msg) {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
        unbinder.unbind();
    }


    public void startActivity(Class target, boolean isFinish) {
        startActivity(new Intent(getActivity(), target));
        if (isFinish) {
            getActivity().finish();
            //overridePendingTransition( R.anim.activity_left_in,R.anim.activity_right_out);
        } else {
            // overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    public void finish() {
        getActivity().finish();
    }

    @Override
    public void onSuccess(String result) {
        dismissAwaitDialog();
    }

    @Override
    public void onFailure(String code, String error) {
        dismissAwaitDialog();
    }

    public void showSelectDialog(String content, View.OnClickListener onOKListener) {

        CommonHintDialog.Builder builder = new CommonHintDialog.Builder(getActivity()).setContent(content);
        builder.setNegativeButton(getResources().getString(R.string.confirm), onOKListener);


        builder.setPositiveButton(getResources().getString(R.string.cancel), v -> {
            if (rCommonHintDialog != null) {
                rCommonHintDialog.dismiss();
            }
        });
        rCommonHintDialog = builder.create();

        rCommonHintDialog.show();
        Window win = rCommonHintDialog.getWindow();
        win.getDecorView().setPadding(DensityUtils.dip2px(mContext, 20), 0, DensityUtils.dip2px(mContext, 20), 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

    }


    public void dismissDialog() {
        if (rCommonHintDialog != null) {
            rCommonHintDialog.dismiss();
        }
    }

    @Override
    public void setAdapter() {

    }


    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以调用此方法
     */
    protected void stopLoad() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    @Override
    public void showAwaitDialog(int id) {
        if (!isVisible()) {
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
        if (!isVisible()) {
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
        Runnable delayTask = () -> {
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
        ThreadPoolProxyFactory.getNormalThreadPoolProxy().execute(delayTask);
    }

    @Override
    public void dismissImmediatelyAwaitDialog() {
        if (promptDialog != null) {
            promptDialog.dismissImmediately();
        }

    }

    @Override
    public void showSuccessDialog() {
        if (!isVisible()) {
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
        if (!isVisible()) {
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
        if (!isVisible()) {
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
        if (!isVisible()) {
            return;
        }
        if (promptDialog == null) {
            promptDialog = new PromptDialog(mActivity);
        }
        promptDialog.dismissImmediately();
        promptDialog.showInfo(getResources().getString(R.string.dialog_prompt), true);
    }

}
