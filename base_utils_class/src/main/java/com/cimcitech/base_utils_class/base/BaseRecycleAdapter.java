package com.cimcitech.base_utils_class.base;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cimcitech.base_utils_class.R;
import com.cimcitech.base_utils_class.dialog.CommonHintDialog;
import com.cimcitech.base_utils_class.progress_dialog.PromptDialog;
import com.cimcitech.base_utils_class.utils.DensityUtils;
import com.cimcitech.base_utils_class.utils.ToastUtil;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2018/3/8 0008.
 */

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context context;

    protected LayoutInflater layoutInflater;

    protected List<T> dataList;

    protected int layoutId;

    private CommonHintDialog rCommonHintDialog;

    PromptDialog promptDialog;


    public BaseRecycleAdapter(Context context, List<T> dataList, int layoutId) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.layoutId = layoutId;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(layoutId, parent, false);
        return new BaseViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public abstract void bindData(BaseViewHolder holder, T data, int position);

    public void delete(int position) {
        dataList.remove(position);
        //notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void deleteAll() {
        dataList.clear();
        notifyDataSetChanged();
    }

    // 获取到数据进行更新
    public void updateData(List<T> list) {
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged(); //刷新
    }


    /**
     * 插入一项
     *
     * @param item
     * @param position
     */
    public void insert(T item, int position) {
        dataList.add(position, item);
        notifyItemInserted(position);
        /**
         * RecyclerView notifyItemInserted(0)在RecycleView未满的时候，
         * 会有Insert的动画效果。但是，当RecycleView已满（需要滑动才能看到全部数据）时，
         * 就不再有Insert的动画效果了。
         */
        if (position == 0) {
//            recyclerView.scrollToPosition(0);
        }
    }


    public void showSelectDialog(String content, View.OnClickListener onOKListener) {

        CommonHintDialog.Builder builder = new CommonHintDialog.Builder(context).setContent(content);
        builder.setNegativeButton(context.getResources().getString(R.string.confirm), onOKListener);


        builder.setPositiveButton(context.getResources().getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rCommonHintDialog != null) {
                    rCommonHintDialog.dismiss();
                }
            }
        });
        rCommonHintDialog = builder.create();

        rCommonHintDialog.show();
        Window win = rCommonHintDialog.getWindow();
        win.getDecorView().setPadding(DensityUtils.dip2px(context, 20), 0, DensityUtils.dip2px(context, 20), 0);
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

    public void showToast(String msg) {
        try {
            ToastUtil.showToast(context,msg);
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            ToastUtil.showToast(context,msg);
            Looper.loop();
        }
    }


    public void showAwaitDialog(Activity mActivity,int id) {

        if (promptDialog == null) {
            promptDialog = new PromptDialog(mActivity);
        }
        promptDialog.dismissImmediately();

        promptDialog.showLoading(context.getResources().getString(id), false);

    }


    public void dismissImmediatelyAwaitDialog() {
        if (promptDialog != null) {
            promptDialog.dismissImmediately();
        }

    }



}


