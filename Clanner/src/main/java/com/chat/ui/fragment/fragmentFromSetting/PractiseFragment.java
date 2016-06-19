package com.chat.ui.fragment.fragmentFromSetting;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import com.chat.ui.activity.R;
import com.chat.ui.fragment.BaseFragment;

/**
 * Created by Clanner on 2016/6/17.
 */
public class PractiseFragment extends BaseFragment{

    public static PractiseFragment newInsatnce(){
        return new PractiseFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_practise;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //设置ActionBar
        setHasOptionsMenu(true);
        ActionBar actionBar = getHoldingActivity().getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("练习");
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
