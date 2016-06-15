package com.chat.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chat.ui.activity.R;

import java.util.List;

/**
 * Created by Clanner on 2016/6/14.
 */
public class SettingAdapter extends BaseAdapter {

    private List<String> datas;
    private LayoutInflater inflater;

    public SettingAdapter(Context context, List<String> datas) {
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_setting, null);
            //viewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(datas.get(position));
        return convertView;
    }

    class ViewHolder {
        private TextView textView;
    }
}
