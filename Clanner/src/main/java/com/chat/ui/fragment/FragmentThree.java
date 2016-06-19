package com.chat.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chat.ui.activity.ImageViewActivity;
import com.chat.ui.activity.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * Created by Clanner on 2016/5/26.
 */
public class FragmentThree extends BaseFragment implements View.OnClickListener, View.OnLongClickListener {

    private EditText et_qrContent;
    private TextView qrText;
    private ImageView qr_imageview;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("content")) {
                Bundle bundle = intent.getExtras();
                String content = bundle.getString("qrcontent");
                qrText.setText(content);
            }
        }
    };

    public static FragmentThree newInstance() {
        return new FragmentThree();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        getHoldingActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        view.findViewById(R.id.btn_imageSelter).setOnClickListener(this);
        view.findViewById(R.id.btn_createqrcode).setOnClickListener(this);
        et_qrContent = (EditText) view.findViewById(R.id.et_qrcontent);
        qr_imageview = (ImageView) view.findViewById(R.id.qr_imageview);
        qr_imageview.setOnLongClickListener(this);
        qrText = (TextView) view.findViewById(R.id.qrText);
        final TextInputLayout et_qrContentWrapper = (TextInputLayout) view.findViewById(R.id.et_qrcontentwrapper);
        et_qrContentWrapper.setHint("请输入要转换为二维码的内容");

        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("content");
        getHoldingActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_imageSelter:
                Intent intent = new Intent(getHoldingActivity().getApplicationContext(), ImageViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_createqrcode:
                CreateQrCode();
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        Toast.makeText(getHoldingActivity().getApplicationContext(), "长按", Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * 生成二维码
     */
    private void CreateQrCode() {
        String content = et_qrContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(getHoldingActivity().getApplicationContext(), "请输入内容", Toast.LENGTH_SHORT).show();
        } else {
            /**
             * 第一个参数：转换的内容
             * 第二个参数：宽
             * 第三个参数：高
             * 第四个参数：loga
             */
            Bitmap bitmap = EncodingUtils.createQRCode(content, 500, 500,
                    BitmapFactory.decodeResource(getResources(), R.mipmap.welcome));
            qr_imageview.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getHoldingActivity().unregisterReceiver(receiver);
    }
}
