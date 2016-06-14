package com.example.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.Utils.Adapter;
import com.example.entity.ApkEntity;

import java.util.ArrayList;

/**
 * Created by Clanner on 2016/5/16.
 */
public class ThirdActivity extends AppCompatActivity implements LoadListView.ILoadListener,LoadListView.IReflashListener {
    ArrayList<ApkEntity> apk_list = new ArrayList<ApkEntity>();
    Adapter adapter;
    LoadListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thirdactivity);
        getData();
        showListView(apk_list);
    }

    private void showListView(ArrayList<ApkEntity> apk_list) {
        if (adapter == null) {
            listview = (LoadListView) findViewById(R.id.listview);
            listview.setInterface(this,this);
            adapter = new Adapter(this, apk_list);
            listview.setAdapter(adapter);
        } else {
            adapter.onDateChange(apk_list);
        }
    }

    private void getData() {
        for (int i = 0; i < 10; i++) {
            ApkEntity entity = new ApkEntity();
            entity.setName("测试程序");
            entity.setInfo("50w用户");
            entity.setDes("这是一个神奇的应用！");
            apk_list.add(entity);
        }
    }

    private void getLoadData() {
        for (int i = 0; i < 2; i++) {
            ApkEntity entity = new ApkEntity();
            entity.setName("更多程序");
            entity.setInfo("50w用户");
            entity.setDes("这是一个神奇的应用！");
            apk_list.add(entity);
        }
    }

    @Override
    public void onLoad() {
        // TODO Auto-generated method stub
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取更多数据
                getLoadData();
                //更新listview显示；
                showListView(apk_list);
                //通知listview加载完毕
                listview.loadComplete();
            }
        }, 500);
    }

    private void setReflashData() {
        for (int i = 0; i < 2; i++) {
            ApkEntity entity = new ApkEntity();
            entity.setName("刷新数据");
            entity.setDes("这是一个神奇的应用");
            entity.setInfo("50w用户");
            apk_list.add(0,entity);
        }
    }

    @Override
    public void onReflash() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取最新数据
                setReflashData();
                //通知界面显示
                showListView(apk_list);
                //通知listview 刷新数据完毕；
                listview.reflashComplete();
            }
        }, 500);
    }
}
