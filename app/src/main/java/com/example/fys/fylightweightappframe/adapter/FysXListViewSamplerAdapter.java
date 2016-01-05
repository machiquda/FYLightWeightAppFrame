package com.example.fys.fylightweightappframe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fys.fylightweightappframe.R;

import java.util.List;


import fengyu.cn.library.views.BaseListAdapter;

/**
 * Created by fys on 2015/12/24.
 */
public class FysXListViewSamplerAdapter extends BaseListAdapter<String> {
    public FysXListViewSamplerAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final String item = getItem(position);
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.fyslistview_sample_item,
                    null);
            holder.textView = (TextView) convertView
                    .findViewById(R.id.tv_1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(item);

        return convertView;
    }

    class ViewHolder {
        TextView textView;

    }
}
