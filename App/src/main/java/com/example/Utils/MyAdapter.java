package com.example.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.entity.Clanner;
import com.example.ui.R;

import java.util.List;

/**
 * Created by Clanner on 2016/5/8.
 */
public class MyAdapter extends BaseAdapter{
    private Context context;
    private List<Clanner.ItemsBean> list;
    private LayoutInflater inflater;

    public MyAdapter(Context context, List<Clanner.ItemsBean> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Clanner.ItemsBean items = list.get(position);
        viewHolder.name.setText(items.getName());
        return convertView;
    }

    class ViewHolder {
        private TextView name;
    }
}
