package com.chat.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chat.entity.ApkEntity;
import com.chat.ui.activity.R;
import com.chat.ui.view.DividerItemDecoration;
import com.chat.utils.adapter.SimpleAdapter;

import java.util.ArrayList;

/**
 * Created by Clanner on 2016/5/26.
 */
public class FragmentTwo extends BaseFragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    //private List<String> datas = new ArrayList<>();
    private ArrayList<ApkEntity> apk_list = new ArrayList<ApkEntity>();
    private SimpleAdapter adapter;

    public static FragmentTwo newInstance() {
        return new FragmentTwo();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initData();
        setMethod(view);
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            ApkEntity entity = new ApkEntity();
            entity.setName("测试程序");
            entity.setInfo("50w用户");
            entity.setDes("这是一个神奇的应用！");
            apk_list.add(entity);
        }
//        datas = new ArrayList<>();
//        for (int i = 'A'; i <= 'z'; i++) {
//            datas.add("" + (char) i);
//        }
    }

    //设置点击事件
    private void setMethod(View v) {
        v.findViewById(R.id.btn_gridview).setOnClickListener(this);
        v.findViewById(R.id.btn_listview).setOnClickListener(this);
        v.findViewById(R.id.btn_hor_gridview).setOnClickListener(this);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        adapter = new SimpleAdapter(getHoldingActivity().getApplicationContext(), apk_list);
        recyclerView.setAdapter(adapter);

        //设置RecyclerView的布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getHoldingActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //设置RecyclerView的Item间分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(getHoldingActivity().getApplicationContext()
                , DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gridview:
                recyclerView.setLayoutManager(new GridLayoutManager(getHoldingActivity().getApplicationContext(), 3));
                break;
            case R.id.btn_listview:
                recyclerView.setLayoutManager(new LinearLayoutManager(getHoldingActivity()));
                break;
            case R.id.btn_hor_gridview:
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_two;
    }

}
