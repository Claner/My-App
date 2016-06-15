package com.chat.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chat.ui.activity.LoginActivity;
import com.chat.ui.activity.R;
import com.chat.ui.fragment.fragmentFromSetting.AboutFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Clanner on 2016/6/3.
 */
public class SettingFragment extends BaseFragment implements ListView.OnItemClickListener {

    private ListView listView;
    private ListView listView2;
    private List<String> datas;
    private List<String> datas2;
    //    private SettingAdapter adapter
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setDatas();
        initView(view);
    }

    private void initView(View view) {

        setHasOptionsMenu(true);
        ActionBar actionBar = getHoldingActivity().getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("设置");

        listView = (ListView) view.findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(getContext(), R.layout.item_setting, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        listView2 = (ListView) view.findViewById(R.id.listview2);
        adapter2 = new ArrayAdapter<String>(getContext(), R.layout.item_setting, datas2);
        listView2.setAdapter(adapter2);
        listView2.setOnItemClickListener(this);
//        adapter = new SettingAdapter(getHoldingActivity().getApplicationContext(), datas);
//        listView.setAdapter(adapter);
    }

    /**
     * 设置数据
     */
    private void setDatas() {
        datas = new ArrayList<>(Arrays.asList("Notifications", "Do Not Disturb", "Chat", "Privacy",
                "General", "My Account", "Like Us On Facebook", "Follow Us On Twitter"));

        datas2 = new ArrayList<>(Arrays.asList("About", "Log Out"));
//        datas = new ArrayList<>();
//        datas.add("Notifications");
//        datas.add("Do Not Disturb");
//        datas.add("Chat");
//        datas.add("Privacy");
//        datas.add("General");
//        datas.add("My Account");
//        datas.add("Like Us On Facebook");
//        datas.add("Follow Us On Twitter");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.listview:
                list1_onClickListener(i);
                break;
            case R.id.listview2:
                list2_onClickListener(i);
                break;
        }
    }

    /**
     * 列表一的点击事件
     */
    private void list1_onClickListener(int i) {
        switch (i) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
        }
    }

    /**
     * 列表二的点击事件
     */
    private void list2_onClickListener(int i) {
        switch (i) {
            case 0:
                addFragment(AboutFragment.newInstance());
                break;
            case 1:
                LogOut();
                break;
        }
    }

    private void LogOut() {
        getHoldingActivity().finish();
        //发送一条广播去关闭Activity
        getHoldingActivity().getApplicationContext().sendBroadcast(new Intent("finish"));
        Intent intent = new Intent(getHoldingActivity().getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                removeFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
