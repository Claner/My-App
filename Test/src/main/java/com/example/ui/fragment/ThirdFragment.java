package com.example.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.ui.activity.R;

/**
 * Created by Clanner on 2016/5/23.
 */
public class ThirdFragment extends BaseFragment {

    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.third_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFragment();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_third;
    }
}
