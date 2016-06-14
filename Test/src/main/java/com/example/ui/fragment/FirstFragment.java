package com.example.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.example.ui.activity.R;

/**
 * Created by Clanner on 2016/5/23.
 */

//未完成
public class FirstFragment extends BaseFragment implements View.OnClickListener {

    public static String FIRST_FRAGMENT = "first_fragment";
    private String msg;
    private EditText usernameEdt;

    public static FirstFragment newInstance(String msg) {
        FirstFragment fragment = new FirstFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FIRST_FRAGMENT, msg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            msg = (String) getArguments().getSerializable(FIRST_FRAGMENT);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        usernameEdt = (EditText) view.findViewById(R.id.username_edt);
        usernameEdt.setText(msg);
        view.findViewById(R.id.register_text).setOnClickListener(this);
        view.findViewById(R.id.first_back).setOnClickListener(this);
        view.findViewById(R.id.promise_text).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_text:
                addFragment(SecondFragment.newStance("从登录界面跳转过来的"));
                break;
            case R.id.first_back:
                removeFragment();
                break;
            case R.id.promise_text:
                addFragment(ThirdFragment.newInstance());
                break;
        }
    }
}
