package com.chat.utils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chat.entity.ApkEntity;
import com.chat.ui.activity.R;

import java.util.ArrayList;

/**
 * Created by Clanner on 2016/6/2.
 */
public class SimpleAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    //private List<String> datas;
    private ArrayList<ApkEntity> datas;

    public SimpleAdapter(Context context, ArrayList<ApkEntity> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    //创建ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_single_textview, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    //绑定ViewHolder
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ApkEntity entity = datas.get(position);
        holder.name_tv.setText(entity.getName());
        holder.des_tv.setText(entity.getDes());
        holder.info_tv.setText(entity.getInfo());
        //holder.tv.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView name_tv;
    TextView des_tv;
    TextView info_tv;

    public MyViewHolder(View arg0) {
        super(arg0);
        name_tv = (TextView) arg0.findViewById(R.id.item3_apkname);
        des_tv = (TextView) arg0.findViewById(R.id.item3_apkdes);
        info_tv = (TextView) arg0.findViewById(R.id.item3_apkinfo);
        //tv = (TextView) arg0.findViewById(R.id.tv);
    }
}
