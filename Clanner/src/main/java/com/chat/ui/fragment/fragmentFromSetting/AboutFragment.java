package com.chat.ui.fragment.fragmentFromSetting;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import com.chat.ui.activity.R;
import com.chat.ui.fragment.BaseFragment;

/**
 * Created by Clanner on 2016/6/15.
 */
public class AboutFragment extends BaseFragment {

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ActionBar actionBar = getHoldingActivity().getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("关于");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
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
