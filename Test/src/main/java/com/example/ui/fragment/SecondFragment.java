package com.example.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.ui.activity.R;

/**
 * Created by Clanner on 2016/5/23.
 */

public class SecondFragment extends BaseFragment {

    public static String SECOND_FRAGMENT = "second_fragment";
    private String msg;
    private TextView secondText;

    public static SecondFragment newStance(String msg) {
        SecondFragment fragment = new SecondFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SECOND_FRAGMENT, msg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            msg = (String) getArguments().getSerializable(SECOND_FRAGMENT);
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        secondText = (TextView) view.findViewById(R.id.second_txt);
        secondText.setText(msg);
        view.findViewById(R.id.second_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFragment();
            }
        });
    }

}
