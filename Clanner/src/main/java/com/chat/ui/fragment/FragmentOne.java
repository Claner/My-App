package com.chat.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.chat.ui.activity.R;

/**
 * Created by Clanner on 2016/5/26.
 */
public class FragmentOne extends BaseFragment {

    public static FragmentOne newInstance() {
        return new FragmentOne();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }
}
