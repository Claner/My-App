package com.chat.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.chat.ui.fragment.BaseFragment;
import com.chat.ui.fragment.LoginFragment;

/**
 * Created by Clanner on 2016/5/24.
 */
public class LoginActivity extends AppActivity {

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return LoginFragment.newInstance();
    }

    /**
     * 获取Cookie
     */
    public String getCookie(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
}
