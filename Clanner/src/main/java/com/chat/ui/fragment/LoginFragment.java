package com.chat.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chat.entity.LoginHelper;
import com.chat.ui.activity.MainActivity;
import com.chat.ui.activity.R;
import com.chat.utils.MyOkHttpManager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Clanner on 2016/5/24.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private String baseUrl = "http://tpwhcm.com/TDetc/";
    private String url = "http://tpwhcm.com/TDetc/Home/User/login";
    private Gson gson;
    private Handler handler;

    private String account;
    private String username;
    private String password;

    //控件
    private EditText et_username;
    private EditText et_password;
    //    private ProgressBar progressBar;
//    private Spinner spinner;
    private ProgressDialog progressDialog;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        et_username = (EditText) view.findViewById(R.id.et_username);
        et_password = (EditText) view.findViewById(R.id.et_password);
        final TextInputLayout usernameWrapper = (TextInputLayout) view.findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) view.findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("Account");
        passwordWrapper.setHint("Password");
        ActionBar actionBar = getHoldingActivity().getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(R.string.app_name);
//        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        view.findViewById(R.id.btn_submit).setOnClickListener(this);
        view.findViewById(R.id.tv_register).setOnClickListener(this);
        view.findViewById(R.id.tv_promise).setOnClickListener(this);
        view.findViewById(R.id.tv_forget).setOnClickListener(this);
        gson = new Gson();
        handler = new Handler();
        account = getAccount();
        if (account != null) {
            et_username.setText(account);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                Login();
                break;
            case R.id.tv_register:
                Register();
                break;
            case R.id.tv_promise:
                Promise();
                break;
            case R.id.tv_forget:
                ForgetPassword();
                break;
        }
    }

    /**
     * 登陆
     */
    private void Login() {
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
//        progressBar.setVisibility(View.VISIBLE);
        progressDialog = ProgressDialog.show(getHoldingActivity(), null, "正在登陆...");

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getHoldingActivity().getApplicationContext(), "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            });
        } else {

            MyOkHttpManager.getInstance().setContext(getHoldingActivity().getApplicationContext());
            MyOkHttpManager.Post(url, new MyOkHttpManager.ResultCallback() {
                @Override
                public void onError(IOException e) {
                    Log.d("Hello", "失败");
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    LoginHelper helper = null;
                    try {
                        helper = gson.fromJson(response.body().string(), LoginHelper.class);
                        int code = helper.getCode();
                        if (code == 20000) {
                            saveAccount(username);
                            progressDialog.dismiss();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            //以下方法只适用于5.0之后的系统
                            getHoldingActivity().overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
                            getHoldingActivity().finish();
                        }
                    } catch (JsonSyntaxException e2) {
                        e2.printStackTrace();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
//                                progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                                Toast.makeText(getHoldingActivity().getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }
            }, new MyOkHttpManager.Param[]{
                    new MyOkHttpManager.Param("phone", username),
                    new MyOkHttpManager.Param("password", password)
            });

//            OkHttpClient client = MyApplication.getClient(getHoldingActivity().getApplicationContext());
//            RequestBody body = new FormBody.Builder().add("phone", username).add("password", password).build();
//            Request request = new Request.Builder().url(url).post(body).build();
//            Call call = client.newCall(request);
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Log.d("Hello", "失败");
//                }
//
//                @Override
//                public void onResponse(Call call, final Response response) throws IOException {
//
//                    LoginHelper helper = gson.fromJson(response.body().string(), LoginHelper.class);
//                    int code = helper.getCode();
//                    if (code == 20000) {
//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        startActivity(intent);
//                        getHoldingActivity().finish();
//                    }
//
//                }
//            });

        }
    }

    /**
     * 注册
     */
    private void Register() {
        addFragment(RegisterFragment.newStance());
    }

    /**
     * 用户协议
     */
    private void Promise() {
        addFragment(PromiseFragment.newStance());
    }

    /**
     * 忘记密码
     */
    private void ForgetPassword() {
        addFragment(ForgetPasswordFragment.newStance());
    }

    /**
     * 获取Cookie
     */
    public String getCookie(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    /**
     * 保存用户名
     */
    public void saveAccount(String account) {
        SharedPreferences preferences = getHoldingActivity().getApplicationContext().getSharedPreferences("account", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("account", account);
        editor.commit();
    }

    /**
     * 获取用户名
     */
    public String getAccount() {
        SharedPreferences preferences = getHoldingActivity().getApplicationContext().getSharedPreferences("account", Context.MODE_PRIVATE);
        return preferences.getString("account", "");
    }

}
