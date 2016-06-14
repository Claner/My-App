package com.chat.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chat.ui.activity.LoginActivity;
import com.chat.ui.activity.R;

/**
 * Created by Clanner on 2016/6/3.
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener {

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
//        view.findViewById(R.id.indicator_one).setVisibility(View.GONE);
        setLinstener(view);
    }

    private void setLinstener(View view) {
        view.findViewById(R.id.btn_notifications).setOnClickListener(this);
        view.findViewById(R.id.btn_donotdisturb).setOnClickListener(this);
        view.findViewById(R.id.btn_chat).setOnClickListener(this);
        view.findViewById(R.id.btn_privacy).setOnClickListener(this);
        view.findViewById(R.id.btn_general).setOnClickListener(this);
        view.findViewById(R.id.btn_myaccount).setOnClickListener(this);
        view.findViewById(R.id.btn_facebook).setOnClickListener(this);
        view.findViewById(R.id.btn_twitter).setOnClickListener(this);
        view.findViewById(R.id.btn_about).setOnClickListener(this);
        view.findViewById(R.id.btn_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_notifications:
                Toast.makeText(getHoldingActivity().getApplicationContext(), "新消息提醒", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_donotdisturb:
                Toast.makeText(getHoldingActivity().getApplicationContext(), "勿扰模式", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_chat:
                Toast.makeText(getHoldingActivity().getApplicationContext(), "聊天", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_privacy:
                Toast.makeText(getHoldingActivity().getApplicationContext(), "隐私", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_general:
                Toast.makeText(getHoldingActivity().getApplicationContext(), "通用", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_myaccount:
                Toast.makeText(getHoldingActivity().getApplicationContext(), "账号与安全", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_facebook:
                Toast.makeText(getHoldingActivity().getApplicationContext(), "在FaceBook上赞我们", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_twitter:
                Toast.makeText(getHoldingActivity().getApplicationContext(), "在Twitter上关注我们", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_about:
                Toast.makeText(getHoldingActivity().getApplicationContext(), "关于", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_logout:
                Intent intent = new Intent(getHoldingActivity().getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                Log.d("Hello", "exit");
                getHoldingActivity().finish();
                break;
        }
    }
}
