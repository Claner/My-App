package com.chat.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chat.ui.activity.R;


/**
 * Created by Clanner on 2016/5/24.
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener {

    public static RegisterFragment newStance() {
        return new RegisterFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
//        view.findViewById(R.id.register_back).setOnClickListener(this);
        setHasOptionsMenu(true);
        view.findViewById(R.id.btn_send).setOnClickListener(this);
        view.findViewById(R.id.btn_register).setOnClickListener(this);
        ActionBar actionBar = getHoldingActivity().getSupportActionBar();
        actionBar.setTitle("注册");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.register_back:
//                removeFragment();
//                break;
            case R.id.btn_send:
                Toast.makeText(getHoldingActivity().getApplicationContext(), "发送验证码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_register:
                Toast.makeText(getHoldingActivity().getApplicationContext(), "注册", Toast.LENGTH_SHORT).show();
                break;
        }
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
