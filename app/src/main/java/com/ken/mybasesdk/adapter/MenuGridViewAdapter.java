package com.ken.mybasesdk.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.ken.mybasesdk.R;
import com.ken.mybasesdk.activity.UpdateAppVersionActivity;
import com.ken.mybasesdk.activity.UseToastActivity;
import com.ken.mybasesdk.bean.MenuBean;

import java.util.List;

public class MenuGridViewAdapter extends BaseAdapter {
    private static final String TAG = "MenuGridViewAdapter";
    private LayoutInflater layoutInflater;
    private List<MenuBean> beanList;
    private Context mContext;

    public MenuGridViewAdapter(Context context, List<MenuBean> beanList) {
        this.beanList = beanList;
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_gv_mian, null);
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.tv_action);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MenuBean actionBean = beanList.get(position);
        if (actionBean != null) {
            holder.text.setText(actionBean.getButtonText());
        }

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                switch (beanList.get(position).getAct()) {
                    case 0:
//                        APP 更新
                        intent.setClass(mContext, UpdateAppVersionActivity.class);
                        break;
                    case 1:
                        intent.setClass(mContext, UseToastActivity.class);
                        break;
                    default:
                        break;

                }
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }


    class ViewHolder {
        Button text;
    }

}
