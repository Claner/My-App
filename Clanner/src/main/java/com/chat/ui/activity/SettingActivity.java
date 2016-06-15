package com.chat.ui.activity;

import android.os.Bundle;

import com.chat.ui.fragment.BaseFragment;
import com.chat.ui.fragment.SettingFragment;

/**
 * Created by Clanner on 2016/6/14.
 */
public class SettingActivity extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return SettingFragment.newInstance();
    }
}
