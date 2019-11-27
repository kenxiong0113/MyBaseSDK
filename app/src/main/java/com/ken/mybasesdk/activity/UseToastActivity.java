package com.ken.mybasesdk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cimcitech.base_utils_class.base.BaseToolBarActivity;
import com.cimcitech.base_utils_class.utils.CustomToast;
import com.cimcitech.base_utils_class.utils.ToastUtil;
import com.ken.mybasesdk.R;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class UseToastActivity extends BaseToolBarActivity implements RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "UseToastActivity";
    @BindView(R.id.btn_error)
    Button btnError;
    @BindView(R.id.btn_success)
    Button btnSuccess;
    @BindView(R.id.btn_info)
    Button btnInfo;
    @BindView(R.id.btn_warning)
    Button btnWarning;
    @BindView(R.id.btn_usual)
    Button btnUsual;
    @BindView(R.id.btn_usual_icon)
    Button btnUsualIcon;
    @BindView(R.id.btn_custom)
    Button btnCustom;
    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rb_3)
    RadioButton rb3;
    @BindView(R.id.rb_4)
    RadioButton rb4;
    @BindView(R.id.rg_toast)
    RadioGroup rgToast;
    @BindView(R.id.btn_toast)
    Button btnToast;

    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_use_toast;
    }

    @Override
    protected void initBase() {

    }

    @Override
    protected void initView() {
        setLeftBack();
        setTopTitle("个性化toast");
    }

    @Override
    protected void initListener() {
        rgToast.setOnCheckedChangeListener(this);
        btnError.setOnClickListener(view -> Toasty.error(mActivity, "This is an error toast.", Toast.LENGTH_SHORT, true).show());
        btnSuccess.setOnClickListener(view -> Toasty.success(mActivity, "Success!", Toast.LENGTH_SHORT, true).show());
        btnInfo.setOnClickListener(view -> Toasty.info(mActivity, "Here is some info for you.", Toast.LENGTH_SHORT, true).show());
        btnWarning.setOnClickListener(view -> Toasty.warning(mActivity, "Beware of the dog.", Toast.LENGTH_SHORT, true).show());
        btnUsual.setOnClickListener(view -> Toasty.normal(mActivity, "Normal toast w/o icon.").show());
//        To display the usual Toast with icon:
//
//        Toasty.normal(yourContext, "Normal toast w/ icon", yourIconDrawable).show();
//        You can also create your custom Toasts with the custom() method:
//
//        Toasty.custom(yourContext, "I'm a custom Toast", yourIconDrawable, tintColor, duration, withIcon,
//                shouldTint).show();

        btnToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToastUtil.showToast(UseToastActivity.this, "测试内容");
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_1:
                type = 1;
                break;
            case R.id.rb_2:
                type = 2;
                break;
            case R.id.rb_3:
                type = 3;
                break;
            case R.id.rb_4:
                type = 4;
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtil.cancelToast();
    }
}
