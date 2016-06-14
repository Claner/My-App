package com.chat.ui.activity;

import android.os.Bundle;

import com.chat.ui.fragment.BaseFragment;
import com.chat.ui.fragment.LoginFragment;

/**
 * Created by Clanner on 2016/5/24.
 */
public class LoginActivity extends AppActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return LoginFragment.newInstance();
    }

}
