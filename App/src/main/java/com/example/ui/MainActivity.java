package com.example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.Utils.DoubleClickExitHelper;
import com.example.Utils.MyAdapter;
import com.example.Utils.MyOkHttpHelper;
import com.example.Utils.OkHttpClientManager;
import com.example.entity.Clanner;
import com.example.fragment.LeftMenuFragment;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //控件
    private Toolbar toolbar;
    private Button sec;
    private Button get;
    private Button post;
    private Button third;
    private ProgressBar progressBar;
    private ListView listView;
    private LeftMenuFragment leftMenu;
    private DrawerLayout drawerLayout;

    //工具
    private Gson gson;
    private List<Clanner.ItemsBean> list;
    private MyAdapter adapter;
    private DoubleClickExitHelper doubleClickExitHelper;

    //url地址
    private String url = "https://api.github.com/search/repositories?q=javascript&sort=stars";
    private String url2 = "https://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setListener();
    }

    /**
     * 初始化
     */
    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        sec = (Button) findViewById(R.id.btn_sec);
        get = (Button) findViewById(R.id.btn_get);
        post = (Button) findViewById(R.id.btn_post);
        third = (Button) findViewById(R.id.thirdActivity);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        listView = (ListView) findViewById(R.id.listView);
        gson = new Gson();
        list = new ArrayList<Clanner.ItemsBean>();
        doubleClickExitHelper = new DoubleClickExitHelper(this);
        leftMenu = new LeftMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.left_menu, leftMenu).commit();
    }

    /**
     * 设置点击事件
     */
    private void setListener() {
        sec.setOnClickListener(this);
        get.setOnClickListener(this);
        post.setOnClickListener(this);
        third.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sec:
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_get:
                progressBar.setVisibility(View.VISIBLE);
                Get();
                break;
            case R.id.btn_post:
                progressBar.setVisibility(View.VISIBLE);
                Post();
                break;
            case R.id.thirdActivity:
                Intent intent2 = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private void Get() {

        MyOkHttpHelper.Get(url, new MyOkHttpHelper.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.d("Hello", "请求失败");
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.d("Hello", "请求成功");
                progressBar.setVisibility(View.GONE);
                Clanner clanner = gson.fromJson(response, Clanner.class);
                for (int i = 0; i < 30; i++) {
                    Clanner.ItemsBean item = clanner.getItems(i).get(i);
                    list.add(item);
                }
                adapter = new MyAdapter(getApplicationContext(), list);
                listView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
            }
        });


//        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.ResultCallback<String>() {
//            @Override
//            public void onError(Request request, Exception e) {
//                Log.d("Hello", "请求失败");
//                progressBar.setVisibility(View.GONE);
//                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.d("Hello", "请求成功");
//                progressBar.setVisibility(View.GONE);
//                Clanner clanner = gson.fromJson(response, Clanner.class);
//                for (int i = 0; i < 30; i++) {
//                    Clanner.ItemsBean item = clanner.getItems(i).get(i);
//                    list.add(item);
//                }
//                adapter = new MyAdapter(getApplicationContext(), list);
//                listView.setAdapter(adapter);
//                Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void Post() {
        OkHttpClientManager.postAsyn(url2, new OkHttpClientManager.ResultCallback<String>() {

            @Override
            public void onError(Request request, Exception e) {
                Log.d("Hello", "请求失败");
            }

            @Override
            public void onResponse(String response) {
                Log.d("Hello", "请求成功");
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                Log.d("Hello", response);
            }
        }, new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param("userName", "Clanner")
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return doubleClickExitHelper.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

}
