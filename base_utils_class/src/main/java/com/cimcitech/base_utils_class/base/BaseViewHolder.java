package com.cimcitech.base_utils_class.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cimcitech.base_utils_class.utils.LogUtils;


/**
 * 适配器基类Holder
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

    public String TAG = this.getClass().getName();

    protected Context context;

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    // SparseArray 比 HashMap 更省内存，在某些条件下性能更好，只能存储 key 为 int 类型的数据，
    // 用来存放 View 以减少 findViewById 的次数
    protected SparseArray<View> viewSparseArray;

    protected onItemCommonClickListener commonClickListener;

    public BaseViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        viewSparseArray = new SparseArray<>();
    }

    /**
     * 根据 ID 来获取 View
     *
     * @param viewId viewID
     * @param <T>    泛型
     * @return 将结果强转为 View 或 View 的子类型
     */
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找，找打的话则直接返回
        // 如果找不到则 findViewById ，再把结果存入缓存中
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }


    public BaseViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public BaseViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener l) {
        getView(viewId).setOnClickListener(l);
        return this;
    }

    public BaseViewHolder setOnTouchListener(int viewId, View.OnTouchListener l) {
        getView(viewId).setOnTouchListener(l);
        return this;
    }

    public BaseViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public BaseViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    /**
     * 设置 textview的文本背景
     *
     * @param viewId     控件id
     * @param resourceId 背景资源文件
     */
    public BaseViewHolder setTextBackgroundResource(int viewId, int resourceId) {
        TextView textView = getView(viewId);
        textView.setBackgroundResource(resourceId);
        return this;
    }

    public BaseViewHolder setBackgroundResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setBackgroundResource(resourceId);
        return this;
    }

    /**
     * 设置布局的背景
     */
    public BaseViewHolder setLinearLayoutBackgroundResource(int viewId, int resourceId) {
        LinearLayout linearLayout = getView(viewId);
        linearLayout.setBackgroundResource(resourceId);
        return this;
    }

    public interface onItemCommonClickListener {

        void onItemClickListener(int position);

        void onItemLongClickListener(int position);

    }

    public void setCommonClickListener(onItemCommonClickListener commonClickListener) {
        this.commonClickListener = commonClickListener;
    }

    @Override
    public void onClick(View v) {

//        LogUtils.e(TAG,"onClick: 哈哈哈哈哈");
//        long currentTime = Calendar.getInstance().getTimeInMillis();
//        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
//            lastClickTime = currentTime;
//            onNoDoubleClick();
//        }else {
//            Toast.makeText(context,context.getResources().getString(R.string.toast_Do_not_operate_frequently),Toast.LENGTH_SHORT).show();
//        }
        if (commonClickListener != null) {
            commonClickListener.onItemClickListener(getAdapterPosition());
        }
    }

    private void onNoDoubleClick() {
        LogUtils.e(TAG, "onNoDoubleClick: 嘿嘿嘿");
        if (commonClickListener != null) {
            commonClickListener.onItemClickListener(getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (commonClickListener != null) {
            commonClickListener.onItemLongClickListener(getAdapterPosition());
        }
        return false;
    }

    /**
     * 防止点击过快
     * */

}

