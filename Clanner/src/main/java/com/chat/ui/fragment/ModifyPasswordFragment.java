package com.chat.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chat.MyApplication;
import com.chat.entity.ResetHelper;
import com.chat.ui.activity.R;
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
public class ModifyPasswordFragment extends BaseFragment implements View.OnClickListener {

    private String url = "http://tpwhcm.com/TDetc/Home/User/forgetPassword";
    private Gson gson;
    private Handler handler;

    private EditText et_newPassword;
    private EditText et_sureNewPassword;
    private String newPassword;
    private String sureNewPassword;

    public static ModifyPasswordFragment newInstance() {
        return new ModifyPasswordFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        et_newPassword = (EditText) view.findViewById(R.id.et_newPassword);
        et_sureNewPassword = (EditText) view.findViewById(R.id.et_sureNewPassword);
//        view.findViewById(R.id.modify_back).setOnClickListener(this);
        view.findViewById(R.id.btn_modify).setOnClickListener(this);
        ActionBar actionBar = getHoldingActivity().getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("修改密码");
        gson = new Gson();
        handler = new Handler();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_modifypassword;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                removeFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.modify_back:
//                removeFragment();
//                break;
            case R.id.btn_modify:
                ResetPassword();
                break;
        }
    }

    /**
     * 修改密码
     */
    private void ResetPassword() {
        newPassword = et_newPassword.getText().toString().trim();
        sureNewPassword = et_sureNewPassword.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(sureNewPassword)) {
            Toast.makeText(getHoldingActivity().getApplicationContext(), "必填信息不能为空", Toast.LENGTH_SHORT).show();
            if (!newPassword.equals(sureNewPassword)) {
                Toast.makeText(getHoldingActivity().getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (newPassword.equals(sureNewPassword)) {
                OkHttpClient client = MyApplication.getClient(getHoldingActivity().getApplicationContext());
                RequestBody body = new FormBody.Builder().add("password", newPassword).add("rePassword", sureNewPassword).build();
                Request request = new Request.Builder().url(url).post(body).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("Hello", "失败");
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ResetHelper helper = gson.fromJson(response.body().string(), ResetHelper.class);
                                    int code = helper.getCode();
                                    if (code == 20000) {
                                        Toast.makeText(getHoldingActivity().getApplicationContext(), "重置密码成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getHoldingActivity().getApplicationContext(), "重置失败", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            } else {
                Toast.makeText(getHoldingActivity().getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
