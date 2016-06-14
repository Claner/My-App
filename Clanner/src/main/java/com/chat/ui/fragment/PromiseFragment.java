package com.chat.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.chat.ui.activity.R;


/**
 * Created by Clanner on 2016/5/24.
 */
public class PromiseFragment extends BaseFragment {

    private Toolbar toolbar;

    public static PromiseFragment newStance() {
        return new PromiseFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ActionBar actionBar = getHoldingActivity().getSupportActionBar();
        actionBar.setTitle("用户协议");
        Log.d("Hello","用户协议");
        actionBar.setDisplayHomeAsUpEnabled(true);
//        view.findViewById(R.id.promise_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                removeFragment();
//            }
//        });
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_promise;
    }
}
