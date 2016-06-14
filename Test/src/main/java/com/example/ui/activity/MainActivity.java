package com.example.ui.activity;

import android.util.Log;

import com.example.ui.fragment.BaseFragment;
import com.example.ui.fragment.MainFragment;

public class MainActivity extends AppActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        Log.d("Hello", "从AppActivity中的onCreate后回来，重写getFirstFragment");
        return MainFragment.newInsrance();
    }
}
