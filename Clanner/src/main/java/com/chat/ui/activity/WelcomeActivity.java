package com.chat.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.ImageView;

/**
 * Created by Clanner on 2016/6/14.
 */
public class WelcomeActivity extends Activity {

    private Handler handler = new Handler();
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        imageView = (ImageView) findViewById(R.id.welcome);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
                finish();
            }
        }, 1000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//欢迎界面屏蔽返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return true;
    }
}
