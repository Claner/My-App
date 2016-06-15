package com.chat.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chat.MyApplication;
import com.chat.entity.CheckHelper;
import com.chat.entity.ForgetPasswordHelper;
import com.chat.ui.activity.R;
import com.chat.ui.view.TimeButton;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Clanner on 2016/5/24.
 */
public class ForgetPasswordFragment extends BaseFragment implements View.OnClickListener {

    private String url = "http://tpwhcm.com/TDetc/Home/User/forgetPasswordSendMsg";
    private String checkUrl = "http://tpwhcm.com/TDetc/Home/User/checkCode";
    private Gson gson;
    private Handler handler;
    private TimeButton button;
    private EditText et_send;
    private EditText et_check;
    private String phoneNumber;
    private String checkCode;

    public static ForgetPasswordFragment newStance() {
        return new ForgetPasswordFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //在fragment中使用onOptionsItemSelected必须先调用下面的发法
        setHasOptionsMenu(true);
        et_send = (EditText) view.findViewById(R.id.et_forget);
        et_check = (EditText) view.findViewById(R.id.et_forget_code);
//        view.findViewById(R.id.forget_back).setOnClickListener(this);
//        view.findViewById(R.id.btn_forget_code).setOnClickListener(this);
        final TextInputLayout accountWarpper = (TextInputLayout) view.findViewById(R.id.account_wrapper);
        final TextInputLayout codeWrapper = (TextInputLayout) view.findViewById(R.id.code_wrapper);
        accountWarpper.setHint("请输入账号");
        codeWrapper.setHint("请输入验证码");
        button = (TimeButton) view.findViewById(R.id.btn_forget_code);
        button.setOnClickListener(this);
        view.findViewById(R.id.btn_forget).setOnClickListener(this);
        ActionBar actionBar = getHoldingActivity().getSupportActionBar();
        //给左上角图标的左边加上一个返回的图标
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("忘记密码");
        gson = new Gson();
        handler = new Handler();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forgetpassword;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_forget_code:
                SendMsg();
                break;
//            case R.id.btn_forget_code:
//                SendMsg();
//                break;
            case R.id.btn_forget:
                addFragment(ModifyPasswordFragment.newInstance());
                CheckMsgCode();
                break;
        }
    }

    /**
     * 发送验证码
     *
     * 这里有个bug，就算没有输入账号自定义的Button也会显示为60s
     * 于是用了个粗暴的方法解决
     * 即如果账号为空1则设置显示倒计时的时长为很短
     * 若不为空则设置为60s
     */
    private void SendMsg() {
        phoneNumber = et_send.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(getHoldingActivity().getApplicationContext(), "请输入账号以获取验证码", Toast.LENGTH_SHORT).show();
            button.setTextAfter("秒后重新获取").setTextBefore("点击获取验证码").setTimeLenght(1);
        } else {
            OkHttpClient client = MyApplication.getClient(getHoldingActivity().getApplicationContext());
            RequestBody body = new FormBody.Builder().add("phone", phoneNumber).build();
            Request request = new Request.Builder().url(url).post(body).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("Hello", "失败");
                }

                @Override
                public void onResponse(final Call call, final Response response) throws IOException {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ForgetPasswordHelper helper = gson.fromJson(response.body().string(), ForgetPasswordHelper.class);
                                int code = helper.getCode();
                                if (code == 20000) {
                                    Toast.makeText(getHoldingActivity().getApplicationContext(), "发送成功，请注意查收", Toast.LENGTH_SHORT).show();
                                    button.setTextAfter("秒后重新获取").setTextBefore("点击获取验证码").setTimeLenght(60 * 1000);
                                } else {
                                    Toast.makeText(getHoldingActivity().getApplicationContext(), "该账号未注册", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
    }

    /**
     * 检验验证码
     */
    private void CheckMsgCode() {
        checkCode = et_check.getText().toString().trim();
        if (TextUtils.isEmpty(checkCode)) {
            Toast.makeText(getHoldingActivity().getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
        } else {
            OkHttpClient client = MyApplication.getClient(getHoldingActivity().getApplicationContext());
            RequestBody body = new FormBody.Builder().add("code", checkCode).build();
            Request request = new Request.Builder().url(checkUrl).post(body).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("Hello", "失败");
                }

                @Override
                public void onResponse(final Call call, final Response response) throws IOException {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                CheckHelper helper = gson.fromJson(response.body().string(), CheckHelper.class);
                                int code = helper.getCode();
                                if (code == 20000) {
                                    addFragment(ModifyPasswordFragment.newInstance());
                                } else {
                                    Toast.makeText(getHoldingActivity().getApplicationContext(), "验证码错误或失效", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
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

    @Override
    public void onDestroy() {
        button.onDestroy();
        super.onDestroy();
    }
}
