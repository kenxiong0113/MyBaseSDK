package com.cimcitech.base_utils_class.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.cimcitech.base_utils_class.R;
import com.cimcitech.base_utils_class.utils.LogUtils;


public class CommonHintDialog extends Dialog {
    private static final String TAG = "CommonHintDialog";

    public CommonHintDialog(Context context) {
        super(context);
    }

    public CommonHintDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context;
        private String content;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;

        public Integer getContentGravity() {
            return contentGravity;
        }

        public Builder setContentGravity(Integer contentGravity) {
            this.contentGravity = contentGravity;
            return this;
        }

        private Integer contentGravity;

        private View.OnClickListener positiveButtonClickListener, negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }


        /**
         * Set the Dialog title from resource
         *
         * @param content
         * @return
         */
        public Builder setContent(int content) {
            this.content = (String) context.getText(content);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param content
         * @return
         */
        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        /**
         * Set a custom content view for the Dialog.
         * If a message is set, the contentView is not
         * added to the Dialog...
         *
         * @param v
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText, View.OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText, View.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button resource and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText, View.OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button text and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText, View.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        int layoutId = R.layout.custom_dialog;

        public Builder setLayoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }


        /**
         * Create the custom dialog
         */
        public CommonHintDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CommonHintDialog dialog = new CommonHintDialog(context, R.style.updateDialog);
            // TODO: 2019/2/24 0024 是否点击外部 取消对话框
            dialog.setCanceledOnTouchOutside(true);
            assert inflater != null;
            View layout = inflater.inflate(layoutId, null);

            Window window = dialog.getWindow();
            assert window != null;
            WindowManager.LayoutParams P = window.getAttributes();
            //LogUtils.e(TAG, "create: "+P.width );

            //P.width = ScreenUtils.getPxWidth(context);
            //P.y = context.getWindowManager().getDefaultDisplay().getHeight();
            // 以下这两句是为了保证按钮可以水平满屏
            P.width = ViewGroup.LayoutParams.MATCH_PARENT;
            P.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            LogUtils.e(TAG, "create: " + P.width);
            window.setAttributes(P);

//            window.setBackgroundDrawableResource(android.R.color.transparent);// 去掉dialog的默认背景
            dialog.setContentView(layout);
            // set the dialog title
            ((TextView) layout.findViewById(R.id.tv_title)).setText(content);

            if (contentGravity != null) {
                ((TextView) layout.findViewById(R.id.tv_title)).setGravity(contentGravity);
            }


            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_positive)).setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.btn_positive)).setOnClickListener(positiveButtonClickListener);
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.btn_positive).setVisibility(View.GONE);
            }

            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_negative)).setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.btn_negative)).setOnClickListener(negativeButtonClickListener);
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.btn_negative).setVisibility(View.GONE);
            }

            // set the content message
//            if (message != null) {
//                ((TextView) layout.findViewById(R.id.update_message)).setText(message);
//            } else if (contentView != null) {
//                // if no message set
//                // add the contentView to the dialog body
//                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
//                ((LinearLayout) layout.findViewById(R.id.content)).addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            }
//            dialog.setContentView(layout);
            // 设置显示位置
            //dialog.onWindowAttributesChanged(P);
            return dialog;
        }

    }

    @Override
    public void dismiss() {
        super.dismiss();


    }
}
