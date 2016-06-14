package com.chat.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chat.ui.activity.ImageViewActivity;
import com.chat.ui.activity.R;

/**
 * Created by Clanner on 2016/5/26.
 */
public class FragmentThree extends BaseFragment implements View.OnClickListener {

    public static FragmentThree newInstance() {
        return new FragmentThree();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.btn_imageSelter).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_imageSelter:
                Intent intent = new Intent(getHoldingActivity().getApplicationContext(), ImageViewActivity.class);
                startActivity(intent);
                break;
        }
    }
}
