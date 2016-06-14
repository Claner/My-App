package com.example.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.ui.fragment.BaseFragment;

/**
 * Created by Clanner on 2016/5/23.
 * 进一步封装BaseActivity
 */
public abstract class AppActivity extends BaseActivity {

    //获取第一个Fragment
    protected abstract BaseFragment getFirstFragment();

    //获取Intent
    protected void handleIntent(Intent intent) {

    }

    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Hello","调用了AppActivity的onCreate方法");
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        if (getIntent() != null) {
            handleIntent(getIntent());
        }//避免重复添加Fragment
        if (getSupportFragmentManager().getFragments() == null) {
            BaseFragment firstFragment = getFirstFragment();
            if (firstFragment != null) {
                addFragment(firstFragment);
            }
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.fragment_container;
    }

    /**
     * 1.一个必须实现的抽象方法来获取当前Activity应该现实的第一个Fragment
     *
     * 2.获取intent的方法，在需要传递/接受数据的Activity实现
     *
     * 3.在Activity的onCreate()方法中拿到intent，添加fragment
     */
}
