package com.example.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ui.fragment.BaseFragment;
import com.example.ui.fragment.FirstFragment;

/**
 * Created by Clanner on 2016/5/23.
 */
public class LoginActivity extends AppActivity {

    private String username;

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            username = bundle.getString("username");
        }
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return FirstFragment.newInstance(username);
    }
}
