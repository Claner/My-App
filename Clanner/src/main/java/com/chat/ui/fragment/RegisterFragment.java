package com.chat.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chat.ui.activity.R;
import com.chat.ui.view.TimeButton;


/**
 * Created by Clanner on 2016/5/24.
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener {

    //控件
    private EditText etAccount;
    private EditText etCode;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etSurePassword;
    private TimeButton btn_send;
    private Button btn_register;

    private String account;
    private String code;
    private String username;
    private String password;
    private String surePassword;

    public static RegisterFragment newStance() {
        return new RegisterFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ActionBar actionBar = getHoldingActivity().getSupportActionBar();
        actionBar.setTitle("注册");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setWidget(view);
        setTextInputLayout(view);
    }

    private void setWidget(View view) {
        etAccount = (EditText) view.findViewById(R.id.et_account);
        etCode = (EditText) view.findViewById(R.id.et_code);
        etUsername = (EditText) view.findViewById(R.id.et_username);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        etSurePassword = (EditText) view.findViewById(R.id.et_sureNewPassword);
        btn_send = (TimeButton) view.findViewById(R.id.btn_send);
        btn_register = (Button) view.findViewById(R.id.btn_register);
        btn_send.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }


    private void setTextInputLayout(View view) {
        final TextInputLayout etAccountWrapper = (TextInputLayout) view.findViewById(R.id.et_account_wrapper);
        final TextInputLayout etCodeWrapper = (TextInputLayout) view.findViewById(R.id.et_code_wrapper);
        final TextInputLayout registerUsernameWarpper = (TextInputLayout) view.findViewById(R.id.register_username_wrapper);
        final TextInputLayout registerPasswordWarpper = (TextInputLayout) view.findViewById(R.id.register_password_wrapper);
        final TextInputLayout registerSurePasswordWrapper = (TextInputLayout) view.findViewById(R.id.register_surepassword_wrapper);
        etAccountWrapper.setHint("请输入申请的账号");
        etCodeWrapper.setHint("请输入验证码");
        registerUsernameWarpper.setHint("请输入用户名");
        registerPasswordWarpper.setHint("请输入密码");
        registerSurePasswordWrapper.setHint("请确认密码");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                sendCode();
                break;
            case R.id.btn_register:
                Register();
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        account = etAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(getHoldingActivity().getApplicationContext(), "请输入账号以获取验证码", Toast.LENGTH_SHORT).show();
            btn_send.setTextAfter("秒后重新获取").setTextBefore("点击获取验证码").setTimeLenght(1);
        } else {
            Toast.makeText(getHoldingActivity().getApplicationContext(), "发送成功请注意查收", Toast.LENGTH_SHORT).show();
            btn_send.setTextAfter("秒后重新获取").setTextBefore("点击获取验证码").setTimeLenght(60 * 1000);
        }
    }

    /**
     * 注册
     */
    private void Register() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                removeFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
