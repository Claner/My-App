package com.example.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ui.activity.LoginActivity;
import com.example.ui.activity.R;

/**
 * Created by Clanner on 2016/5/23.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    public static MainFragment newInsrance() {
        return new MainFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.main_btn).setOnClickListener(this);
        view.findViewById(R.id.main_second_btn).setOnClickListener(this);
        view.findViewById(R.id.main_tablayout_btn).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn:
                Bundle data = new Bundle();
                data.putString("username", "Clanner");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtras(data);
                startActivity(intent);
                break;
            case R.id.main_second_btn:
                addFragment(SecondFragment.newStance("从首界面跳转过来的"));
                break;
            case R.id.main_tablayout_btn:
                break;
        }
    }
}
